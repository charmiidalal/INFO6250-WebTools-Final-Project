<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
            <p>Search movies</p>

            <form action = "browser.htm" method = "get">
                <fieldset>
                    <div class = "form-group">
                        <label><strong>Keyword : </strong></label>
                        <input class = "form-control" type = "text" placeholder = "Keyword" id = "inputLarge" name = "keyword" required = "true">
                    </div>
                    <fieldset class = "form-group">
                        <div class = "form-check">
                            <label class = "form-check-label">
                                <input type = "radio" class = "form-check-input" id = "title" value = "title" name = "type" checked = "">
                                Search By Title
                            </label>
                        </div>

                        <div class = "form-check">
                            <label class = "form-check-label">
                                <input type = "radio" class = "form-check-input" id = "actor" value = "actor" name = "type">
                                Search By Actor
                            </label>
                        </div>

                        <div class = "form-check">
                            <label class = "form-check-label">
                                <input type = "radio" class = "form-check-input" id = "actress" value = "actress" name = "type">
                                Search By Actress
                            </label>
                        </div>
                    </fieldset>
                    <br>
                    <div class = "text-center" id = "replaceSection">
                        <button type = "submit" class = "btn btn-primary btn-lg">Search Movies</button>
                        <button class = "btn btn-primary btn-lg"><a href="../index.htm" style="color:white">Back</a></button>
                    </div>
                </fieldset>
            </form>
        </div>
    </body>
</html>