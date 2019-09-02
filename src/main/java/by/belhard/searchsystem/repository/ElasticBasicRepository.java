package by.belhard.searchsystem.repository;

import org.elasticsearch.index.query.QueryBuilder;

import java.util.List;

public interface ElasticBasicRepository<T,ID> {

    void save(T document);
    T getById(ID id);
    List<T> search(QueryBuilder query);
    boolean isIndexExist(String index);
    void createIndex(String index);
    void deleteIndex(String index);
}
