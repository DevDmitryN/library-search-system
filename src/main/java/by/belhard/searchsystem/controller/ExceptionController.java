package by.belhard.searchsystem.controller;

import by.belhard.searchsystem.exception.ElasticsearchException;
import by.belhard.searchsystem.exception.OpeningFileException;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.NoSuchElementException;

@ControllerAdvice
@Slf4j
public class ExceptionController {

    @ExceptionHandler({NoSuchElementException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ModelAndView badRequest(RuntimeException e){
        log.error(e.getMessage());
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("exception",e);
        modelAndView.setViewName("error-page");
        return modelAndView;
    }

    @ExceptionHandler({ElasticsearchException.class, OpeningFileException.class,IllegalStateException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ModelAndView internalServerError(RuntimeException e){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("exception",e);
        modelAndView.setViewName("error-page");
        return modelAndView;
    }


}
