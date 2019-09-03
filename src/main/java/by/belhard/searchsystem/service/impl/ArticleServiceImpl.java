package by.belhard.searchsystem.service.impl;

import by.belhard.searchsystem.model.Article;
import by.belhard.searchsystem.repository.ArticleRepository;
import by.belhard.searchsystem.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleRepository documentRepository;

    public void save(Article article){
        documentRepository.save(article);
    }

    public Article getById(long id){
        return documentRepository.findById(id).get();
    }

    public List<Article> getAll(){
        return (List<Article>) documentRepository.findAll();
    }


}
