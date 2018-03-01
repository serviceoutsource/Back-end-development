<%--
  Created by IntelliJ IDEA.
  User: Gavin
  Date: 2018/2/28
  Time: 9:18
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>AliGenie_webhook测试</title>
</head>
<body>
<h2>menu点菜/菜单意图测试</h2><br>
<form action="/aliGenie/menu" method="post">
    <input type="text" name="val">
    <input type="submit" value="提交">
</form><br>
<h2>推荐菜品意图测试</h2><br>
<form action="/aliGenie/foodAdvice" method="post">
    <input type="text" name="val">
    <input type="submit" value="提交">
</form><br>
<h2>厨具控制意图测试</h2><br>
<form action="/aliGenie/cookerCtrl" method="post">
    <input type="text" name="val">
    <input type="submit" value="提交">
</form><br>
<h2>食材处理意图测试</h2><br>
<form action="/aliGenie/foodHandle" method="post">
    <input type="text" name="val">
    <input type="submit" value="提交">
</form>

</body>
</html>
