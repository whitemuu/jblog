package me.nichijou.pojo;

import lombok.Data;

import javax.persistence.Table;

/**
 * Created by nichijou on 8/25/17.
 */
@Table(name = "articles")
public @Data
class Article {
	private String title;
	private String tags;
	private String htmlUrl;
	private String content;
	//	private Date lastModified;
	//	private Date created;
}
