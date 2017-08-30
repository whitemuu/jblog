package me.nichijou.controller;

import freemarker.template.TemplateException;
import me.nichijou.pojo.Article;
import me.nichijou.service.ArticleService;
import me.nichijou.service.FetchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

/**
 * Created by nichijou on 8/27/17.
 */
@Controller
public class FetchTrigger {
	@Autowired
	private FetchService fetchService;

	@Autowired
	private ArticleService articleService;

	@RequestMapping("fetch")
	public String refreshArticles(Model model, HttpServletRequest request) {
		try {
			this.fetchService.refreshArticles();
//			return "redirect:home.html";
			String webRoot = request.getSession().getServletContext().getRealPath("");
			List<Article> articles = this.articleService.getAllTitles();
			this.fetchService.updateRss(articles,webRoot);
			model.addAttribute("articles", articles);
			return "home";
		} catch (IOException e) {
			e.printStackTrace();
			return "redirect:404";
		} catch (TemplateException e) {
			// fixme 这里是更新rss失败，没有区别开
			e.printStackTrace();
			return "redirect:404";
		}
	}
}
