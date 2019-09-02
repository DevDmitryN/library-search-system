package by.belhard.searchsystem.service;

import by.belhard.searchsystem.service.impl.ArticleServiceImpl;
import by.belhard.searchsystem.service.impl.ElasticArticleServiceImpl;
import by.belhard.searchsystem.service.impl.FileSystemServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ElasticArticleServiceTest {

    @Autowired
    private FileSystemServiceImpl fileSystemService;
    @Autowired
    private ArticleServiceImpl articleService;
    @Autowired
    private ElasticArticleServiceImpl elasticArticleService;

    @Test
    public void saveDocument(){

    }
}
