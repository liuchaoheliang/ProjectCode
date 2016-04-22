<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<form method="post" id="form1" action="<%=basePath%>/merchant/product/add.api" enctype="multipart/form-data" onsubmit="return submitss()">
   <input type="file" name="file">
   <input type="file" name="file">
   <input type="text" name="fullName">
   <input type="submit" name="" value="submi">
</form>
</body>
</html>