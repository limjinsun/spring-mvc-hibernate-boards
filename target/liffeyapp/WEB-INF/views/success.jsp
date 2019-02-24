<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
<head>
<title>Home</title>

<style>
.failed {
	color: red;
}
</style>

</head>
<body>
	<h1>Success</h1>
	<c:url var="logoutUrl" value="/logout" />
	<!-- Check for logout -->
	<c:if test="${param.logout != null}">
		<div class="failed">You have been logged out.</div>
	</c:if>

	<form:form action="${pageContext.servletContext.contextPath}/logout" method="POST">
		<input type="submit" value="Log out" />
		<%-- 	<input type="hidden" name="${_csrf.parameterName}"
			value="${_csrf.token}" /> --%>
	</form:form>

</body>
</html>
