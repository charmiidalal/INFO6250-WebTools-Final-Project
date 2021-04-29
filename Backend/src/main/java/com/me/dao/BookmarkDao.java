package com.me.dao;

import com.me.pojo.Bookmark;
import com.me.pojo.News;
import com.me.pojo.User;
import java.util.ArrayList;
import org.hibernate.HibernateError;
import org.hibernate.query.Query;
import org.json.JSONException;
import org.json.simple.JSONArray;

/**
 *
 * @author charmidalal
 */
public class BookmarkDao extends Dao {

    /* Creates new bookmark */
    public void createBookmark(Bookmark bookmark) {
        try {
            beginTransaction();
            getSession().saveOrUpdate(bookmark);
            commit();
        } catch (HibernateError e) {
            rollback();
        } finally {
            close();
        }
    }

    /* Delete bookmark with specific user before insertng  new bunch */
    public void deleteBookmark(User u) {
        try {
            beginTransaction();
            Query q = getSession().createQuery("from Bookmark where userID = :userID ");
            q.setParameter("userID", u.getId());
            ArrayList<Bookmark> bookmarkList = (ArrayList<Bookmark>) q.list();
            for (Bookmark b : bookmarkList) {
                getSession().delete(b);
            }
            commit();
        } catch (HibernateError e) {
            rollback();
        } finally {
            close();
        }
    }

    /* Update bookmark with new list */
    public void updateBookmark(NewsDao newsDao, User user, String bookmarks) throws JSONException {
        org.json.JSONArray blist = new org.json.JSONArray(bookmarks);
        for (int i = 0; i < blist.length(); i++) {
            Bookmark b = new Bookmark();
            b.setUser(user);
            b.setNews(newsDao.getNews(new Long(blist.getJSONObject(i).getInt("newsID"))));
            createBookmark(b);
        }
    }

    /* Fetch bookmark list with details of user */
    public JSONArray getBookmarkList(User user) {
        JSONArray arr = new JSONArray();
        try {
            beginTransaction();
            Query q = getSession().createQuery("from Bookmark where userID = :userID");
            q.setParameter("userID", user.getId());
            ArrayList<Bookmark> bookmarkList = (ArrayList<Bookmark>) q.list();
            if (bookmarkList.isEmpty()) {
                return null;
            }
            for (Bookmark bookmark : bookmarkList) {
                org.json.simple.JSONObject o = new org.json.simple.JSONObject();
                News news = bookmark.getNews();
                o.put("source", news.getSource());
                o.put("author", news.getAuthor());
                o.put("title", news.getTitle());
                o.put("description", news.getDescription());
                o.put("url", news.getUrl());
                o.put("urlToImage", news.getUrlToImage());
                o.put("publishedAt", news.getPublishedAt());
                o.put("content", news.getContent());
                o.put("newsID", news.getNewsID());
                arr.add(o);
            }
            commit();

        } catch (HibernateError e) {
            rollback();
        } finally {
            close();
        }

        return arr;
    }

}
