<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page session="false"%>
<html>
<head>
<title>Home</title>
</head>
<body>
	<h1>Success ${user.fname} ${user.lname}</h1>

	<!-- Check for login error -->
	<c:if test="${param.error != null}">
		<div class="alert alert-danger col-xs-offset-1 col-xs-10">Invalid username and password.</div>
	</c:if>

	<!-- Check for logout -->
	<c:if test="${param.logout != null}">
		<div class="alert alert-success col-xs-offset-1 col-xs-10">You have been logged out.</div>
	</c:if>

	<c:url var="logoutUrl" value="/logout" />
	<form:form action="${logoutUrl}" method="POST">
		<input type="submit" value="Log out" />
		<%-- 	<input type="hidden" name="${_csrf.parameterName}"
			value="${_csrf.token}" /> --%>
	</form:form>
	
</body>
</html>
