package by.belhard.searchsystem.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ArticleController {

    @GetMapping("/articles/{id}")
    public String getArticle(@PathVariable("id") int id){
        return "articles/" + id;
    }
}
