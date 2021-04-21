<%-- 
    Document   : Part5
    Created on : Mar 19, 2021, 6:05:24 PM
    Author     : charmidalal
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Result</title>
    </head>
    <body>
        <h4><%= request.getAttribute("result")%></h4>
        <button class = "btn btn-primary btn-lg"><a href="index.htm" style="color:white">Back</a></button>
    </body>
</html>
