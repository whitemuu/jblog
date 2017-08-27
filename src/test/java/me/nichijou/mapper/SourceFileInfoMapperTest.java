package me.nichijou.mapper;

import me.nichijou.pojo.SourceFileInfo;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * Created by nichijou on 8/27/17.
 */
public class SourceFileInfoMapperTest {
	private ApplicationContext context;

	private SourceFileInfoMapper mapper;
	@Before
	public void setUp() throws Exception {
		context= new ClassPathXmlApplicationContext("spring/applicationContext*");
		mapper = (SourceFileInfoMapper) context.getBean("sourceFileInfoMapper");
	}
	@Test
	public void selectNameAndSha() throws Exception {
		List<SourceFileInfo> sourceFileInfos = mapper.selectNameAndSha();
		System.out.println(sourceFileInfos);
	}

	@Test
	public void deleteArticles() throws Exception {
		Map<String,String> map = new HashMap<>();
		map.put("xxx.org","skdjfk");
		map.put("aaaa.org","skdjfk");
		map.put("abcd.org","skdjfk");
		int i = mapper.deleteArticles(map);
		System.out.println(i);
	}

	@Test
	public void addArticles() throws Exception {
		LinkedList<SourceFileInfo> list = new LinkedList<>();
		list.add(new SourceFileInfo("f.org","www.www","skdjfkj","bb cc","cccccccccccccc"));
		list.add(new SourceFileInfo("ii.org","www.www","sssssss","bb cc","ssssssssss"));
		mapper.addArticles(list);
	}
}