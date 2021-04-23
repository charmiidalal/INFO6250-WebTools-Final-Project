/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.me.dao;

import com.me.pojo.News;
import com.me.pojo.User;
import java.util.ArrayList;
import org.hibernate.HibernateError;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

/**
 *
 * @author manushpatel
 */
public class NewsDao {

    private static final SessionFactory sf = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
    private Session session = null;

    public Session getSession() {
        if (session == null || !session.isOpen()) {
            session = sf.openSession();
        }
        return session;
    }

    public void beginTransaction() {
        getSession().beginTransaction();
    }

    public void commit() {
        getSession().getTransaction().commit();
    }

    public void rollback() {
        getSession().getTransaction().rollback();
    }

    public void close() {
        getSession().close();
    }

    public ArrayList<News> getAllNewsList() {
        ArrayList<News> newsList = null;
        try {
            beginTransaction();
            Query q = getSession().createQuery("from News");
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

    public ArrayList<News> getNewsByAuthor(String username) {
        ArrayList<News> newsList = null;
        try {
            beginTransaction();
            Query q = getSession().createQuery("from News where author = '" + username + "'");
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
    
    public News getNews(int newsID) {
        News news = null;
        try {
            beginTransaction();
            Query q = getSession().createQuery("from News where newsID = '" + newsID + "'");
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

}
