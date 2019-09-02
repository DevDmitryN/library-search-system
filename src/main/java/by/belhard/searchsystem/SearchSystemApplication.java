package by.belhard.searchsystem;

import by.belhard.searchsystem.service.impl.ArticleServiceImpl;
import by.belhard.searchsystem.service.impl.ElasticArticleServiceImpl;
import by.belhard.searchsystem.util.mapper.ElasticArticleMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class SearchSystemApplication {

    @Autowired
    private ElasticArticleMapper elasticArticleMapper;
    @Autowired
    private ArticleServiceImpl articleService;
    @Autowired
    private ElasticArticleServiceImpl elasticArticleService;


    public static void main(String[] args) {
        SpringApplication.run(SearchSystemApplication.class, args);
    }

    @Bean
    public ObjectMapper objectMapper(){
        return new ObjectMapper();
    }

    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }

    @PostConstruct
    public void loadDocuments(){
        elasticArticleService.saveAll(elasticArticleMapper.toDtoList(articleService.getAll()));
    }




}
