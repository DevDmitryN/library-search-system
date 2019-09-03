package by.belhard.searchsystem.repository;

import by.belhard.searchsystem.model.Article;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleRepository extends CrudRepository<Article,Long> {

}
