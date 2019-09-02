package by.belhard.searchsystem.service.impl;

import by.belhard.searchsystem.entity.Article;
import by.belhard.searchsystem.exception.OpeningFileException;
import by.belhard.searchsystem.service.FileSystemService;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;

import java.io.*;

import static by.belhard.searchsystem.util.Constants.PATH_TO_VIEW;

@Service
@Slf4j
public class FileSystemServiceImpl implements FileSystemService {
    @Override
    public String getText(Article article) {
        try {
            Document document = Jsoup.parse(new File(PATH_TO_VIEW + article.getUrl() + ".html"),"UTF-8");
            return document.body().text();
        } catch (IOException e) {
            e.printStackTrace();
            throw new OpeningFileException(e.getMessage());
        }
    }

//    @Override
//    public String getText(Article article){
//        try (FileInputStream inputStream = new FileInputStream(new File(article.getUrl()))){
//            XWPFDocument document = new XWPFDocument(inputStream);
//            return new XWPFWordExtractor(document).getText();
//        } catch (IOException e) {
//            log.error(e.getMessage());
//            throw new OpeningFileException(e.getMessage());
//        }
//    }
}
