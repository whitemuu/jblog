package me.nichijou.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Table;
import java.util.Date;

/**
 * Created by nichijou on 8/25/17.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Table(name = "articles")
public @Data
class SourceFileInfo extends Article {
	private String path;
	private String downloadUrl;//raw
	private String sha;
	private Date created;

	@Override
	public String toString() {
		return name + ":" + sha + ":" + htmlUrl;
	}
}
