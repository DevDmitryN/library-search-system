package by.belhard.searchsystem.util.mapper;

import by.belhard.searchsystem.model.Article;
import by.belhard.searchsystem.model.ElasticArticle;
import by.belhard.searchsystem.service.ArticleService;
import by.belhard.searchsystem.service.FileSystemService;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class ElasticArticleMapper extends AbstractMapper<Article, ElasticArticle> {

    @Autowired
    private ModelMapper mapper;
    @Autowired
    private FileSystemService fileSystemService;
    @Autowired
    private ArticleService articleService;

    public ElasticArticleMapper() {
        super(Article.class, ElasticArticle.class);
    }

    @PostConstruct
    public void setupMapper(){
        mapper.createTypeMap(Article.class,ElasticArticle.class)
                .addMappings(m -> m.skip(ElasticArticle::setName))
                .addMappings(m -> m.skip(ElasticArticle::setContent))
                .setPostConverter(toDtoConverter());
        mapper.createTypeMap(ElasticArticle.class,Article.class)
                .addMappings(m -> m.skip(Article::setName))
                .addMappings(m -> m.skip(Article::setUrl))
                .setPostConverter(toEntityConverter());
    }

    private Converter<Article,ElasticArticle> toDtoConverter(){
        return mappingContext -> {
            Article source = mappingContext.getSource();
            ElasticArticle destination = mappingContext.getDestination();
            mapSpecificFields(source,destination);
            return destination;
        };
    }

    private Converter<ElasticArticle,Article> toEntityConverter(){
        return mappingContext -> {
            ElasticArticle source = mappingContext.getSource();
            Article destination = mappingContext.getDestination();
            mapSpecificFields(source,destination);
            return destination;
        };
    }

    private void mapSpecificFields(Article source, ElasticArticle destination){
        destination.setName(source.getName());
        String content = fileSystemService.getText(source);
        destination.setContent(content);
    }

    private void mapSpecificFields(ElasticArticle source, Article destination){
        Article article = articleService.getById(source.getId());
        destination.setName(article.getName());
        destination.setUrl(article.getUrl());
    }
}
