/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.me.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.me.dao.UserDao;
import com.me.pojo.User;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.simple.JSONObject;
import org.springframework.http.HttpHeaders;
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
 * @author manushpatel
 */
@CrossOrigin(origins = "**", allowedHeaders = "*")
@Controller
public class PreferenceController {

    @CrossOrigin(origins = "**", allowedHeaders = "*")
    @RequestMapping(value = "/UpdateBookmarks.htm", method = RequestMethod.POST)
    public ResponseEntity<String> UpdateBookmarks(HttpServletRequest request, HttpServletResponse response, @RequestBody String body) throws JsonProcessingException {
        UserDao ad = new UserDao();
        Map<String, String> result
                = new ObjectMapper().readValue(body, HashMap.class);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Custom-Header", "foo");
        String username = result.get("username");
        String bookmarks = result.get("bookmarks");
        User uap = ad.getUser(username);
        if (uap != null) {
            uap.setBookmarks(bookmarks);
            ad.updateUser(uap);
            return new ResponseEntity<>(
                    "Sucess", headers, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(
                    "Unauthorized", headers, HttpStatus.UNAUTHORIZED);
        }
    }
    
    @CrossOrigin(origins = "**", allowedHeaders = "*")
    @RequestMapping(value = "/UpdatePreferences.htm", method = RequestMethod.POST)
    public ResponseEntity<String> UpdatePreferences(HttpServletRequest request, HttpServletResponse response, @RequestBody String body) throws JsonProcessingException, JSONException {
        UserDao ad = new UserDao();
        Map<String, String> result
                = new ObjectMapper().readValue(body, HashMap.class);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Custom-Header", "foo");
        JSONArray categoriesArray = new JSONArray(result.get("categories"));
        JSONArray countriesArray = new JSONArray(result.get("countries"));
        String username = result.get("username");
        String categories = categoriesArray.toString();
        String countries = countriesArray.toString();
        User uap = ad.getUser(username);
        if (uap != null) {
            uap.setCategories(categories);
            uap.setCountries(countries);
            ad.updateUser(uap);
            return new ResponseEntity<>(
                    "Sucess", headers, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(
                    "Unauthorized", headers, HttpStatus.UNAUTHORIZED);
        }
    }
    

    @CrossOrigin(origins = "**", allowedHeaders = "*")
    @RequestMapping(value = "/GetPreferences.htm", method = RequestMethod.GET)
    public ResponseEntity<String> GetPreferences(@RequestParam String username,HttpServletRequest request, HttpServletResponse response) throws JsonProcessingException {
        UserDao ad = new UserDao();
       
        HttpHeaders headers = new HttpHeaders();
        headers.add("Custom-Header", "foo");
        User uap = ad.getUser(username);
        if (uap != null) {
            JSONObject o = new JSONObject();
            o.put("categories", uap.getCategories());
            o.put("countries", uap.getCountries());
            o.put("bookmarks", uap.getBookmarks());
            return new ResponseEntity<>(new Gson().toJson(o), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(
                    "Unauthorized", headers, HttpStatus.UNAUTHORIZED);
        }
    }
}
