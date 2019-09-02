package by.belhard.searchsystem.entity;

import lombok.Data;

@Data
public class ElasticArticle {
    private long id;
    private String title;
    private String content;
}
