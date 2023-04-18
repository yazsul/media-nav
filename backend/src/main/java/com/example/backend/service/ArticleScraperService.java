package com.example.backend.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.example.backend.domain.Article;

import java.util.stream.Collectors;

import com.example.backend.util.ScraperHelper;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ArticleScraperService {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private List<Article> articles = new ArrayList<>();

    @Value("${newspaper.thehindu.url}")
    private String newspaperUrl;

    @Value("${newspaper.thehindu.parse.timeout.ms}")
    Integer parseTimeoutMillis;

    @Value("${newspaper.thehindu.article.authortag}")
    String authorTagName;

    @Value("${newspaper.thehindu.article.titletag}")
    String titleTagName;

    @Value("${newspaper.thehindu.article.desctag}")
    String descTagName;

    @Value("#{'${newspaper.thehindu.article.searchtags}'.split(',')}")
    List<String> articleLinksSearchTags;

    @PostConstruct
    public void loadContents() {
        LOGGER.info("loadContents()...start");
        articles.clear();
        List<String> articleDetailsSearchTags = Arrays.asList(authorTagName, titleTagName, descTagName);
        ScraperHelper scraperHelper = new ScraperHelper(newspaperUrl, parseTimeoutMillis, articleDetailsSearchTags, articleLinksSearchTags);

        LOGGER.info("Extracting article details...start");

        scraperHelper.fetchAllLinkMetaDetailsFromPage()
                .thenAccept(list-> list.stream().filter(map->map.get(authorTagName)!=null && map.get(authorTagName).length()>0)
                        .forEach(map-> articles.add(new Article(map.get(titleTagName), map.get(descTagName), map.get(authorTagName)))));

        LOGGER.info("loadContents()...completed");
    }

    public List<String> listAuthors() {
        return articles.stream().map(Article::getAuthorName)
                .distinct()
                .collect(Collectors.toList());
    }

    public List<Article> searchArticlesByAuthor(String authorName) {
        return articles.stream().filter(a->a.getAuthorName().equalsIgnoreCase(authorName)).toList();
    }

    public List<Article> searchArticleByTitle(String title) {
        return articles.stream().filter(a->a.getTitle().startsWith(title)).toList();
    }

    public List<Article> searchArticleByDescription(String desc) {
        return articles.stream().filter(a->a.getDescription().startsWith(desc)).toList();
    }
}
