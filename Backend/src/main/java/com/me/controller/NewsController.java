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
import com.me.pojo.News;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author charmidalal
 */
@CrossOrigin(origins = "**", allowedHeaders = "*")
@Controller
public class NewsController {

    @Autowired
    NewsDao newsDao;

    /* Stores news created by the admins */
    @CrossOrigin(origins = "**", allowedHeaders = "*")
    @RequestMapping(value = "/CreateNews.htm", method = RequestMethod.POST)
    public ResponseEntity<String> CreateNews(@Autowired News news, HttpServletRequest request, HttpServletResponse response, @RequestBody String body) throws JsonProcessingException, JSONException, ParseException {
        Map<String, String> result
                = new ObjectMapper().readValue(body, HashMap.class);
        String imageURL = result.get("imageURL");
        String sourceName = result.get("source");
        String title = result.get("title");
        String author = result.get("username");
        String content = result.get("content");
        String description = result.get("description");
        String url = result.get("newsURL");
        String publishedAt = result.get("publishedAt");
        publishedAt = publishedAt.split("T")[0];
        SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-mm-dd");
        Date pubDate = formatter1.parse(publishedAt);
        news.setUrlToImage(imageURL);
        news.setSource(sourceName);
        news.setTitle(title);
        news.setAuthor(author);
        news.setContent(content);
        news.setDescription(description);
        news.setUrl(url);
        news.setPublishedAt(pubDate);
        news.setCategory("Science");
        JSONObject o = new JSONObject();
        newsDao.createNews(news);
        o.put("_id", news.getNewsID());
        o.put("source", sourceName);
        o.put("content", content);
        o.put("title", title);
        o.put("description", description);
        o.put("url", url);
        o.put("urlToImage", imageURL);
        o.put("publishedAt", publishedAt);
        return new ResponseEntity<>(o.toString(), HttpStatus.OK);
    }

    /* Gets News by Category, Source and Country */
    @CrossOrigin(origins = "**", allowedHeaders = "*")
    @RequestMapping(value = "/newsAll.htm", method = RequestMethod.GET)
    public ResponseEntity<String> sendRefinedUpdate(@RequestParam String country, @RequestParam String category, @RequestParam String source, @RequestParam String q) throws JSONException, ParseException {
        String urlString = "https://newsapi.org/v2/top-headlines?apiKey=afbc440a2ed6400d8a7a875b87ba539c&country=" + country + "&category=" + category + "&sources=" + source + "&q=" + q + "";
        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject(urlString, String.class);
        newsDao.addScrapedNews(result, category, country, source);
        ArrayList<News> newsList = newsDao.getAllNewsListByFilter(category, country, source, q);
        org.json.simple.JSONObject obj = newsDao.fetchScrapedNews(newsList);
        return new ResponseEntity<>(new Gson().toJson(obj), HttpStatus.OK);
    }

    /* Fetch available News Source List */
    @CrossOrigin(origins = "**", allowedHeaders = "*")
    @RequestMapping(value = "/GetSourceList.htm", method = RequestMethod.GET)
    public ResponseEntity<String> sendSourceList() throws JSONException {
        String urlString = "https://newsapi.org/v2/sources?apiKey=afbc440a2ed6400d8a7a875b87ba539c";
        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject(urlString, String.class);
        JSONObject root = new JSONObject(result);
        return new ResponseEntity<>(root.toString(), HttpStatus.OK);
    }

