package by.belhard.searchsystem.entity;

import lombok.Data;
import org.hibernate.annotations.GeneratorType;

import javax.persistence.*;

@Data
@Entity
@Table(name = "articles", schema = "library")
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "title")
    private String title;
    @Column(name = "directory_path")
    private String directoryPath;

    public String getPath(){
        return directoryPath + title;
    }
}
