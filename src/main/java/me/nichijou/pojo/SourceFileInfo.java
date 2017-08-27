package me.nichijou.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Table;

/**
 * Created by nichijou on 8/25/17.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Table(name = "articles")
public @Getter @Setter class SourceFileInfo {
	private String name;

	public SourceFileInfo() {
	}

	public SourceFileInfo(String name, String htmlUrl, String sha, String tags, String content) {

		this.name = name;
		this.htmlUrl = htmlUrl;
		this.sha = sha;
		this.tags = tags;
		this.content = content;
	}

	private String path;
	private String downloadUrl;//raw
	private String htmlUrl;//origin

	private String sha;

	private String title;
	private String tags;
	private String content;

	@Override
	public String toString() {
		return name+":"+sha+":"+htmlUrl;
	}
}
