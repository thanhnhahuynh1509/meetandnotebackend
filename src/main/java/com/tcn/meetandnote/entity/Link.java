package com.tcn.meetandnote.entity;

public class Link {

    private String domain;
    private String url;
    private String title;
    private String description;
    private String image;
    private String altImage;
    private String videoURL;

    public Link(String domain, String url, String title,  String description, String image, String altImage, String videoURL) {
        this.domain = domain;
        this.url = url;
        this.title = title;
        this.description = description;
        this.image = image;
        this.altImage = altImage;
        this.videoURL = videoURL;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getAltImage() {
        return altImage;
    }

    public void setAltImage(String altImage) {
        this.altImage = altImage;
    }

    public String getVideoURL() {
        return videoURL;
    }

    public void setVideoURL(String videoURL) {
        this.videoURL = videoURL;
    }
}
