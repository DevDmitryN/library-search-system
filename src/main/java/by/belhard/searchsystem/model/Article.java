package by.belhard.searchsystem.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "articles", schema = "library")
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "name")
    private String name;
    @Column(name = "url")
    private String url;

}
