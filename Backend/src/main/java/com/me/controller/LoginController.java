package com.me.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import com.google.gson.Gson;
import com.me.dao.UserDao;
import com.me.pojo.User;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
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

    @CrossOrigin(origins = "**", allowedHeaders = "*")
    @RequestMapping(value = "/login.htm", method = RequestMethod.POST)
    public ResponseEntity<String> UserLogin(HttpServletRequest request, HttpServletResponse response, @RequestBody String body) throws JsonProcessingException {
        UserDao ad = new UserDao();
        Map<String, String> result
                = new ObjectMapper().readValue(body, HashMap.class);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Custom-Header", "foo");
        Gson gson = new Gson();
        String username = result.get("username");
        String password = result.get("password");
        User uap = ad.getUser(username);
        if (uap != null) {
            String resultpassword = uap.getPassword();
            String resultEmail = uap.getEmail();
            if (password.equals(resultpassword)) {
                return new ResponseEntity<>("{\"role\":\"" + uap.getRole() + "\",\"id\":\"" + uap.getId() + "\",\"username\":\""
                        + uap.getUsername() + "\",\"email\":\"" + uap.getEmail() + "\",\"accessToken\":\""
                        + getJwt(username, resultEmail) + ""
                        + "\"}", headers, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(
                        "{ \"message\" : \"Incorrect Password!\"}", headers, HttpStatus.UNAUTHORIZED);
            }
        } else {
            return new ResponseEntity<>(
                    "{\"message\": \"User not found!\"}", headers, HttpStatus.NOT_FOUND);
        }
    }

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
