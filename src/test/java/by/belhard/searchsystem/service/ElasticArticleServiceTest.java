package by.belhard.searchsystem.service;

import by.belhard.searchsystem.model.ElasticArticle;
import by.belhard.searchsystem.service.impl.ElasticArticleServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ElasticArticleServiceTest {

    @Autowired
    private ElasticArticleServiceImpl elasticArticleService;

    @Test
    public void save(){
        ElasticArticle elasticArticle = new ElasticArticle();
        elasticArticle.setId(1000);
        elasticArticle.setName("test article");
        elasticArticle.setContent("test article content");
        elasticArticleService.save(elasticArticle);
        ElasticArticle savedArticle = elasticArticleService.getById(elasticArticle.getId());
        assertEquals(elasticArticle.getName(),savedArticle.getName());
    }

    @Test
    public void search(){
        String testRequest = "блокчейн";
        List<ElasticArticle> result = elasticArticleService.search(testRequest);
        assertFalse(result.isEmpty());
    }
}
