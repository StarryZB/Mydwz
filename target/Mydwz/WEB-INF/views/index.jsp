
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<link rel="stylesheet" href="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css">
<script src="https://cdn.bootcss.com/jquery/2.2.4/jquery.min.js"></script>
<script src="https://cdn.bootcss.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>

<html>
<head>
    <title>get MyDwz</title>
</head>
<body>
<div align="center" style="margin-top: 100px" >
    <form action="/mydwz/getMyDwz" method="post" id="form">
        长网址：<input id="longUrl" name="longUrl" type="text"><br>
        <br>
        <input type="submit" name="缩短网址" class="btn btn-primary">
    </form>
</div>
</body>

</html>
