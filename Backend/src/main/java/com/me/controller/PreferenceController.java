/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.me.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.me.dao.BookmarkDao;
import com.me.dao.NewsDao;
import com.me.dao.UserDao;
import com.me.pojo.User;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONException;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author charmidalal
 */
@CrossOrigin(origins = "**", allowedHeaders = "*")
@Controller
public class PreferenceController {

    @Autowired
    UserDao userDao;
    @Autowired
    NewsDao newsDao;
    @Autowired
    BookmarkDao bookmarkDao;
    
    /* Stores latest updated bookmarks */
    @CrossOrigin(origins = "**", allowedHeaders = "*")
    @RequestMapping(value = "/UpdateBookmarks.htm", method = RequestMethod.POST)

    public ResponseEntity<String> UpdateBookmarks(HttpServletRequest request, HttpServletResponse response, @RequestBody String body) throws JsonProcessingException, JSONException {
        Map<String, String> result
                = new ObjectMapper().readValue(body, HashMap.class);
        String username = result.get("username");
        String bookmarks = result.get("bookmarks");
        User uap = userDao.getUser(username);
        if (uap != null) {
            bookmarkDao.deleteBookmark(uap);
            bookmarkDao.updateBookmark(newsDao, uap, bookmarks);
            return new ResponseEntity<>(
                    "Success", HttpStatus.OK);
        } else {
            return new ResponseEntity<>(
                    "Unauthorized", HttpStatus.UNAUTHORIZED);
        }
    }

    /* Stores latest updated user preferences */
    @CrossOrigin(origins = "**", allowedHeaders = "*")
    @RequestMapping(value = "/UpdatePreferences.htm", method = RequestMethod.POST)
    public ResponseEntity<String> UpdatePreferences(HttpServletRequest request, HttpServletResponse response, @RequestBody String body) throws JsonProcessingException, JSONException {
        Map<String, String> result
                = new ObjectMapper().readValue(body, HashMap.class);
        String categories = result.get("categories");
        String countries = result.get("countries");
        String username = result.get("username");
        User uap = userDao.getUser(username);
        if (uap != null) {
            uap.setCategories(categories);
            uap.setCountries(countries);
            userDao.updateUser(uap);
            return new ResponseEntity<>(
                    "Sucess", HttpStatus.OK);
        } else {
            return new ResponseEntity<>(
                    "Unauthorized", HttpStatus.UNAUTHORIZED);
        }
    }

    /* Returns stored preferences of user such as categories, countries & bookmarks */
    @CrossOrigin(origins = "**", allowedHeaders = "*")
    @RequestMapping(value = "/GetPreferences.htm", method = RequestMethod.GET)
    public ResponseEntity<String> GetPreferences(@RequestParam String username, HttpServletRequest request, HttpServletResponse response) throws JsonProcessingException {
        User uap = userDao.getUser(username);
        if (uap != null) {
            JSONObject o = new JSONObject();
            List<String> catList = Arrays.asList(uap.getCategories().split(","));
            List<String> countryList = Arrays.asList(uap.getCountries().split(","));
            o.put("categories", catList);
            o.put("countries", countryList);
            o.put("bookmarks", new Gson().toJson(bookmarkDao.getBookmarkList(uap)));
            return new ResponseEntity<>(new Gson().toJson(o), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(
                    "Unauthorized", HttpStatus.UNAUTHORIZED);
        }
    }
}
