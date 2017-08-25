package me.nichijou.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by nichijou on 8/25/17.
 */
@Controller
public class HomeController {
	@RequestMapping("home")
	public String gethomePage(Model model){
		model.addAttribute("title","homepage");
		return "home";
	}
}
