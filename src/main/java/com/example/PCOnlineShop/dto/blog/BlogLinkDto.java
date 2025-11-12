package com.example.PCOnlineShop.dto.blog;

public class BlogLinkDto {
    private String title;
    private String link;
    private String imageUrl;
    private String summary; // optional

    public BlogLinkDto() {}

    public BlogLinkDto(String title, String link, String imageUrl, String summary) {
        this.title = title;
        this.link = link;
        this.imageUrl = imageUrl;
        this.summary = summary;
    }

    // getters & setters
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getLink() { return link; }
    public void setLink(String link) { this.link = link; }
    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
    public String getSummary() { return summary; }
    public void setSummary(String summary) { this.summary = summary; }
}
