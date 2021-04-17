/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.me.controller;

import Helper.Validation;
import com.google.gson.Gson;
import com.me.dao.UserDao;
import com.me.pojo.User;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author manushpatel
 */
@Controller
public class SignUpController {

    @RequestMapping(value = "/signup.htm", method = RequestMethod.POST)
    public ResponseEntity<String> UserSignup(@RequestParam("username") String username, @RequestParam("password") String password,
            @RequestParam("email") String email, @RequestParam("role") String role, HttpServletRequest request, HttpServletResponse response) {
        Validation validation = new Validation();
        UserDao userDao = new UserDao();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Custom-Header", "foo");
        Gson gson = new Gson();
        User uap = userDao.getUser(username);
        if (uap != null) {
            return new ResponseEntity<>(
                    "Username", headers, HttpStatus.UNAUTHORIZED);
        }
        uap = userDao.getEmail(email);
        if (uap != null) {
            return new ResponseEntity<>(
                    "Email", headers, HttpStatus.UNAUTHORIZED);
        }
        if (!validation.passwordPatternCorrect(password) || password.trim().length() < 6 || password.trim().length() > 10) {
            return new ResponseEntity<>(
                    "Password", headers, HttpStatus.UNAUTHORIZED);
        }
        userDao.createUser(username, password, role, email);
        return new ResponseEntity<>("Success", headers, HttpStatus.OK);
    }
}
