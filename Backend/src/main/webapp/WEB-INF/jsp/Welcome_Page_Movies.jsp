<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<head>
    <title>Part 3</title>
    <meta charset = "utf-8">
    <meta name = "viewport" content = "width=device-width, initial-scale=1">
    <link rel = "stylesheet" href = "https://cdn.staticfile.org/twitter-bootstrap/3.3.7/css/bootstrap.min.css">
    <script src = "https://cdn.staticfile.org/jquery/2.1.1/jquery.min.js"></script>
    <script src = "https://cdn.staticfile.org/twitter-bootstrap/3.3.7/js/bootstrap.min.js"></script>
</head>

<body>
    <div class = "row">
        <div class = "container">
            <h1>Part 3. Programming Assignment</h1>
            <p>Movie Store</p>
            <form action = "movie.htm" method = "POST">
                <div class="form-group">
                    <label>Select Operation:</label>
                    <select class="form-control" id="movie_operation" name="movie_operation">
                        <option value = "search_movie">Search Movies</option>
                        <option value = "add_movie">Add Movie</option>
                    </select>
                </div>
                <div class = "text-center">
                    <button class = "btn btn-primary btn-lg" type = "submit" value = "go">Go!</button>
                </div>
            </form>
        </div>
    </div>
    <button class = "btn btn-primary btn-lg"><a href="index.htm" style="color:white">Back</a></button>
</body>
</html>