package com.multimedia.cms.controller;

import com.multimedia.exceptions.ResolutionNotFound;
import com.multimedia.exceptions.WallpaperNotFound;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;

@ControllerAdvice
public class ExceptionAdvice {

    @ExceptionHandler
    public HttpEntity<String> handleError(IOException ex) {
        MultiValueMap<String, String> map = new HttpHeaders();
        map.add("Content-Type", "text/html;charset=UTF-8");
        return new ResponseEntity<String>(ex.getMessage(), map, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({WallpaperNotFound.class, ResolutionNotFound.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ModelAndView resourceNotFound(RuntimeException ex) {
        return new ModelAndView("WEB-INF/jsp/error/404.jsp");
    }

}
