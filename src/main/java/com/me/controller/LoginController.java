package com.me.controller;

import com.me.dao.UserDao;
import com.me.pojo.User;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
//import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginController {

//    @PostMapping("user/main.htm")
    @RequestMapping(value = "user/main.htm", method = RequestMethod.POST)
    public ResponseEntity<String> UserLogin(@RequestParam("username") String username, @RequestParam("password") String password,
            HttpServletRequest request, HttpServletResponse response) {
        UserDao ad = new UserDao();
//        HttpHeaders headers = new HttpHeaders();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Custom-Header", "foo");
        User uap = ad.getUser(username);
        if (uap != null) {
            String resultpassword = uap.getPassword();
            String resultusername = uap.getUsername();

            if (username.equals(resultusername) && password.equals(resultpassword)) {
                return new ResponseEntity<>(
                        "Auth", headers, HttpStatus.OK);
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

}
