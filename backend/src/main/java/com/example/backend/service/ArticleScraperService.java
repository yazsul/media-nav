package com.example.backend.service;

import java.util.ArrayList;
import java.util.List;

import com.example.backend.domain.Article;

import java.util.stream.Collectors;

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

    public List<String> listAuthors() {
        return articles.stream().map(a->a.getAuthorName())
                .distinct()
                .collect(Collectors.toList());
    }

    public List<Article> searchArticlesByAuthor(String authorName) {
        return articles.stream().filter(a->a.getAuthorName().equalsIgnoreCase(authorName))
                .collect(Collectors.toList());
    }

    public List<Article> searchArticleByTitle(String title) {
        return articles.stream().filter(a->a.getTitle().startsWith(title))
                .collect(Collectors.toList());
    }

    public List<Article> searchArticleByDescription(String desc) {
        return articles.stream().filter(a->a.getDescription().startsWith(desc))
                .collect(Collectors.toList());
    }
}
