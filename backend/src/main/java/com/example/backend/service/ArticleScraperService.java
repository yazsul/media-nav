package com.example.backend.service;

import com.example.backend.domain.Article;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ArticleScraperService {

    public Article getArticleFromCustomUrl(String url) throws IOException {
        Document document = Jsoup.connect(url).get();

        // get article content
        String content = getContentOfArticle(document);

        // get article title
        String title = getTitleOfArticle(document);

        // get publication name
        String publicationName = getPublicationNameOfArticle(document);

        // get article author
        String author = getAuthorOfArticle(document);

        // get date of article
        String date = getDateOfArticle(document);

        return new Article(content, title, url, publicationName, author, date);
    }

    private String getContentOfArticle(Document document) {
        Elements contentElements = document.select("div.article-content, article, div.blog-post");
        StringBuilder articleContent = new StringBuilder();
        for (Element el : contentElements) {
            articleContent.append(el.text()).append("\n");
        }
        return articleContent.toString();
    }

    private String getTitleOfArticle(Document document) {
        String title = document.title();
        // If title is empty, select specific elements that contain the article's title
        if (title.isEmpty()) {
            // Select the elements that may contain the article's title
            Elements titleElements = document.select("h1.article-title, h1.title, h2.title, h3.title");
            // Extract the title from the elements
            for (Element el : titleElements) {
                title = el.text();
                if (!title.isEmpty()) {
                    break;
                }
            }
        }
        return title;
    }

    private String getPublicationNameOfArticle(Document document) {
        Elements publicationElements = document.select("meta[property='og:site_name'], meta[name='og:site_name'], "
                + "meta[property='og:article:publisher'], meta[name='og:article:publisher'], meta[property='og:site_name']");
        String publicationName = "";
        for (Element el : publicationElements) {
            publicationName = el.attr("content");
            if (!publicationName.isEmpty()) {
                break;
            }
        }
        return publicationName;
    }

    private String getAuthorOfArticle(Document document) {
        Elements authorElements = document.select("span.author, div.author, meta[name='author'], meta[property='author'],"
                + "span.byline-author-name");
        // Extract the author from the elements
        String author = "";
        for (Element el : authorElements) {
            author = el.text();
            if (!author.isEmpty()) {
                break;
            }
        }
        return author;
    }

    private String getDateOfArticle(Document document) {
        Elements dateElements = document.select("time, span.date, span.time, div.date, div.time, "
                + "meta[property='article:published_time'], meta[property='og:article:published_time']");
        // Extract the date from the elements
        String date = "";
        for (Element el : dateElements) {
            date = el.attr("datetime");
            if (!date.isEmpty()) {
                break;
            }
            date = el.text();
            if (!date.isEmpty()) {
                break;
            }
        }
        return date;
    }

    public String sendArticleContent(String content) {
        return content;
    }
}
