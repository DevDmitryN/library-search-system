package by.belhard.searchsystem.model;

import lombok.Data;

@Data
public class ElasticArticle {
    private long id;
    private String name;
    private String content;
}
