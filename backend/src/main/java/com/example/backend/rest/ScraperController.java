package com.example.backend.rest;

import java.util.List;

import com.example.backend.domain.Article;
import com.example.backend.service.ScraperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/articles")
public class ScraperController {

    @Autowired
    ScraperService scraperService;

    @GetMapping(value="/by-author/{authorName}", produces = "application/json")
    public List<Article> searchArticlesByAuthor(@PathVariable("authorName") String authorName) {
        return scraperService.searchArticlesByAuthor(authorName);
    }

    //List all the authors
    @GetMapping(value="/authors", produces = "application/json")
    public List<String> listAuthors() {
        return scraperService.listAuthors();
    }

    //Search articles by title
    @GetMapping(value="/by-title/{title}", produces = "application/json")
    public List<Article> searchArticleByTitle(@PathVariable("title") String title) {
        return scraperService.searchArticleByTitle(title);
    }

    //search articles by description
    @GetMapping(value="/by-desc/{desc}", produces = "application/json")
    public List<Article> searchArticleByDescription(@PathVariable("desc") String desc) {
        return scraperService.searchArticleByDescription(desc);
    }
}