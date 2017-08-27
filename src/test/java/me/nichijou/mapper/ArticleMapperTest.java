package me.nichijou.mapper;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by nichijou on 8/27/17.
 */
public class ArticleMapperTest {
	private ApplicationContext context;
	private ArticleMapper mapper;
	@Before
	public void setUp() throws Exception {
		context= new ClassPathXmlApplicationContext("spring/applicationContext*");
		mapper = (ArticleMapper) context.getBean("articleMapper");
	}

	@Test
	public void getArticleByName() throws Exception {
		System.out.println(mapper.selectArticleByName("marriage.org"));
	}

}