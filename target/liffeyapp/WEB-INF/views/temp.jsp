<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page session="false" %>
<html>
<head>
	<title>Temporary</title>
</head>
<body>
<h1>
	Hello world! ${user} 
</h1>

<p>First name : ${user.fname} </p>
<p>Last name : ${user.lname} </p>
<p>Email : ${user.email} </p>
<p>Password : ${user.password} </p>

</body>
</html>
