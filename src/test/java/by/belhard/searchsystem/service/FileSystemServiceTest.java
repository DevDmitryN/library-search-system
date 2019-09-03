package by.belhard.searchsystem.service;

import by.belhard.searchsystem.model.Article;
import by.belhard.searchsystem.service.impl.FileSystemServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FileSystemServiceTest {

    @Autowired
    private FileSystemServiceImpl fileSystemService;
    @Autowired
    private ArticleService articleService;

    @Test
    public void getText(){
        Article article = articleService.getAll().get(0);
        String content = fileSystemService.getText(article);
        assertNotNull(content);
        assertFalse(content.isEmpty());
    }

}
