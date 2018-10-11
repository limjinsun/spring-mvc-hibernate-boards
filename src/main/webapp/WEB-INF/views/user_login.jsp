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
<link rel="icon"
	href="${pageContext.servletContext.contextPath}/resources/images/favicon.ico"
	sizes="32x32">
<!-- Favicons-->
<link rel="apple-touch-icon-precomposed"
	href="${pageContext.servletContext.contextPath}/resources/images/favicon.ico">
<!-- For iPhone -->
<meta name="msapplication-TileColor" content="#00bcd4">
<meta name="msapplication-TileImage"
	content="${pageContext.servletContext.contextPath}/resources/images/favicon.ico">


<!--Import materialize.css-->
<%-- <link type="text/css" rel="stylesheet"
	href="${pageContext.servletContext.contextPath}/resources/css/materialize.min.css"
	media="screen,projection" /> don't need. to prevent mixed css with custom css. --%>
<!-- CORE CSS-->
<%-- <link
	href="${pageContext.servletContext.contextPath}/resources/css/style.css"
	type="text/css" rel="stylesheet"> --%>
<!-- Custome CSS-->
<link
	href="${pageContext.servletContext.contextPath}/resources/css/custom/custom.css"
	type="text/css" rel="stylesheet">
<!-- INCLUDED PLUGIN CSS ON THIS PAGE -->
<link
	href="${pageContext.servletContext.contextPath}/resources/js/perfect-scrollbar/perfect-scrollbar.css"
	type="text/css" rel="stylesheet">
<link
	href="${pageContext.servletContext.contextPath}/resources/js/flag-icon/css/flag-icon.min.css"
	type="text/css" rel="stylesheet">
<!--Let browser know website is optimized for mobile-->
<meta name="viewport" content="width=device-width, initial-scale=1.0" />

<title>Login</title>
</head>
<body>

	<div class="limiter">
		<div class="container-login100">
			<div class="wrap-login100 p-t-50 p-b-90">

				<form:form modelAttribute="user" class="login100-form validate-form flex-sb flex-w" method="POST" action="userLogin">
					<span class="login100-form-title p-b-51"> Spring MVC Forum Login </span>
					<div class="wrap-input100 validate-input m-b-16" data-validate="Username is required">
						<form:input path="email" type="email" class="input100" name="email" value="" required="required" aria-required="true" placeholder="Email" />
						<span class="focus-input100"></span>
					</div>

					<div class="wrap-input100 validate-input m-b-16" data-validate="Password is required">
						<form:input path="password" value="" required="required" aria-required="true" class="input100" type="password" name="pass" placeholder="Password" />
						<span class="focus-input100"></span>
					</div>

					<div class="flex-sb-m w-full p-t-3 p-b-24">
						
						<div class="contact100-form-checkbox">
							<input class="input-checkbox100" id="ckb1" type="checkbox" name="remember-me"> <label class="label-checkbox100" for="ckb1"> Remember me </label>
						</div>
						<div>
							<a href="${pageContext.servletContext.contextPath}/user/createUser" class="txt1"> Register? </a>
						</div>

						<div>
							<a href="#" class="txt1"> Forgot? </a>
						</div>

					</div>
		
					<!--  <button class="btn waves-effect waves-light cyan lighten-1 type="submit"
								name="action">
								Join <i class="material-icons right">send</i>
							</button>
							 -->

					<div class="container-login100-form-btn m-t-17">
						<button class="login100-form-btn" type="submit" name="action" >Login</button>
					</div>

				</form:form>
			</div>
		</div>
	</div>




	<%@ include file="tail.jsp"%>