package com.literalura.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GutendexBookDto {

    private String title;

    @JsonAlias("languages")
    private java.util.List<String> languages;

    @JsonAlias("download_count")
    private Integer downloadCount;

    @JsonAlias("authors")
    private java.util.List<GutendexAuthorDto> authors;

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public java.util.List<String> getLanguages() { return languages; }
    public void setLanguages(java.util.List<String> languages) { this.languages = languages; }

    public Integer getDownloadCount() { return downloadCount; }
    public void setDownloadCount(Integer downloadCount) { this.downloadCount = downloadCount; }

    public java.util.List<GutendexAuthorDto> getAuthors() { return authors; }
    public void setAuthors(java.util.List<GutendexAuthorDto> authors) { this.authors = authors; }
}
