<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title>Part 4</title>
        <meta charset = "utf-8">
        <meta name = "viewport" content = "width=device-width, initial-scale=1">
        <link rel = "stylesheet" href = "https://cdn.staticfile.org/twitter-bootstrap/3.3.7/css/bootstrap.min.css">
        <script src = "https://cdn.staticfile.org/jquery/2.1.1/jquery.min.js"></script>
        <script src = "https://cdn.staticfile.org/twitter-bootstrap/3.3.7/js/bootstrap.min.js"></script>
    </head>
    <body>
        <div class = "container">
            <h1>Part 4. Programming Assignment</h1>
            <p>Movie added successfully.</p>
            <h2><%= request.getAttribute("result")%></h2>
            <button class = "btn btn-primary btn-lg"><a href="index.htm" style="color:white">Back</a></button>
        </div>
    </body>
</html>