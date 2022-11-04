package com.jnu.bookmanagementsystem.data;

import java.io.Serializable;

public class ShopItem implements Serializable {
    public ShopItem(String title, String author,String publisher,String translator, String pubDate,String isbn,int resourceId) {
        this.title = title;
        this.author=author;
        this.publisher=publisher;
        this.translator=translator;
        this.pubDate=pubDate;
        this.isbn=isbn;
        this.resourceId = resourceId;
    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getResourceId() {
        return resourceId;
    }

    public void setResourceId(int resourceId) {
        this.resourceId = resourceId;
    }

    private String title;

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    private String author;

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    private String publisher;

    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }
    private String pubDate;

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }
    private String isbn;

    public String getTranslator() {
        return translator;
    }

    public void setTranslator(String translator) {
        this.translator = translator;
    }

    private String translator;
    private int resourceId;
}

