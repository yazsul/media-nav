package com.example.backend.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.example.backend.domain.Article;
import com.example.backend.util.ScraperHelper;
import jakarta.annotation.PostConstruct;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
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

        /*scraperHelper.fetchAllLinkMetaDetailsFromPage()
                .thenAccept(list-> list.stream().filter(map->map.get(authorTagName)!=null && map.get(authorTagName).length()>0)
                        .forEach(map-> articles.add(new Article(map.get(titleTagName), map.get(descTagName), map.get(authorTagName)))));*/

        LOGGER.info("loadContents()...completed");
    }

    public List<String> listAuthors() {
        return articles.stream().map(Article::getAuthor).distinct().toList();
    }

    public List<Article> searchArticlesByAuthor(String authorName) {
        return articles.stream().filter(a->a.getAuthor().equalsIgnoreCase(authorName)).toList();
    }

    public List<Article> searchArticleByTitle(String title) {
        return articles.stream().filter(a->a.getTitle().startsWith(title)).toList();
    }

    public List<Article> searchArticleByDescription(String desc) {
        return articles.stream().filter(a->a.getPublication().startsWith(desc)).toList();
    }

    public List<Article> getFromCustomUrl(String url) throws IOException {
        List<Article> content = new ArrayList<>();
        Document document = Jsoup.connect(url).get();

        // get article content
        Elements contentElements = document.select("div.article-content, article, div.blog-post");
        StringBuilder articleContent = new StringBuilder();
        for (Element el : contentElements) {
            articleContent.append(el.text()).append("\n");
        }

        // get article title
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

        // get publication name
        Elements publicationElements = document.select("meta[property='og:site_name'], meta[name='og:site_name'], "
                + "meta[property='og:article:publisher'], meta[name='og:article:publisher'], meta[property='og:site_name']");
        String publicationName = "";
        for (Element el : publicationElements) {
            publicationName = el.attr("content");
            if (!publicationName.isEmpty()) {
                break;
            }
        }

        // get article author
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

        // get date of article
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

        content.add(new Article(articleContent.toString(), title, url, publicationName, author, date));

        return content;
    }
}
