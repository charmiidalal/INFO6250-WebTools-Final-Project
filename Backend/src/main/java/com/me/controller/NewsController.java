/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.me.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.me.dao.NewsDao;
import com.me.pojo.News;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author manushpatel
 */
@CrossOrigin(origins = "**", allowedHeaders = "*")
@Controller
public class NewsController {

    @CrossOrigin(origins = "**", allowedHeaders = "*")
    @RequestMapping(value = "/news.htm", method = RequestMethod.POST)
    public ResponseEntity<String> CreateNews(HttpServletRequest request, HttpServletResponse response, @RequestBody String body) throws JsonProcessingException {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Custom-Header", "foo");
        Map<String, String> result
                = new ObjectMapper().readValue(body, HashMap.class);
        String imageURL = result.get("imageurl");
        String sourceName = result.get("source");
        String title = result.get("title");
        String author = result.get("author");
        String description = result.get("description");
        String url = result.get("newsurl");
        String publishedAt = result.get("publishedat");
        NewsDao newsDao = new NewsDao();
        News news = new News();
        news.setUrlToImage(imageURL);
        news.setSourceName(sourceName);
        news.setTitle(title);
        news.setAuthor(author);
        news.setDescription(description);
        news.setUrlOther(url);
        news.setPublishedAt(publishedAt);
        newsDao.createNews(news);
        return new ResponseEntity<>("Success", headers, HttpStatus.OK);
    }
}
