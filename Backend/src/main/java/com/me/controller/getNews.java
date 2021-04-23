/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.me.controller;

import com.google.gson.Gson;
import com.me.dao.NewsDao;
import com.me.pojo.News;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.simple.JSONArray;  
import org.json.JSONException;
import org.json.simple.JSONObject;    
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author manushpatel
 */
@CrossOrigin(origins = "**", allowedHeaders = "*")
@Controller
public class getNews {

    @CrossOrigin(origins = "**", allowedHeaders = "*")
    @RequestMapping(value = "/getAllNews.htm", method = RequestMethod.GET)
    public ResponseEntity<String> getAllNews(HttpServletRequest request, HttpServletResponse response) throws JSONException  {
        NewsDao newsDao = new NewsDao();
        ArrayList<News> newsList = newsDao.getAllNewsList();
        JSONObject obj=new JSONObject();  
        obj.put("status","ok");
        obj.put("totalResults",newsList.size());
        JSONArray arr = new JSONArray();  
        
        for(News n:newsList)
        {
            JSONObject o = new JSONObject();
            JSONObject source = new JSONObject();
            source.put("id", "null");
            source.put("name",n.getSource());
            o.put("source",source);
            o.put("author", n.getAuthor());
            o.put("title",n.getTitle());
            o.put("description",n.getDescription());
            o.put("url",n.getUrl());
            o.put("urlToImage",n.getUrlToImage());
            o.put("publishedAt",n.getPublishedAt());
            o.put("content",n.getContent());
            arr.add(o);
        }
        obj.put("articles",arr);
        return new ResponseEntity<>(new Gson().toJson(obj), HttpStatus.OK);

    }
}
