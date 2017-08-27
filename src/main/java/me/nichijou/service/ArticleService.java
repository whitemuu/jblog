package me.nichijou.service;

import me.nichijou.exception.NoArticleWithThisName;
import me.nichijou.mapper.ArticleMapper;
import me.nichijou.mapper.SourceFileInfoMapper;
import me.nichijou.pojo.Article;
import me.nichijou.pojo.SourceFileInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by nichijou on 8/25/17.
 */
@Service
public class ArticleService {
	@Autowired
	private SourceFileInfoMapper sourceFileInfoMapper;
	@Autowired
	private ArticleMapper articleMapper;

	public Article getArticleByName(String name) throws NoArticleWithThisName {
		Article article = this.articleMapper.selectArticleByName(name);
		if (article == null) {
			throw new NoArticleWithThisName();
		}
		return article;
	}

	Map<String, String> getNameAndShaInRepo() {
		List<SourceFileInfo> sourceFileInfos = this.sourceFileInfoMapper.selectNameAndSha();
		HashMap<String, String> map = new HashMap<>();
		for (SourceFileInfo sourceFileInfo : sourceFileInfos) {
			map.put(sourceFileInfo.getName(), sourceFileInfo.getSha());
		}
		return map;
	}

	public void removeArticles(Map<String, String> articles) {
		this.sourceFileInfoMapper.deleteArticles(articles);
	}


	public void addArticles(List<SourceFileInfo> sourceFileInfos) {
		this.sourceFileInfoMapper.addArticles(sourceFileInfos);
	}

	public void updateArticles(List<SourceFileInfo> updatedSourceFileInfos) {
		this.sourceFileInfoMapper.updateArticles(updatedSourceFileInfos);
	}

	public List<Article> getAllTitles() {
		List<Article> articles = this.articleMapper.selectAllTitles();
		return articles;
	}
}
