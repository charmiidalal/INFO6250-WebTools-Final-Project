<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <link rel="stylesheet" href="https://cdn.staticfile.org/twitter-bootstrap/3.3.7/css/bootstrap.min.css">
        <script src="https://cdn.staticfile.org/jquery/2.1.1/jquery.min.js"></script>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Part 3</title>
    </head>

    <body>
        <div class = "container">
            <h1>Part 3. Programming Assignment</h1>
            <p>Create an MVC application to browse movies and add new movies to the DB. (You may change/improve/simplify the views). 
                For those of you who don’t know how to create a database, we could create one on the server, and send you the Connection information.</p>

            <form class = "form-horizontal" action = "movie.htm" method = "post">
                <input type="hidden" value="insert" name="movie_operation" />
                <div class = "form-group">
                    <label>Title</label>
                    <input class = "form-control" type = "text" name = "title" required = "true">
                </div>
                <div class = "form-group">
                    <label>Actor</label>
                    <input class = "form-control" type = "text" name = "actor" required = "true">
                </div>
                <div class = "form-group">
                    <label>Actress</label>
                    <input class = "form-control" type = "text" name = "actress" required = "true">
                </div>
                <div class = "form-group">
                    <label>Genre</label>
                    <input class = "form-control" type = "text" name = "genre" required = "true">
                </div>
                <div class = "form-group">
                    <label>Year</label>
                    <input class = "form-control" type = "number" max="3000" min = "1600" name = "year" required = "true">
                </div>
                <div class ="text-center" id = "addMovie">
                    <button type = "submit" class = "btn btn-primary btn-lg">Add Movie</button>
                    <button class = "btn btn-primary btn-lg"><a href="index.htm" style="color:white">Back</a></button>
                </div>
            </form>
        </div>
        <div class = "container">
            <c:if test = "${requestScope.status != null}">
                <h4><c:out value = "${requestScope.result}"/>!</h4>
            </c:if>
        </div>
    </body>
</html>