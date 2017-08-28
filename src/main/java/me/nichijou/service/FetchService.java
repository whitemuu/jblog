package me.nichijou.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import me.nichijou.pojo.SourceFileInfo;
import me.nichijou.util.OrgParser;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by nichijou on 8/27/17.
 */
@Service
public class FetchService {
	@Autowired
	private ArticleService articleService;

	private static final ObjectMapper MAPPER = new ObjectMapper();

	@Value("${GITHUB_REPO_URL}")
	public String REPO_URL;
	@Value("${GITHUB_ARTICLES_URL}")
	public String ARTICLES_URL;

	public String doGet(String url) throws IOException {
		CloseableHttpClient client = HttpClients.createDefault();
		HttpGet httpGet = new HttpGet(url);
		CloseableHttpResponse response = null;
		try {
			response = client.execute(httpGet);
			if (response.getStatusLine().getStatusCode() == 200) {
				return EntityUtils.toString(response.getEntity(), "utf-8");
			}
			return null;
		} finally {
			if (response != null)
				response.close();
			client.close();
		}
	}

	public List<SourceFileInfo> getSourceFileInfos() throws IOException {
		String articlesJson = this.doGet(ARTICLES_URL);
		this.MAPPER.setPropertyNamingStrategy(
				PropertyNamingStrategy.CAMEL_CASE_TO_LOWER_CASE_WITH_UNDERSCORES);
		List<SourceFileInfo> sourceFileInfos = MAPPER.readValue(articlesJson, new TypeReference<List<SourceFileInfo>>() {
		});
		return sourceFileInfos;
	}

	public void refreshArticles() throws IOException {
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
		if (!dbArticles.isEmpty()) {
			this.articleService.removeArticles(dbArticles);
		}
		if (!sourceFileInfos.isEmpty()) {
			for (SourceFileInfo sourceFileInfo : sourceFileInfos) {
				this.fetchTitleAndContent(sourceFileInfo);
			}
			this.articleService.addArticles(sourceFileInfos);
		}
		if (!updatedSourceFileInfos.isEmpty()) {
			for (SourceFileInfo updatedSourceFileInfo : updatedSourceFileInfos) {
				this.fetchTitleAndContent(updatedSourceFileInfo);
			}
			this.articleService.updateArticles(updatedSourceFileInfos);
		}
	}

	public void fetchTitleAndContent(SourceFileInfo sourceFileInfo) throws IOException {
		String content = this.doGet(sourceFileInfo.getDownloadUrl());
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(content.getBytes())));
		OrgParser.parseMeta(bufferedReader, sourceFileInfo);

		Elements article =  Jsoup.connect(sourceFileInfo.getHtmlUrl()).get().select("#readme > article");
		article.select("article > h1:nth-child(1)").remove();
		article.select("a.anchor").remove();
		sourceFileInfo.setContent(article.toString());
	}

	/**
	 * @param sourceFileInfo
	 * @throws IOException use fetchTitleAndContent instead
	 */
	@Deprecated
	public void fetchAndParseFile(SourceFileInfo sourceFileInfo) throws IOException {
		String content = this.doGet(sourceFileInfo.getDownloadUrl());
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(content.getBytes())));
		OrgParser.parseMeta(bufferedReader, sourceFileInfo);
		OrgParser.parseContent(bufferedReader, sourceFileInfo);
	}
}
