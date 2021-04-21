<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
    <head>
        <title>HW4</title>
        <meta charset = "utf-8">
        <meta name = "viewport" content = "width=device-width, initial-scale=1">
        <link rel = "stylesheet" href = "https://cdn.staticfile.org/twitter-bootstrap/4.1.0/css/bootstrap.min.css">
        <script src = "https://cdn.staticfile.org/jquery/3.2.1/jquery.min.js"></script>
        <script src = "https://cdn.staticfile.org/popper.js/1.12.5/umd/popper.min.js"></script>
        <script src = "https://cdn.staticfile.org/twitter-bootstrap/4.1.0/js/bootstrap.min.js"></script>
    </head>

    <body>
        <div class = "container">
            <h1> Homework 4 </h1>
        </div>
        <ul class="nav flex-column">
            <li class="nav-item">
                <form action = "Hw4.htm" method = "POST">
                    <input type = "hidden" value = "movie" name = "option" />
                    <input class = "btn btn-link" type = "submit" value = "PART3 LINK"/>
                </form>
            </li>
            <li class="nav-item">
                <form action = "Hw4.htm" method = "POST">
                    <input type = "hidden" value = "books" name = "option" />
                    <input class = "btn btn-link" type = "submit" value = "PART4 LINK"/>
                </form>
            </li>
            <li class="nav-item">
                <form action = "part5.htm" method = "GET">
                    <input class = "btn btn-link" type = "submit" value = "PART5 LINK"/>
                </form>
            </li>
            <li class = "nav-item">
                <form action = "part6.htm" method = "GET">
                    <input class = "btn btn-link" type = "submit" value = "PART6 LINK"/>
                </form>
            </li>
        </ul>
    </body>
</html>