package me.nichijou.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Table;
import java.util.Date;

/**
 * Created by nichijou on 8/25/17.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Table(name = "articles")
public @Getter @Setter class SourceFileInfo {
	private String name;


	private String path;
	private String downloadUrl;//raw
	private String htmlUrl;//origin

	private String sha;

	private Date created;

	private String title;
	private String tags;
	private String content;

	@Override
	public String toString() {
		return name+":"+sha+":"+htmlUrl;
	}
}
