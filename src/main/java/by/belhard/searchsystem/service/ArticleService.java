package by.belhard.searchsystem.service;

import by.belhard.searchsystem.model.Article;

import java.util.List;

public interface ArticleService {

    void save(Article article);
    Article getById(long id);
    List<Article> getAll();
}
