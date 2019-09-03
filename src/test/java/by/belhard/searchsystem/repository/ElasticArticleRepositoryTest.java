package by.belhard.searchsystem.repository;

import by.belhard.searchsystem.model.ElasticArticle;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static by.belhard.searchsystem.util.Constants.CONTENT;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ElasticArticleRepositoryTest {

    @Autowired
    private ElasticArticleRepository elasticArticleRepository;

    @Test
    public void save(){
        ElasticArticle elasticArticle = new ElasticArticle();
        elasticArticle.setId(1000);
        elasticArticle.setName("test article");
        elasticArticle.setContent("test article content");
        elasticArticleRepository.save(elasticArticle);
        ElasticArticle savedArticle = elasticArticleRepository.getById(elasticArticle.getId());
        assertEquals(elasticArticle.getName(),savedArticle.getName());
    }

    @Test
    public void search(){
        String testRequest = "блокчейн";
        MatchQueryBuilder query = QueryBuilders.matchQuery(CONTENT, testRequest);
        List<ElasticArticle> search = elasticArticleRepository.search(query);
        assertFalse(search.isEmpty());
    }
}
