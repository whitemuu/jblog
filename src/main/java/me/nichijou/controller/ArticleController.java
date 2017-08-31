package me.nichijou.controller;

import me.nichijou.exception.NoArticleWithThisName;
import me.nichijou.pojo.Article;
import me.nichijou.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by nichijou on 8/25/17.
 */
@Controller
@RequestMapping("article")
public class ArticleController {
	@Autowired
	ArticleService service;

	@RequestMapping(value = "{name}", method = RequestMethod.GET)
	public String getArticle(@PathVariable String name, Model model) {
		try {
			Article article = this.service.getArticleByName(name);
			model.addAttribute("article", article);
			// 返回视图会被拦截如果匹配servlet拦截路径的话
			return "page";
		} catch (NoArticleWithThisName noArticleWithThisName) {
			return "404";
		}
	}
}