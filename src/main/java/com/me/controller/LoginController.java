package com.me.controller;

import io.jsonwebtoken.Jwts;
import com.google.gson.Gson;
import com.me.dao.UserDao;
import com.me.pojo.User;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

//    @PostMapping("user/main.htm")
    @RequestMapping(value = "/login.htm", method = RequestMethod.POST)
    public ResponseEntity<String> UserLogin(@RequestParam("username") String username, @RequestParam("password") String password,
            HttpServletRequest request, HttpServletResponse response) {
        UserDao ad = new UserDao();
//        HttpHeaders headers = new HttpHeaders();
        HttpHeaders headers = new HttpHeaders();

        headers.add("Custom-Header", "foo");
        Gson gson = new Gson();

        User uap = ad.getUser(username);
        if (uap != null) {
            String resultpassword = uap.getPassword();
            String resultusername = uap.getUsername();

            if (username.equals(resultusername) && password.equals(resultpassword)) {
                return new ResponseEntity<>(
                        gson.toJson("{accessToken:" + getJwt(username)) + "}", headers, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(
                        "Unauthorized", headers, HttpStatus.UNAUTHORIZED);
            }
        } else {
            return new ResponseEntity<>(
                    "Unauthorized", headers, HttpStatus.UNAUTHORIZED);
            //scs.BadRequestCode(request, response);
        }
//       return new ModelAndView("success","sucessMessage","User added");
//       SessionFactory sf = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
//        try (Session session = sf.openSession()) {
//            Transaction tx = session.beginTransaction();
//            session.save(user);
//            tx.commit();
//        }

    }

    public String getJwt(String username) {
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        String jwtToken = Jwts.builder()
                .claim("username", username)
                .claim("email", "jane@example.com")
                .setIssuedAt(now)
                .compact();
        return jwtToken;
    }

}