    /* Gets News by Author to show in Post News Section */
    @CrossOrigin(origins = "**", allowedHeaders = "*")
    @RequestMapping(value = "/GetNewsByAuthor.htm", method = RequestMethod.GET)
    public ResponseEntity<String> GetNewsByAuthor(@RequestParam String username) throws JSONException {
        ArrayList<News> newsList = newsDao.getNewsByAuthor(username);
        JSONArray arr = new JSONArray();
        if (newsList != null) {
            for (News n : newsList) {
                org.json.simple.JSONObject o = new org.json.simple.JSONObject();
                o.put("_id", n.getNewsID());
                o.put("source", n.getSource());
                o.put("author", n.getAuthor());
                o.put("title", n.getTitle());
                o.put("description", n.getDescription());
                o.put("url", n.getUrl());
                o.put("urlToImage", n.getUrlToImage());
                o.put("publishedAt", n.getPublishedAt());
                o.put("content", n.getContent());
                arr.add(o);
            }
        }
        return new ResponseEntity<>(new Gson().toJson(arr), HttpStatus.OK);
    }

    /* Deletes news created by admins */
    @CrossOrigin(origins = "**", allowedHeaders = "*")
    @RequestMapping(value = "/DeleteNewsByAuthor.htm", method = RequestMethod.POST)
    public ResponseEntity<String> DeleteNewsByAuthor(@RequestParam String username, @RequestParam String newsID) throws JSONException {
        News news = newsDao.getNews(Integer.parseInt(newsID));
        if (news != null) {
            newsDao.deleteNewsByID(news);
            ArrayList<News> newsList = newsDao.getNewsByAuthor(username);
            JSONArray arr = new JSONArray();
            if (newsList != null) {
                for (News n : newsList) {
                    org.json.simple.JSONObject o = new org.json.simple.JSONObject();
                    o.put("_id", n.getNewsID());
                    o.put("source", n.getSource());
                    o.put("author", n.getAuthor());
                    o.put("title", n.getTitle());
                    o.put("description", n.getDescription());
                    o.put("url", n.getUrl());
                    o.put("urlToImage", n.getUrlToImage());
                    o.put("publishedAt", n.getPublishedAt());
                    o.put("content", n.getContent());
                    arr.add(o);
                }
            }
            return new ResponseEntity<>(new Gson().toJson(arr), HttpStatus.OK);
        }
        return new ResponseEntity<>("No News Found", HttpStatus.UNAUTHORIZED);
    }

    /* Fetches headlines of News*/
    @CrossOrigin(origins = "**", allowedHeaders = "*")
    @RequestMapping(value = "/getAllNews.htm", method = RequestMethod.GET)
    public ResponseEntity<String> getAllNews(HttpServletRequest request, HttpServletResponse response) throws JSONException, ParseException {
        String urlString = "http://newsapi.org/v2/everything?q=covid&pageSize=50&sortBy=publishedAt&apiKey=afbc440a2ed6400d8a7a875b87ba539c";
        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject(urlString, String.class);
        newsDao.addScrapedNews(result, "", "", "");
        ArrayList<News> newsList = newsDao.getAllNewsList();
        org.json.simple.JSONObject obj = newsDao.fetchScrapedNews(newsList);
        return new ResponseEntity<>(new Gson().toJson(obj), HttpStatus.OK);
    }

    /* Scraps news  page by page from admin post news section*/
    @CrossOrigin(origins = "**", allowedHeaders = "*")
    @RequestMapping(value = "/scrap.htm", method = RequestMethod.GET)
    public ResponseEntity<String> runScrapNews(@RequestParam String page, HttpServletRequest request, HttpServletResponse response, @Autowired News news) throws JsonProcessingException, ProtocolException, MalformedURLException, IOException, JSONException, ParseException {
        String urlString = "http://newsapi.org/v2/everything?q=usa&pageSize=20&sortBy=publishedAt&apiKey=afbc440a2ed6400d8a7a875b87ba539c&page=" + page;
        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject(urlString, String.class);
        newsDao.addScrapedNews(result, "", "", "");
        ArrayList<News> newsList = newsDao.getAllNewsList();
        org.json.simple.JSONObject obj = newsDao.fetchScrapedNews(newsList);
        return new ResponseEntity<>(new Gson().toJson(obj), HttpStatus.OK);
    }
}
