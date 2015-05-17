package com.feedmybrain.feedlyclient;

import android.util.Log;

import com.feedmybrain.util.Constants;

import java.io.IOException;
import java.util.LinkedList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Created by blackecho on 16/05/15.
 */
public class Article {

    private String id;
    private LinkedList<String> keywords;
    private String title;
    private Long published;
    private String author;
    private LinkedList<Category> categories;
    private String summary;
    private String website;
    private String href;
    private String content;

    public Article(String id, LinkedList<String> keywords, String title, Long published, String author, LinkedList<Category> categories, String summary,
                   String website, String href, String content) {
        this.id = id;
        this.keywords = keywords;
        this.title = title;
        this.published = published;
        this.author = author;
        this.categories = categories;
        this.summary = summary;
        this.website = website;
        this.href = href;
        this.content = content;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LinkedList<String> getKeywords() {
        return keywords;
    }

    public void setKeywords(LinkedList<String> keywords) {
        this.keywords = keywords;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getPublished() {
        return published;
    }

    public void setPublished(Long published) {
        this.published = published;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public LinkedList<Category> getCategories() {
        return categories;
    }

    public void setCategories(LinkedList<Category> categories) {
        this.categories = categories;
    }

    public String getSummary() {
        Document raw_content = Jsoup.parse(content);
        Elements paragraphs = raw_content.select("p");
        String article_content = "";
        if (paragraphs.size() != 0) {
            article_content = "";
            for (Element p : paragraphs)
                article_content += p.text();
        } else {
            article_content = "Sorry, no preview available for this article";
        }
        return article_content;
    }

    public void setSummary(String content) {
        this.content = content;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String toString(){
        String res ="";
        res = res + "Article from: " + getWebsite() + ". From author: " + getAuthor() + ". " +
                "Title: " + getTitle() + ". ";
        return res;
    }

    public String toFullArticle() {
        String article_content = "";
        try {
            Document raw_content = Jsoup.connect(href).get();
            Elements paragraphs = raw_content.select("p");
            if (paragraphs.size() != 0) {
                article_content = "";
                for (Element p : paragraphs)
                    article_content += p.text();
            } else {
                article_content = "Sorry, no content available for this article";
            }
        } catch (IOException ie) {
            ie.printStackTrace();
        }
        return article_content;
    }

}
