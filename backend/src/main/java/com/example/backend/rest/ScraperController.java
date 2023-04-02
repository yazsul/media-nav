package com.example.backend.rest;

import java.util.List;

import com.example.backend.domain.Article;
import com.example.backend.service.ScraperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/articles")
public class ScraperController {

    @Autowired
    ScraperService scraperService;

    @RequestMapping(value="/by-author/{authorName}", method = RequestMethod.GET, produces = "application/json")
    public List<Article> searchArticlesByAuthor(@PathVariable("authorName") String authorName) {
        return scraperService.searchArticlesByAuthor(authorName);
    }

    //List all the authors
    @RequestMapping(value="/authors", method = RequestMethod.GET, produces = "application/json")
    public List<String> listAuthors() {
        return scraperService.listAuthors();
    }

    //Search articles by title
    @RequestMapping(value="/by-title/{title}", method = RequestMethod.GET, produces = "application/json")
    public List<Article> searchArticleByTitle(@PathVariable("title") String title) {
        return scraperService.searchArticleByTitle(title);
    }

    //search articles by description
    @RequestMapping(value="/by-desc/{desc}", method = RequestMethod.GET, produces = "application/json")
    public List<Article> searchArticleByDescription(@PathVariable("desc") String desc) {
        return scraperService.searchArticleByDescription(desc);
    }
}
