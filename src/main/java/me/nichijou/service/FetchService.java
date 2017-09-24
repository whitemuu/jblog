package me.nichijou.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import me.nichijou.pojo.Article;
import me.nichijou.pojo.SourceFileInfo;
import me.nichijou.util.HttpUtil;
import me.nichijou.util.OrgParser;
import org.jsoup.Jsoup;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.*;

/**
 * Created by nichijou on 8/27/17.
 */
@Service
public class FetchService {

	@Autowired
	private ArticleService articleService;

	private static final ObjectMapper MAPPER = new ObjectMapper();

	private static final Logger LOGGER = LoggerFactory.getLogger(FetchService.class);

	@Value("${GITHUB_REPO_URL}")
	private String REPO_URL;
	@Value("${GITHUB_ARTICLES_URL}")
	private String ARTICLES_URL;

	public void refreshArticles(String json) throws IOException, TemplateException {
		List<SourceFileInfo> sourceFileInfos = this.getSourceFileInfos();
		List<SourceFileInfo> updatedSourceFileInfos = new LinkedList<>();
		Map<String, String> dbArticles = this.articleService.getNameAndShaInRepo();
		Iterator<SourceFileInfo> iterator = sourceFileInfos.iterator();
		while (iterator.hasNext()) {
			SourceFileInfo next = iterator.next();
			String name = next.getName();
			String oldSha;
			if ((oldSha = dbArticles.get(name)) != null) {
				dbArticles.remove(name);
				iterator.remove();
				if (!oldSha.equals(next.getSha())) {
					updatedSourceFileInfos.add(next);
				}
			}
		}
		boolean anyUpdate = false;
		if (!dbArticles.isEmpty()) {
			anyUpdate = true;
			this.articleService.removeArticles(dbArticles);
			LOGGER.info("删除文章" + dbArticles.size() + "篇");
		}
		if (!sourceFileInfos.isEmpty()) {
			anyUpdate = true;
			for (SourceFileInfo sourceFileInfo : sourceFileInfos) {
				this.fetchTitleAndContent(sourceFileInfo);
			}
			this.articleService.addArticles(sourceFileInfos);
			LOGGER.info("增加文章" + sourceFileInfos.size() + "篇");
		}
		if (!updatedSourceFileInfos.isEmpty()) {
			anyUpdate = true;
			for (SourceFileInfo updatedSourceFileInfo : updatedSourceFileInfos) {
				this.fetchTitleAndContent(updatedSourceFileInfo);
			}
			this.articleService.updateArticles(updatedSourceFileInfos);
			LOGGER.info("更新文章" + updatedSourceFileInfos.size() + "篇");
		}
		if (anyUpdate) {
			this.updateRss();
		}
	}

	public List<SourceFileInfo> getSourceFileInfos() throws IOException {
		String articlesJson = HttpUtil.doGet(ARTICLES_URL);
		this.MAPPER.setPropertyNamingStrategy(
				PropertyNamingStrategy.CAMEL_CASE_TO_LOWER_CASE_WITH_UNDERSCORES);
		List<SourceFileInfo> sourceFileInfos = MAPPER.readValue(articlesJson, new TypeReference<List<SourceFileInfo>>() {
		});
		return sourceFileInfos;
	}

	public void fetchTitleAndContent(SourceFileInfo sourceFileInfo) throws IOException {
		String content = HttpUtil.doGet(sourceFileInfo.getDownloadUrl());
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(content.getBytes())));
		OrgParser.parseMeta(bufferedReader, sourceFileInfo);
		OrgParser.readDesc(bufferedReader, sourceFileInfo);

		Elements article = Jsoup.connect(sourceFileInfo.getHtmlUrl()).get().select("#readme > article");
		article.select("article > h1:nth-child(1)").remove();
		article.select("a.anchor").remove();
		article.select("table").wrap("<div style=\"overflow-x:auto;\"></div>");
		sourceFileInfo.setContent(article.toString());
	}

	/**
	 * @param sourceFileInfo
	 * @throws IOException use fetchTitleAndContent instead
	 */
	@Deprecated
	public void fetchAndParseFile(SourceFileInfo sourceFileInfo) throws IOException {
		String content = HttpUtil.doGet(sourceFileInfo.getDownloadUrl());
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(content.getBytes())));
		OrgParser.parseMeta(bufferedReader, sourceFileInfo);
		OrgParser.parseContent(bufferedReader, sourceFileInfo);
	}

	public void updateRss() throws IOException, TemplateException {
		Configuration configuration = new Configuration(Configuration.VERSION_2_3_23);

		String classPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
		configuration.setDirectoryForTemplateLoading(new File(classPath + "template"));
		Template template = configuration.getTemplate("rss.ftl");
		HashMap<String, Object> root = new HashMap<>();
		List<Article> allTitles = this.articleService.getAllTitles();
		root.put("articles", allTitles);

		Writer writer = new FileWriter(new File(classPath.substring(0, classPath.indexOf("/WEB-INF/classes")) + "/feed/rss.xml"));
//		Writer writer = new FileWriter(new File("/Users/nichijou/Documents/ideaProjects/jblog/src/main/webapp/feed/rss.xml"));

		template.process(root, writer);
		LOGGER.info("rss已更新");
	}
}
