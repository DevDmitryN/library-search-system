package by.belhard.searchsystem.service.impl;

import by.belhard.searchsystem.model.ElasticArticle;
import by.belhard.searchsystem.repository.ElasticArticleRepository;
import by.belhard.searchsystem.service.ElasticArticleService;
import by.belhard.searchsystem.util.Constants;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ElasticArticleServiceImpl implements ElasticArticleService {

    @Autowired
    private ElasticArticleRepository elasticArticleRepository;

    @Override
    public void save(ElasticArticle elasticArticle) {
        elasticArticleRepository.save(elasticArticle);
    }

    public void saveAll(List<ElasticArticle> elasticArticles) {
        for (ElasticArticle elasticArticle : elasticArticles) {
            save(elasticArticle);
        }
    }

    @Override
    public ElasticArticle getById(long id)  {
        return elasticArticleRepository.getById(id);
    }

    @Override
    public List<ElasticArticle> search(String requestString) {
        return elasticArticleRepository.search(QueryBuilders.matchQuery(Constants.CONTENT,requestString));
    }



}
