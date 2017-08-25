package me.nichijou.pojo;

import lombok.Data;

/**
 * Created by nichijou on 8/25/17.
 */
public @Data class SourceFileInfo {
    private String name;
    private String path;
    private String sha;
    private String downloadUrl;
    private String htmlUrl;
}
