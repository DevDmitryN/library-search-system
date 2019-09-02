package by.belhard.searchsystem.service.impl;

import by.belhard.searchsystem.entity.Article;
import by.belhard.searchsystem.exception.OpeningFileException;
import by.belhard.searchsystem.service.FileSystemService;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.stereotype.Service;

import java.io.*;

@Service
@Slf4j
public class FileSystemServiceImpl implements FileSystemService {

    @Override
    public String getText(Article article){
        try (FileInputStream inputStream = new FileInputStream(new File(article.getPath()));){
            XWPFDocument document = new XWPFDocument(inputStream);
            return new XWPFWordExtractor(document).getText();
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new OpeningFileException(e.getMessage());
        }
    }
}
