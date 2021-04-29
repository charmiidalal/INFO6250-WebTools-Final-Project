/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.me.pojo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author charmidalal
 */
@Entity
@Table(name = "bookmark")
public class Bookmark {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long bookmarkID;

    @ManyToOne()
    @JoinColumn(name = "userID")
    public User user;

    @ManyToOne()
    @JoinColumn(name = "newsID")
    public News news;

    public long getBookmarkID() {
        return bookmarkID;
    }

    public void setBookmarkID(long bookmarkID) {
        this.bookmarkID = bookmarkID;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public News getNews() {
        return news;
    }

    public void setNews(News news) {
        this.news = news;
    }

}
