<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<!--Import Google Icon Font-->
<link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
<!-- Favicons-->
<link rel="icon" href="${pageContext.servletContext.contextPath}/resources/images/favicon.ico" sizes="32x32">
<!-- Favicons-->
<link rel="apple-touch-icon-precomposed" href="${pageContext.servletContext.contextPath}/resources/images/favicon.ico">
<!-- For iPhone -->
<meta name="msapplication-TileColor" content="#00bcd4">
<meta name="msapplication-TileImage" content="${pageContext.servletContext.contextPath}/resources/images/favicon.ico">

<title>Custom Login Page</title>
<style>
.failed {
	color: red;
}
</style>
</head>



<body>
	<h3>My Custom Login Page</h3>
	<c:url value="/validateLogin" var="loginProcessingUrl" />
	<form:form action="${loginProcessingUrl}" method="POST">
		<!-- Check for login error -->
		<c:if test="${param.error != null}">
			<div class="failed">Invalid username and password.</div>
		</c:if>

		<!-- Check for logout -->
		<c:if test="${param.logout != null}">
			<div class="failed">You have been logged out.</div>
		</c:if>

		<p>
			Email: <input type="text" name="username" />
		</p>

		<p>
			Password: <input type="password" name="password" />
		</p>

		<input type="submit" value="Login" />

		<%-- you don't have to put this, as it's inculde in form: tag 
		<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" /> --%>
	</form:form>
</body>

</html>
