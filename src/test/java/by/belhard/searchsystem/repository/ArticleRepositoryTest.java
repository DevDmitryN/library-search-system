package by.belhard.searchsystem.repository;

import by.belhard.searchsystem.model.Article;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ArticleRepositoryTest {

    @Autowired
    private ArticleRepository articleRepository;

    @Test
    public void getAll(){
        List<Article> articles = (List<Article>) articleRepository.findAll();
        assertFalse(articles.isEmpty());
    }

}
