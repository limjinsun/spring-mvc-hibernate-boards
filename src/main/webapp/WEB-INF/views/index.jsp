<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<!--Import Google Icon Font-->
<link href="https://fonts.googleapis.com/icon?family=Material+Icons"
	rel="stylesheet">
<!-- Favicons-->
<link rel="icon" href="${pageContext.servletContext.contextPath}/resources/images/favicon.ico" sizes="32x32">
<!-- Favicons-->
<link rel="apple-touch-icon-precomposed" href="${pageContext.servletContext.contextPath}/resources/images/favicon.ico">
<!-- For iPhone -->
<meta name="msapplication-TileColor" content="#00bcd4">
<meta name="msapplication-TileImage" content="${pageContext.servletContext.contextPath}/resources/images/favicon.ico">
<!--Import materialize.css-->
<link type="text/css" rel="stylesheet" href="${pageContext.servletContext.contextPath}/resources/css/materialize.min.css" media="screen,projection" />
<!-- CORE CSS-->
<link href="${pageContext.servletContext.contextPath}/resources/css/style.css" type="text/css" rel="stylesheet">
<!-- Custome CSS-->
<link href="${pageContext.servletContext.contextPath}/resources/css/custom/custom.css" type="text/css" rel="stylesheet">
<!-- INCLUDED PLUGIN CSS ON THIS PAGE -->
<link href="${pageContext.servletContext.contextPath}/resources/js/perfect-scrollbar/perfect-scrollbar.css" type="text/css" rel="stylesheet">
<link href="${pageContext.servletContext.contextPath}/resources/js/flag-icon/css/flag-icon.min.css" type="text/css" rel="stylesheet">
<!--Let browser know website is optimized for mobile-->
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
	
<title>Spring MVC</title>
</head>
<body>
	<div style="margin-top: 50px;" class="container">
		<div class="row" style="text-align: center;">
			<a href="./login"><i class="material-icons">add_circle</i></a>
		</div>
	</div>
	
	<div style="margin-top: 50px;" class="container">
		<div class="row" style="text-align: center;">
			<a href="./register"><i class="material-icons">send</i></a>
		</div>
	</div>
</body>
</html>