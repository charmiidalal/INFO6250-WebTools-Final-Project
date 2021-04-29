/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.me.controller;

import Helper.Validation;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.me.dao.UserDao;
import com.me.pojo.User;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
public class SignUpController {

    @Autowired
    UserDao userDao;

    /* Filters Cors headers & requests */
    @CrossOrigin(origins = "**", allowedHeaders = "*")
    @RequestMapping(value = "/signup.htm", method = RequestMethod.OPTIONS)
    public void corsHeaders(HttpServletResponse response) {
        response.addHeader("Access-Control-Allow-Origin", "http://localhost:8081");
        response.addHeader("Access-Control-Allow-Methods", "GET,PUT,POST,DELETE,OPTIONS");

    }

    /* Checks if user with same username or email exists & if not then creates account */
    @CrossOrigin(origins = "**", allowedHeaders = "*")
    @RequestMapping(value = "/signup.htm", method = RequestMethod.POST)
    public ResponseEntity<String> UserSignup(@Autowired User user, HttpServletRequest request, HttpServletResponse response, @RequestBody String body) throws JsonProcessingException {
        Validation validation = new Validation();
        Map<String, String> result
                = new ObjectMapper().readValue(body, HashMap.class);
        String username = result.get("username");
        String password = result.get("password");
        String email = result.get("email");
        User uap = userDao.getUser(username);
        if (uap != null) {
            return new ResponseEntity<>(
                    "{ \"message\": \"Username is already taken!\" }", HttpStatus.UNAUTHORIZED);
        }
        uap = userDao.getEmail(email);
        if (uap != null) {
            return new ResponseEntity<>(
                    "{ \"message\": \"Email is already taken!\" }", HttpStatus.UNAUTHORIZED);
        }
        if (!validation.passwordPatternCorrect(password) || password.trim().length() < 6) {
            return new ResponseEntity<>(
                    "{ \"message\": \"Password must be 6 letter long and include alphanumeric string !\" }", HttpStatus.UNAUTHORIZED);
        }
        userDao.createUser(user, username, password, "user", email);
        return new ResponseEntity<>("{ \"message\": \"User was registered successfully!\" }", HttpStatus.OK);
    }

    /* Deletes user's account */
    @CrossOrigin(origins = "**", allowedHeaders = "*")
    @RequestMapping(value = "/DeleteUser.htm", method = RequestMethod.POST)
    public ResponseEntity<String> DeleteUser(@RequestParam String username, HttpServletRequest request, HttpServletResponse response) throws JsonProcessingException {
        User uap = userDao.getUser(username);
        if (uap != null) {
            userDao.deleteUser(uap);
            return new ResponseEntity<>("Success", HttpStatus.OK);
        } else {
            return new ResponseEntity<>(
                    "Unauthorized", HttpStatus.UNAUTHORIZED);
        }
    }
}
