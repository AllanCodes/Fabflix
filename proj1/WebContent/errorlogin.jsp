<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.database.access.Basic" %>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src='https://www.google.com/recaptcha/api.js'></script>
<link href="https://fonts.googleapis.com/css?family=Ubuntu" rel="stylesheet">
<title>FabFlix</title>
<style>
	body {
		margin: 0;
		padding: 0;
		background-color: black;
	}
	
	.newSection {
		background-color: #BF0000;
		color: white;
		text-align: center;
		height: 50px;
	}
	
	a {
		text-decoration: none;
		color: white;
		font-family: 'Ubuntu', sans-serif;
	}
	
	.title {
		font-size: 65px;
		text-align: center;
		color: #BF0000;
		font-family: 'Ubuntu', sans-serif;
	}
	
	.homeImage {
		display: block;
		margin-left: auto;
		margin-right: auto;
		width: 100%;
		opacity: 0.20;
	}

	.mainContainer {
		background-color: #382E2E;
		position: relative;
		text-align: center;
		color: white;
	}
	
	.centered {
		position: absolute;
		top: 50%;
		left: 50%;
		transform: translate(-50%, -50%);
	}

	.topLeft {
		position: absolute;
		top: 8px;
		left: 16px;
	}
	
	.topRight {
		position: absolute;
		top: 8px;
		right: 16px;
	}
	
	#loginButton {
		background-color: #BF0000;
		color: white;
		font-size: 25px;
		border: none;
		border-radius: 3px;
		padding-right: 15px;
		padding-left: 15px;
	}
	
	input[type=text], input[type=password] {
		width: 100%;
		border-radius: 2px;
		border: 1px solid #E2D7D7;
		padding: 14px 20px;
		box-sizing: border-box;
		margin: 8px 0;
		font-size: 14px;
	}
	
	#signInTitle {
		color: black;
		float: left;
		font-family: 'Ubuntu', sans-serif;
		font-size: 25px;
		margin-top: -5px;
	}
	
	label {
		color: black;
		float: left;
		padding-top: 5px;
		font-family: 'Ubuntu', sans-serif;
		font-size: 16px;
	}
	
	input[type=submit] {
		width: 100%;
		background-color: #BF0000;
		font-size: 18px;
		color: white;
		border: none;
		border-radius: 2px;
		padding: 16px 20px;
	}
	
	input[type=submit]:hover {
		background-color: #750B00;
	}
	
	#loginForm {
		display: block;
		background-color: #F2F2F2;
		padding: 20px;
	}
	
	#errorMessage {
		color: #BF0000;
		float: left;
		margin-top: -10px;
		margin-bottom: 10px;
		display: inline;
	}
	
	#userLabel {
		margin-top: -30px;
	}
		
</style>
</head>
<body>
<%
Cookie[] cookies = request.getCookies();
if (cookies != null) {
    for (Cookie c : cookies) {
    	if (c.getName().equals("user")) {
    		 response.sendRedirect("Home");
    	}
	}
}
%>
<div class = "mainContainer">
	<img class = "homeImage" src="https://boygeniusreport.files.wordpress.com/2016/03/movies-tiles.jpg?quality=98&strip=all&w=1200">
	<div class = "centered">
		<h1 class = "title"> Fabflix </h1>
		<div id = "loginForm">
			<div>
				<h2 id = "signInTitle">Sign In</h2>
				<br><br><br>
 				<p id = "errorMessage">${errormsg}</p>
		</div>
			<br><br><br>
			<div>
				<form id="loginform" method="POST" action="login">
					<label id="userLabel">User</label>
				  	<input id="user" type="text" name="user" placeholder="Email">
				  	<label>Password</label>
				  	<input id="pass" type="password" name="pass" placeholder="Password">
					<br><br>
					<input id="submitbutton" type="submit" name="submit" value="Submit">
					<div class="g-recaptcha" data-sitekey="6LcbZ1cUAAAAAJR_bgzK_esCD93zDbAahgdNy4Ow"></div>
				</form>
			</div> 
		</div>	</div>
</div>

<script>
function displayLogin() {
		document.getElementById("loginForm").style.display = "block";
		document.getElementById("loginButton").style.display = "none";
}
</script>
</body>
</html>