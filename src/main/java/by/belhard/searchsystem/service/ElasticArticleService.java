package by.belhard.searchsystem.service;

import by.belhard.searchsystem.model.ElasticArticle;

import java.util.List;

public interface ElasticArticleService {
    void save(ElasticArticle article);
    void saveAll(List<ElasticArticle> articles);
    ElasticArticle getById(long id);
    List<ElasticArticle> search(String searchRequest);

}
