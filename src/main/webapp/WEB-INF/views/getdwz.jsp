<%--
  Created by IntelliJ IDEA.
  User: 34432
  Date: 2020/6/18
  Time: 16:23
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<link rel="stylesheet" href="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css">
<script src="https://cdn.bootcss.com/jquery/2.2.4/jquery.min.js"></script>
<script src="https://cdn.bootcss.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<html>
<head>
    <title>MyDwz</title>
</head>
    <body>
        <div align="center" style="margin-top: 100px" >
            原来网址：${requestScope.getdwz.longUrl}<br>
            短网址：mydwz/${requestScope.getdwz.shortUrl}<br>
            <a href="/mydwz/${requestScope.getdwz.shortUrl}">mydwz/${requestScope.getdwz.shortUrl}</a><br>
        </div>
    </body>
</html>
