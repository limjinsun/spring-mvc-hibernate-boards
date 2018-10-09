<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<title>Access Denied - ProgrammingFree</title>
</head>
<body>
	<h1>You do not have permission to access this page!</h1>
	<c:set var="login_url" value="${pageContext.servletContext.contextPath}/login" />
	
	
	<form action="${login_url}" method="get">
		<input type="submit" value="Sign in as different user" /> 
		<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
	</form>
</body>
</html>