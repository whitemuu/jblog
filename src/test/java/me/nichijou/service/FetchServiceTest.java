package me.nichijou.service;

import me.nichijou.mapper.SourceFileInfoMapper;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import static org.junit.Assert.*;

/**
 * Created by nichijou on 8/27/17.
 */
public class FetchServiceTest {
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
		fetchService.refreshArticles();
	}

	@Test
	public void fetchAndParseFile() throws Exception {
	}

}
