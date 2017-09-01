package me.nichijou.controller;

import freemarker.template.TemplateException;
import me.nichijou.service.ArticleService;
import me.nichijou.service.FetchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

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
	public String refreshArticles(HttpServletRequest request) {
		try {
			this.fetchService.refreshArticles();
//			if (anyUpdate) {
//				// FIXME this is queried twice if any update
//				List<Article> articles = this.articleService.getAllTitles();
//				String webRoot = request.getSession().getServletContext().getRealPath("");
//				this.fetchService.updateRss(articles, webRoot);
//			}
			return "redirect:home.html";
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
