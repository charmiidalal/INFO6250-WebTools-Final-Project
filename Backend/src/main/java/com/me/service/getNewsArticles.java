/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.me.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.me.pojo.Articles;
import com.me.pojo.Example;
import com.me.pojo.Source;
import org.json.JSONException;

@Service
public class getNewsArticles extends MappingJackson2HttpMessageConverter {

    private static getNewsArticles ourInstance = new getNewsArticles();

    public static getNewsArticles getInstance() {
        return ourInstance;
    }

    private getNewsArticles() {
        setPrettyPrint(true);
    }

    public static List<Example> sendRefinedUpdate(String country, String category, String source123) throws IOException, JSONException {

        String urlString = "https://newsapi.org/v2/top-headlines?apiKey=4a0bee367c704198a2335c20c1b7b600&country=" + country + "&category=" + category + "&source=" + source123 + "";

        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject(urlString, String.class);

        JSONObject root = new JSONObject(result);

        String status = null;
        Integer totalResults = null;
        String id = null;
        String name = null;
        String author = null;
        String title = null;
        String description = null;
        String urlother = null;
        String urlToImage = null;
        String publishedAt = null;
        String content = null;

        List<Example> newsList = new ArrayList<>();

        status = root.getString("status");
        totalResults = root.getInt("totalResults");

        JSONArray articlesObject = root.getJSONArray("articles");

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

            Example emp = new Example();
            Articles articles = new Articles();
            Source source = new Source();

            emp.setStatus(status);
            emp.setTotalResults(totalResults);
            articles.setAuthor(author);
            articles.setContent(content);
            articles.setDescription(description);
            articles.setPublishedAt(publishedAt);
            articles.setTitle(title);
            articles.setUrlOther(urlother);
            articles.setUrlToImage(urlToImage);
            source.setId(id);
            source.setName(name);

            articles.setSource(source);
            emp.setArticles(articles);
            newsList.add(emp);
        }
        return newsList;
    }

    public static List<Example> sendCategorizedUpdate(String country, String category) throws IOException, JSONException {

        String urlString = "https://newsapi.org/v2/top-headlines?apiKey=a24d98f562554d239d33c2f9d3da0983&country=" + country + "&category=" + category + "";

        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject(urlString, String.class);

        JSONObject root = new JSONObject(result);

        String status = null;
        Integer totalResults = null;
        String id = null;
        String name = null;
        String author = null;
        String title = null;
        String description = null;
        String urlother = null;
        String urlToImage = null;
        String publishedAt = null;
        String content = null;

        List<Example> newsList = new ArrayList<>();

        status = root.getString("status");
        totalResults = root.getInt("totalResults");

        JSONArray articlesObject = root.getJSONArray("articles");

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

            Example emp = new Example();
            Articles articles = new Articles();
            Source source = new Source();

            emp.setStatus(status);
            emp.setTotalResults(totalResults);
            articles.setAuthor(author);
            articles.setContent(content);
            articles.setDescription(description);
            articles.setPublishedAt(publishedAt);
            articles.setTitle(title);
            articles.setUrlOther(urlother);
            articles.setUrlToImage(urlToImage);
            source.setId(id);
            source.setName(name);

            articles.setSource(source);
            emp.setArticles(articles);
            newsList.add(emp);
        }
        return newsList;

    }

    public static List<Example> sendSourcedUpdate(String country, String source123) throws IOException, JSONException {

        String urlString = "https://newsapi.org/v2/top-headlines?apiKey=a24d98f562554d239d33c2f9d3da0983&country=" + country + "&source=" + source123 + "";

        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject(urlString, String.class);

        JSONObject root = new JSONObject(result);
        String status = null;
        Integer totalResults = null;
        String id = null;
        String name = null;
        String author = null;
        String title = null;
        String description = null;
        String urlother = null;
        String urlToImage = null;
        String publishedAt = null;
        String content = null;

        List<Example> newsList = new ArrayList<>();

        status = root.getString("status");
        totalResults = root.getInt("totalResults");

        JSONArray articlesObject = root.getJSONArray("articles");

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

            Example emp = new Example();
            Articles articles = new Articles();
            Source source = new Source();

            emp.setStatus(status);
            emp.setTotalResults(totalResults);
            articles.setAuthor(author);
            articles.setContent(content);
            articles.setDescription(description);
            articles.setPublishedAt(publishedAt);
            articles.setTitle(title);
            articles.setUrlOther(urlother);
            articles.setUrlToImage(urlToImage);
            source.setId(id);
            source.setName(name);

            articles.setSource(source);
            emp.setArticles(articles);
            newsList.add(emp);
        }
        return newsList;

    }

}
