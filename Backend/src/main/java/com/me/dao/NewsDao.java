/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.me.dao;

import com.me.pojo.News;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import org.hibernate.HibernateError;
import org.hibernate.query.Query;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.JSONArray;

/**
 *
 * @author charmidalal
 */
public class NewsDao extends Dao {

    /* fetch all news from News table */
    public ArrayList<News> getAllNewsList() {
        ArrayList<News> newsList = null;
        try {
            beginTransaction();
            Query q = getSession().createQuery("from News order by publishedAt desc");
            newsList = (ArrayList<News>) q.list();
            if (newsList.isEmpty()) {
                return null;
            }
            commit();
        } catch (HibernateError e) {
            rollback();
        } finally {
            close();
        }
        return newsList;
    }

    /* Fetch news with filter of query, SOurce, category and countries */
    public ArrayList<News> getAllNewsListByFilter(String category, String country, String source, String queryStr) {
        ArrayList<News> newsList = null;
        Query q = null;
        try {
            beginTransaction();
            if (!source.equals("")) {
                if (queryStr.equals("")) {
                    q = getSession().createQuery("from News where source = :source");
                } else {
                    q = getSession().createQuery("from News where source = :source and title like :queryStr");
                    q.setParameter("queryStr", "%" + queryStr + "%");
                }
                q.setParameter("source", source);
            } else {
                if (queryStr.equals("")) {
                    q = getSession().createQuery("from News where category = :category and country = :country");
                } else {
                    q = getSession().createQuery("from News where category = :category and country = :country and title like :queryStr");
                    q.setParameter("queryStr", "%" + queryStr + "%");
                }
                q.setParameter("category", category);
                q.setParameter("country", country);
            }
            newsList = (ArrayList<News>) q.list();
            if (newsList.isEmpty()) {
                return null;
            }
            commit();
        } catch (HibernateError e) {
            rollback();
        } finally {
            close();
        }
        return newsList;
    }

    /* Checks for duplicate news */
    public boolean checkDuplicate(String title) {
        try {
            beginTransaction();
            Query q = getSession().createQuery("from News where title = :title");
            q.setParameter("title", title);
            ArrayList<News> newsList = (ArrayList<News>) q.list();
            if (newsList.isEmpty()) {
                return false;
            }
            commit();
        } catch (HibernateError e) {
            rollback();
        } finally {
            close();
        }
        return true;
    }

    /* Check news list by author */
    public ArrayList<News> getNewsByAuthor(String username) {
        ArrayList<News> newsList = null;
        try {
            beginTransaction();
            Query q = getSession().createQuery("from News where author = :author");
            q.setParameter("author", username);
            newsList = (ArrayList<News>) q.list();
            if (newsList.isEmpty()) {
                return null;
            }
            commit();
        } catch (HibernateError e) {
            rollback();
        } finally {
            close();
        }
        return newsList;
    }

    /* Creates new news */
    public void createNews(News news) {
        try {
            beginTransaction();
            getSession().saveOrUpdate(news);
            commit();
        } catch (HibernateError e) {
            rollback();
        } finally {
            close();
        }
    }

    /* Delete news by author */
    public void deleteNewsByID(News news) {
        try {
            beginTransaction();
            getSession().delete(news);
            commit();
        } catch (HibernateError e) {
            rollback();
        } finally {
            close();
        }
    }

    /* Serach news by NewsID */
    public News getNews(long newsID) {
        News news = null;
        try {
            beginTransaction();
            Query q = getSession().createQuery("from News where newsID = :newsID");
            q.setParameter("newsID", newsID);
            ArrayList<News> newsList = (ArrayList<News>) q.list();
            if (newsList.isEmpty()) {
                return null;
            }
            news = newsList.get(0);
            commit();
        } catch (HibernateError e) {
            rollback();
        } finally {
            close();
        }
        return news;
    }

    /* Convert json to array and store scraped news in table */
    public void addScrapedNews(String newsResult, String category, String country, String source) throws JSONException, ParseException {
        JSONObject root = new JSONObject(newsResult);
        String id = null;
        String name = null;
        String author = null;
        String title = null;
        String description = null;
        String urlother = null;
        String urlToImage = null;
        String publishedAt = null;
        String content = null;

        org.json.JSONArray articlesObject = root.getJSONArray("articles");

        for (int i = 0; i < articlesObject.length(); i++) {
            JSONObject arrayElement = articlesObject.getJSONObject(i);

            JSONObject sourceother = arrayElement.getJSONObject("source");
            if (!sourceother.isNull("id")) {
                id = sourceother.getString("id");
            } else {
                id = null;
            }

            name = (source.equals("")) ? sourceother.getString("name") : source;

            if (!arrayElement.isNull("author")) {
                author = arrayElement.getString("author");
            } else {
                author = null;
            }

            title = arrayElement.getString("title");
            if (checkDuplicate(title)) {
                continue;
            }
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
            publishedAt = publishedAt.split("T")[0];
            SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-mm-dd");
            Date pubDate = formatter1.parse(publishedAt);

            if (!arrayElement.isNull("content")) {
                content = arrayElement.getString("content");
            } else {
                content = null;
            }
            News news = new News();
            news.setAuthor(author);
            news.setContent(content);
            news.setDescription(description);
            news.setPublishedAt(pubDate);
            news.setTitle(title);
            news.setUrl(urlother);
            news.setUrlToImage(urlToImage);
            news.setSource(name);
            news.setCategory(category);
            news.setCountry(country);
            createNews(news);
        }
    }

    /* Converts fetched news in json format */
    public org.json.simple.JSONObject fetchScrapedNews(ArrayList<News> newsList) {
        org.json.simple.JSONObject obj = new org.json.simple.JSONObject();
        obj.put("status", "ok");
        obj.put("totalResults", newsList.size());
        JSONArray arr = new JSONArray();

        for (News n : newsList) {
            org.json.simple.JSONObject o = new org.json.simple.JSONObject();
            org.json.simple.JSONObject sourceB = new org.json.simple.JSONObject();
            sourceB.put("id", "null");
            sourceB.put("name", n.getSource());
            o.put("source", n.getSource());
            o.put("author", n.getAuthor());
            o.put("title", n.getTitle());
            o.put("description", n.getDescription());
            o.put("url", n.getUrl());
            o.put("urlToImage", n.getUrlToImage());
            o.put("publishedAt", n.getPublishedAt());
            o.put("content", n.getContent());
            o.put("newsID", n.getNewsID());
            arr.add(o);
        }
        obj.put("articles", arr);
        return obj;
    }

}
