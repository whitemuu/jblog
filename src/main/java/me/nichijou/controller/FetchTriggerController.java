package me.nichijou.controller;

import me.nichijou.service.FetchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;

/**
 * Created by nichijou on 8/27/17.
 */
@Controller
public class FetchTriggerController {
	@Autowired
	private FetchService fetchService;

	@RequestMapping("fetch")
	public String refreshArticles() {
		try {
			this.fetchService.refreshArticles();
			return "redirect:home.html";
		} catch (IOException e) {
			e.printStackTrace();
			return "redirect:404";
		}
	}
}
