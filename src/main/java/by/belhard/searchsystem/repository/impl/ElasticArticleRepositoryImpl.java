package by.belhard.searchsystem.repository.impl;

import by.belhard.searchsystem.entity.ElasticArticle;
import by.belhard.searchsystem.repository.ElasticArticleRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.client.indices.PutMappingRequest;
import org.elasticsearch.common.Strings;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.FetchSourceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static by.belhard.searchsystem.util.Constants.*;

@Component
@Slf4j
public class ElasticArticleRepositoryImpl implements ElasticArticleRepository {

    @Autowired
    private ObjectMapper mapper;
    @Autowired
    private RestHighLevelClient client;

    private FetchSourceContext fetchSourceContext;

    @Override
    public void save(ElasticArticle document) {
        IndexRequest request = new IndexRequest(ELASTICSEARCH_INDEX);
        try {
            request.id(Long.toString(document.getId()));
            request.source(mapper.writeValueAsString(document), XContentType.JSON);
            client.index(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new ElasticsearchException(e.getMessage());
        }
    }

    @Override
    public ElasticArticle getById(Long id) {
        GetRequest request = new GetRequest(ELASTICSEARCH_INDEX, Long.toString(id));
        request.fetchSourceContext(fetchSourceContext);
        try {
            GetResponse response = client.get(request, RequestOptions.DEFAULT);
            ElasticArticle document = mapper.convertValue(response.getSource(), ElasticArticle.class);
            return document;
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new ElasticsearchException(e.getMessage());
        }
    }

    @Override
    public List<ElasticArticle> search(QueryBuilder query) {
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(query);
        searchSourceBuilder.fetchSource(fetchSourceContext);
        SearchRequest request = new SearchRequest(ELASTICSEARCH_INDEX);
        request.source(searchSourceBuilder);

        try {
            return getHits(client.search(request, RequestOptions.DEFAULT));
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new ElasticsearchException(e.getMessage());
        }
    }

    @Override
    public boolean isIndexExist(String index) {
        GetIndexRequest request = new GetIndexRequest(index);
        try {
            return client.indices().exists(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new ElasticsearchException(e.getMessage());
        }
    }

    @Override
    public void createIndex(String index) {
        CreateIndexRequest request = new CreateIndexRequest(index);
        try {
            client.indices().create(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new ElasticsearchException(e.getMessage());
        }
    }

    @Override
    public void deleteIndex(String index){
        DeleteIndexRequest request = new DeleteIndexRequest(index);
        try {
            client.indices().delete(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new ElasticsearchException(e.getMessage());
        }
    }

    private List<ElasticArticle> getHits(SearchResponse response) {
        List<ElasticArticle> hits = new ArrayList<>();
        for (SearchHit hit : response.getHits().getHits()) {
            hits.add(mapper.convertValue(hit.getSourceAsMap(), ElasticArticle.class));
        }
        return hits;
    }

    @PostConstruct
    private void setMapping() {
        try {
            if (isIndexExist(ELASTICSEARCH_INDEX)) {
                deleteIndex(ELASTICSEARCH_INDEX);
            }
            createIndex(ELASTICSEARCH_INDEX);

            Map<String, String> idMapping = new HashMap<>();
            idMapping.put(TYPE, ID_TYPE);
            Map<String, String> textFieldsMapping = new HashMap<>();
            textFieldsMapping.put(TYPE, TEXT_FIELD_TYPE);
            textFieldsMapping.put(ANALYZER, ANALYZER_TYPE);

            Map<String, Object> properties = new HashMap<>();
            properties.put(ID, idMapping);
            properties.put(TITLE, textFieldsMapping);
            properties.put(CONTENT, textFieldsMapping);

            Map<String, Object> jsonMap = new HashMap<>();
            jsonMap.put(PROPERTIES, properties);

            PutMappingRequest request = new PutMappingRequest(ELASTICSEARCH_INDEX);
            request.source(jsonMap);
            client.indices().putMapping(request, RequestOptions.DEFAULT);

        }catch (IOException e){
            log.error(e.getMessage());
            throw new ElasticsearchException(e.getMessage());
        }
    }

    @PostConstruct
    private void setFetchSourceContext(){
        String[] includes = Strings.EMPTY_ARRAY;
        String[] excludes = new String[]{CONTENT};
        fetchSourceContext = new FetchSourceContext(true, includes, excludes);
    }

}
