/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.me.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.me.dao.NewsDao;
import com.me.pojo.News;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author manushpatel
 */
@CrossOrigin(origins = "**", allowedHeaders = "*")
@Controller
public class ScrapNews {

    @CrossOrigin(origins = "**", allowedHeaders = "*")
    @RequestMapping(value = "/scrap.htm", method = RequestMethod.GET)
    public ResponseEntity<String> runScrapNews(@RequestParam String page,HttpServletRequest request, HttpServletResponse response) throws JsonProcessingException, ProtocolException, MalformedURLException, IOException, JSONException {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Custom-Header", "foo");
        String urlString = "http://newsapi.org/v2/everything?q=usa&pageSize=20&sortBy=publishedAt&apiKey=4a0bee367c704198a2335c20c1b7b600&page="+page;

        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject(urlString, String.class);

        JSONObject root = new JSONObject(result);
        String id = null;
        String name = null;
        String author = null;
        String title = null;
        String description = null;
        String urlother = null;
        String urlToImage = null;
        String publishedAt = null;
        String content = null;
//        status = root.getString("status");
//        totalResults = root.getInt("totalResults");

        JSONArray articlesObject = root.getJSONArray("articles");
        NewsDao nd = new NewsDao();

        for (int i = 0; i < articlesObject.length(); i++) {
            JSONObject arrayElement = articlesObject.getJSONObject(i);

            JSONObject sourceother = arrayElement.getJSONObject("source");
            if (!sourceother.isNull("id")) {
                id = sourceother.getString("id");
            } else {
                id = null;
            }

            name = sourceother.getString("name");

            if (!arrayElement.isNull("author")) {
                author = arrayElement.getString("author");
            } else {
                author = null;
            }

            title = arrayElement.getString("title");

            if (!arrayElement.isNull("description")) {
                description = arrayElement.getString("description");
            } else {
                description = null;
            }

            urlother = arrayElement.getString("url");

            if (!arrayElement.isNull("urlToImage")) {
                urlToImage = arrayElement.getString("urlToImage");
            } else {
                urlToImage = null;
            }

            publishedAt = arrayElement.getString("publishedAt");

            if (!arrayElement.isNull("content")) {
                content = arrayElement.getString("content");
            } else {
                content = null;
            }
            News news = new News();
            news.setAuthor(author);
            news.setContent(content);
            news.setDescription(description);
            news.setPublishedAt(publishedAt);
            news.setTitle(title);
            news.setUrl(urlother);
            news.setUrlToImage(urlToImage);
            news.setSource(name);
            nd.createNews(news);
        }
        return new ResponseEntity<>("Success", headers, HttpStatus.OK);
    }
}
