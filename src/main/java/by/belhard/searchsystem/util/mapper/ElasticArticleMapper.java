package by.belhard.searchsystem.util.mapper;

import by.belhard.searchsystem.entity.Article;
import by.belhard.searchsystem.entity.ElasticArticle;
import by.belhard.searchsystem.service.ArticleService;
import by.belhard.searchsystem.service.FileSystemService;
import by.belhard.searchsystem.service.impl.ArticleServiceImpl;
import by.belhard.searchsystem.service.impl.FileSystemServiceImpl;
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
                .addMappings(m -> m.skip(ElasticArticle::setTitle))
                .addMappings(m -> m.skip(ElasticArticle::setContent))
                .setPostConverter(toDtoConverter());
        mapper.createTypeMap(ElasticArticle.class,Article.class)
                .addMappings(m -> m.skip(Article::setTitle))
                .addMappings(m -> m.skip(Article::setDirectoryPath))
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
        destination.setTitle(source.getTitle().replace(".docx",""));
        String content = fileSystemService.getText(source)
                .replace("\n"," ")
                .replace("-","");
        destination.setContent(content);
    }

    private void mapSpecificFields(ElasticArticle source, Article destination){
        Article article = articleService.getById(source.getId());
        destination.setTitle(article.getTitle());
        destination.setDirectoryPath(article.getDirectoryPath());
    }
}
