package by.belhard.searchsystem.controller;

import by.belhard.searchsystem.service.ArticleService;
import by.belhard.searchsystem.service.ElasticArticleService;
import by.belhard.searchsystem.util.mapper.ElasticArticleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class SearchController {

    @Autowired
    private ElasticArticleService elasticArticleService;
    @Autowired
    private ArticleService articleService;
    @Autowired
    private ElasticArticleMapper articleMapper;

    @GetMapping("/")
    public String startPage(Model model) {
        model.addAttribute("result", articleService.getAll());
        return "search";
    }

    @GetMapping("/search")
    public String searchResult(@RequestParam(name = "request", required = false, defaultValue = "") String request, Model model) {
        if (request.equals("")) {
            return "redirect:/";
        } else {
            model.addAttribute("result", articleMapper.toEntityList(elasticArticleService.search(request)));
            return "search";
        }
    }

}
