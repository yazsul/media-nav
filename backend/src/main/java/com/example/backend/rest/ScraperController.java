package com.example.backend.rest;

import java.io.IOException;
import java.util.List;

import com.example.backend.domain.Article;
import com.example.backend.service.ArticleScraperService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/articles")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class ScraperController {

    private final ArticleScraperService scraperService;

    // method with custom url
    @GetMapping("/from-url")
    public List<Article> getArticles(@RequestParam String url) throws IOException {
        return scraperService.getArticlesFromCustomUrl(url);
    }
}
