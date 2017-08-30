package me.nichijou.pojo;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Table;

/**
 * Created by nichijou on 8/25/17.
 */
@Table(name = "articles")
public @Data
class Article {
	String name;
	String title;
	String tags;
	//	@Column(name = "description") // this is support only in hibernate
	String description; // 不要用desc 是sql关键字 会冲突
	String htmlUrl;
	String content;
	//	private Date lastModified;
	//	private Date created;
}
