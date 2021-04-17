/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.me.dao;

import com.me.pojo.User;
import java.util.ArrayList;
import org.hibernate.HibernateError;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

/**
 *
 * @author manushpatel
 */
public class UserDao {

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

    public User getUser(String username) {
        User user = null;
        try {
            beginTransaction();
            Query q = getSession().createQuery("from User where username = '"+ username +"'");
            ArrayList<User> userList = (ArrayList<User>) q.list();
            if(userList.size() == 0 )
            {
                return null;
            }
            user = userList.get(0);
            commit();
        } catch (HibernateError e) {
            e.printStackTrace();
            rollback();
        } finally {
            close();
        }
        return user;
    }
}
