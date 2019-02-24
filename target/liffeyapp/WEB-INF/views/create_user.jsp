<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

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
	
<title>Join</title>
</head>
<body>

	<%-- <nav>
		<div class="nav-wrapper gradient-45deg-light-blue-cyan">
			<!-- style="background: #119DA4; -->
			<a href="${pageContext.servletContext.contextPath}" class="brand-logo">Spring MVC</a>
			<ul id="nav-mobile" class="right hide-on-med-and-down">
				<!-- <li><a href="sass.html">Sass</a></li>
				<li><a href="badges.html">Components</a></li>
				<li><a href="collapsible.html">JavaScript</a></li> -->
			</ul>
		</div>
	</nav> --%>
	
	<div style="margin-top: 50px;" class="container">
		<div class="row">
			<div class="col s12 m8 l6 offset-m2 offset-l3">
				<div class="card-panel">
					<div class="row">
						<form:form modelAttribute="user" class="col s12" method="POST"
							action="createUser">
							<div class="row">
								<div class="input-field col s12">
									<i class="material-icons prefix">perm_identity</i>
									<form:input path="fname" class="validate" value=""
										required="required" aria-required="true" />
									<form:label path="fname">First Name</form:label>
									<!-- path 는 user 오브젝트의 필드명과 같아야 함. -->
								</div>
							</div>

							<div class="row">
								<div class="input-field col s12">
									<i class="material-icons prefix">account_circle</i>
									<form:input path="lname" class="validate" value=""
										required="required" aria-required="true" />
									<form:label path="lname">Last Name</form:label>
								</div>
							</div>
							<div class="row">
								<div class="input-field col s12">
									<i class="material-icons prefix">lock_outline</i>
									<form:input path="password" type="password" value=""
										class="validate" required="required" aria-required="true" />
									<form:label path="password">Password</form:label>
								</div>
							</div>
							<div class="row">
								<div class="input-field col s12">
									<i class="material-icons prefix">email</i>
									<form:input path="email" type="email" class="validate" value=""
										required="required" aria-required="true" />
									<form:label path="email">Email</form:label>
								</div>
							</div>
							<button class="btn waves-effect waves-light cyan lighten-1 type="
								submit"
								name="action">
								Join <i class="material-icons right">send</i>
							</button>
						</form:form>
					</div>
				</div>
			</div>
		</div>
	</div> 

	<%@ include file="tail.jsp"%>