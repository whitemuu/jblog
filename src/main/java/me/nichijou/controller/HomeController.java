package me.nichijou.controller;

import me.nichijou.pojo.Article;
import me.nichijou.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * Created by nichijou on 8/25/17.
 */
@Controller
public class HomeController {
	@Autowired
	private ArticleService articleService;

	@RequestMapping("home")
	public String getHomePage(Model model) {
		List<Article> articles = this.articleService.getAllTitles();
		model.addAttribute("articles", articles);
		return "home";
	}
}
