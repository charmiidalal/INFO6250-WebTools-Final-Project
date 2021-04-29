/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.me.dao;

import com.me.pojo.User;
import java.util.ArrayList;
import org.hibernate.HibernateError;
import org.hibernate.query.Query;

/**
 *
 * @author charmidalal
 */
public class UserDao extends Dao {

    private User user;

     /* Check duplicate value fo email of user */
    public User getEmail(String email) {
        try {
            beginTransaction();
            Query q = getSession().createQuery("from User where email = :email");
            q.setParameter("email", email);
            ArrayList<User> userList = (ArrayList<User>) q.list();
            if (userList.isEmpty()) {
                return null;
            }
            user = userList.get(0);
            commit();
        } catch (HibernateError e) {
            rollback();
        } finally {
            close();
        }
        return user;
    }

    /* Finds user by username */
    public User getUser(String username) {
        try {
            beginTransaction();
            Query q = getSession().createQuery("from User where username = :username");
            q.setParameter("username", username);
            ArrayList<User> userList = (ArrayList<User>) q.list();
            if (userList.isEmpty()) {
                return null;
            }
            user = userList.get(0);
            commit();
        } catch (HibernateError e) {
            rollback();
        } finally {
            close();
        }
        return user;
    }

    /* Create user with default values */
    public boolean createUser(User userPojo, String username, String password, String role, String email) {
        try {
            beginTransaction();
            userPojo.setEmail(email);
            userPojo.setRole("user");
            userPojo.setUsername(username);
            userPojo.setPassword(password);
            userPojo.setCategories("Business,Entertainment,General,Health,Science,Sports,Technology");
            userPojo.setCountries("United States of America,United Kingdom,Canada,China,Russia,France,"
                    + "Philippines,United Arab Emirates,Australia,Argentina,South Korea,Indonesia");
            getSession().save(userPojo);
            commit();
        } catch (HibernateError e) {
            rollback();
        } finally {
            close();
        }
        return true;
    }

    /* Update user from User table */
    public boolean updateUser(User user) {
        try {
            beginTransaction();
            getSession().update(user);
            commit();
        } catch (HibernateError e) {
            rollback();
        } finally {
            close();
        }
        return true;
    }

    /* Delete user from User table */
    public boolean deleteUser(User user) {
        try {
            beginTransaction();
            getSession().delete(user);
            commit();
        } catch (HibernateError e) {
            rollback();
        } finally {
            close();
        }
        return true;
    }

}
