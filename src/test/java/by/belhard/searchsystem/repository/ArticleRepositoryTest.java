package by.belhard.searchsystem.repository;

import by.belhard.searchsystem.entity.Article;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ArticleRepositoryTest {

    @Autowired
    private ArticleRepository documentRepository;


    @Test
    public void getAll(){
        List<Article> documents = (List<Article>) documentRepository.findAll();
    }

}
