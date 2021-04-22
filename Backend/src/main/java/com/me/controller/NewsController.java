/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.me.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.me.dao.NewsDao;
import com.me.pojo.Articles;
import com.me.pojo.Example;
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

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.me.pojo.Example;
import com.me.pojo.Source;
import com.me.service.getNewsArticles;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

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
    
    @CrossOrigin(origins = "**", allowedHeaders = "*")
    @RequestMapping(value="/newsAll.htm", method = RequestMethod.GET)
    public ResponseEntity<String> sendRefinedUpdate(@RequestParam String country, @RequestParam String category, @RequestParam String source, @RequestParam String q) throws JSONException  {
        String urlString = "https://newsapi.org/v2/top-headlines?apiKey=4a0bee367c704198a2335c20c1b7b600&country=" + country + "&category=" + category + "&sources=" + source + "&q="+q+"";
        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject(urlString, String.class);
        JSONObject root = new JSONObject(result);
        return new ResponseEntity<>(root.toString(), HttpStatus.OK);
    }

    @CrossOrigin(origins = "**", allowedHeaders = "*")
    @RequestMapping(value="/GetSourceList.htm", method = RequestMethod.GET)
    public ResponseEntity<String> sendSourceList() throws JSONException  {
        String urlString = "https://newsapi.org/v2/sources?apiKey=4a0bee367c704198a2335c20c1b7b600";
        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject(urlString, String.class);
        JSONObject root = new JSONObject(result);
        return new ResponseEntity<>(root.toString(), HttpStatus.OK);
    }
}
