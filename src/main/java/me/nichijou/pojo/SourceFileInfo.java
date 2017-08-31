package me.nichijou.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.Table;
import java.util.Date;

/**
 * Created by nichijou on 8/25/17.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Table(name = "articles")
@Data
public class SourceFileInfo extends Article {
	private String path;
	private String downloadUrl;//raw
	private String sha;
	private Date created;
}
