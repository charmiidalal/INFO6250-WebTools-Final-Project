<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<html>
    <head>
        <title>Part 3</title>
        <meta charset = "utf-8">
        <meta name = "viewport" content = "width=device-width, initial-scale=1">
        <link rel = "stylesheet" href = "https://cdn.staticfile.org/twitter-bootstrap/3.3.7/css/bootstrap.min.css">
        <script src = "https://cdn.staticfile.org/jquery/2.1.1/jquery.min.js"></script>
        <script src = "https://cdn.staticfile.org/twitter-bootstrap/3.3.7/js/bootstrap.min.js"></script>
    </head>

    <body>
        <div class = "container">
            <h1>Part 3. Programming Assignment</h1>
            <p>Movie Search result!</p>
        </div>
        <div class = "container">
                <table class = "table">
                    <tbody>
                        <c:if test = "${requestScope.list != null}">
                            <c:set var = "movieList" value = "${requestScope.list}" scope = "request"/>
                        <table border = "1">
                            <thead>
                            <th>Title</th>
                            <th>Actor</th>
                            <th>Actress</th>
                            <th>Genre</th>
                            <th>Year</th>
                            </thead>
                            <tbody>
                                <c:forEach var = "i" items = "${movieList}">
                                    <tr>
                                        <td>${i.getTitle()}</td>
                                        <td>${i.getActor()}</td>
                                        <td>${i.getActress()}</td>
                                        <td>${i.getGenre()}</td>
                                        <td>${i.getYear()}</td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </c:if>
                    </tbody>
                </table>
        </div>
        <button class = "btn btn-primary btn-lg"><a href="index.htm" style="color:white">Back</a></button>
    </body>
</html>