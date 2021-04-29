package com.me.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import com.me.dao.UserDao;
import com.me.pojo.User;
import java.util.Date;
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

@CrossOrigin(origins = "**", allowedHeaders = "*")
@Controller
public class LoginController {

    @Autowired
    UserDao userDao;

    /* Autheticate userand gives access to the news account */
    @CrossOrigin(origins = "**", allowedHeaders = "*")
    @RequestMapping(value = "/login.htm", method = RequestMethod.POST)
    public ResponseEntity<String> UserLogin(HttpServletRequest request, HttpServletResponse response, @RequestBody String body) throws JsonProcessingException {
        Map<String, String> result
                = new ObjectMapper().readValue(body, HashMap.class);
        String username = result.get("username");
        String password = result.get("password");
        User uap = userDao.getUser(username);
        if (uap != null) {
            String resultpassword = uap.getPassword();
            String resultEmail = uap.getEmail();
            if (password.equals(resultpassword)) {
                return new ResponseEntity<>("{\"role\":\"" + uap.getRole() + "\",\"id\":\"" + uap.getId() + "\",\"username\":\""
                        + uap.getUsername() + "\",\"email\":\"" + uap.getEmail() + "\",\"accessToken\":\""
                        + getJwt(username, resultEmail) + ""
                        + "\"}", HttpStatus.OK);
            } else {
                return new ResponseEntity<>(
                        "{ \"message\" : \"Incorrect Password!\"}", HttpStatus.UNAUTHORIZED);
            }
        } else {
            return new ResponseEntity<>(
                    "{\"message\": \"User not found!\"}", HttpStatus.NOT_FOUND);
        }
    }

    /* Generates JWT token to identify the user session after login */
    public String getJwt(String username, String email) {
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        String jwtToken = Jwts.builder()
                .claim("username", username)
                .claim("email", email)
                .setIssuedAt(now)
                .compact();
        return jwtToken;
    }

}
