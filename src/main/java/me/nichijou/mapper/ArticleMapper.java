package me.nichijou.mapper;

import me.nichijou.pojo.Article;

import java.util.List;

/**
 * Created by nichijou on 8/27/17.
 */
public interface ArticleMapper {
	Article selectArticleByName(String name);

	List<Article> selectAllTitles();
}
