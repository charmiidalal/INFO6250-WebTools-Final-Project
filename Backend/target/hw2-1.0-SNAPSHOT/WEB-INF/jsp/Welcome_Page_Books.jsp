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
            <p>Enter number of books to add!</p>
            <div class = "row" id = "addBooks">
                <form>
                    <div class = "form-group">
                        <label class = "col-form-label" for = "inputNumber">Number of Books</label>
                        <input type = "number" class = "form-control" id = "bookNo">
                    </div>
                    <div class = "text-center">
                        <button type = "submit" class = "btn btn-primary btn-lg" onclick = "addRows()">Add</button>
                        <button class = "btn btn-primary btn-lg"><a href="index.htm" style="color:white">Back</a></button>
                    </div>
                </form>
            </div>
        </div>
    </body>

    <script>
        function addRows()
        {
            var bookNo = document.getElementById("bookNo").value;
            var html = "<form action = 'book.htm' method = 'post'><fieldset><table class = 'table table-bordered'><tr><th>ISBN</th><th>Book Title</th><th>Authors</th> <th>Price</th></tr>";
            for (var i = 1; i <= bookNo; i++)
            {
                html += "<tr>";
                html += "<td><input type = 'text' class = 'form-control' required='true' placeholder = 'ISBN' name = '" + i + "_ISBN" + "'></td>";
                html += "<td><input type = 'text' class = 'form-control' required='true' placeholder = 'Title' name = '" + i + "_Title" + "'></td>";
                html += "<td><input type = 'text' class = 'form-control' required='true' placeholder = 'Authors' name = '" + i + "_Authors" + "'></td>";
                html += "<td><input type = 'number' class = 'form-control' required='true' placeholder='Price' name='" + i + "_Price" + "'></td>";
                html += "</td>";
            }
            html += "</table></br><button type = 'submit' class = 'btn btn-primary btn-lg'>Submit</button></div> </fieldset> </form>";
            document.getElementById("addBooks").innerHTML = html;
        }
    </script>
</html>