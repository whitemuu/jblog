package me.nichijou.service;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by nichijou on 8/27/17.
 */
public class FetchServiceTest {
	@Test
	public void updateRss() throws Exception {
		String dir = System.getProperty("user.dir");
		System.out.println(dir);
	}

	@Test
	public void jsoup() throws Exception {
		Document document = Jsoup.connect("https://github.com/whitemuu/blog/blob/master/articles/speedUpSSH.org").get();
		Elements article = document.select("#readme > article");
		Elements select = article.select("article > h1:nth-child(1)");
		select.remove();
		System.out.println(article.toString());
	}

	private ApplicationContext context;
	FetchService fetchService;

	@Before
	public void setUp() throws Exception {
		context = new ClassPathXmlApplicationContext("spring/applicationContext*");
		fetchService = (FetchService) context.getBean("fetchService");
	}

	@Test
	public void doGet() throws Exception {
	}

	@Test
	public void getSourceFileInfos() throws Exception {
		System.out.println(fetchService.getSourceFileInfos());
	}

	@Test
	public void refreshArticles() throws Exception {
		fetchService.syndAllArticles();
	}

	@Test
	public void fetchAndParseFile() throws Exception {
	}

}
