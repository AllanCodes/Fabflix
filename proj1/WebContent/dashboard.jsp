<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link href="https://fonts.googleapis.com/css?family=Ubuntu" rel="stylesheet">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<script type="text/javascript" src="//ajax.googleapis.com/ajax/libs/jquery/2.0.0/jquery.min.js"></script>
<script src="scripts/jquery.tablesorter.js"></script> 
<link rel="stylesheet" href="https://unpkg.com/purecss@1.0.0/build/pure-min.css" integrity="sha384-nn4HPE8lTHyVtfCBi5yW9d20FjT8BJwUXyWZT9InLYax14RDjBj46LmSztkmNP9w" crossorigin="anonymous">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Fabflix</title>
<style>

	body {
		margin: 0;
		padding: 0;
		background-color: #503934;
	}
	
	a {
		text-decoration: none;
		color: white;
		font-family: 'Ubuntu', sans-serif;
		font-size: 25px;
	}
	
	p {
		color: white;
		font-family: 'Ubuntu', sans-serif;
		font-size: 15px;
	}
	
	.title {
		font-size: 50px;
		color: #BF0000;
		font-family: 'Ubuntu', sans-serif;
		position: absolute;
		top: 15px;
		left: 16px;
		cursor: pointer;
	}
	
	.centered {
		position: absolute;
		width: 80%;
		top: 35%;
		left: 50%;
		transform: translate(-50%, -50%);
	}
	
	.checkout {
		background-color: #BF0000;
		color: white;
		font-size: 20px;
		border: none;
		border-radius: 3px;
		padding-right: 15px;
		padding-left: 15px;
		position: absolute;
		top: 30px;
		right: 16px;
		cursor: pointer;
	}
	
	.checkout:hover {
		background-color: #750B00;
	}
	
	input[type=text] {
		width: 200px;
		border-radius: 2px;
		border: 1px solid #E2D7D7;
		padding: 10px 20px;
		box-sizing: border-box;
		margin: 8px 0;
		font-size: 14px;
	}
	
	#searchButton {
		background-color: #BF0000;
		color: white;
		font-size: 20px;
		border: none;
		border-radius: 3px;
		padding-bottom: 15px;
		padding-right: 15px;
		padding-left: 15px;
		padding-top: 5px;
		height: 36px;
		cursor: pointer;
	}
	
	#searchButton:hover {
		background-color: #750B00;
	}
	
	.logout {
		background-color: #BF0000;
		color: white;
		font-size: 20px;
		border: none;
		border-radius: 3px;
		padding-right: 15px;
		padding-left: 15px;
		position: absolute;
		top: 30px;
		right: 150px;
		cursor: pointer;
	}
	
	.logout:hover {
		background-color: #750B00;
	}
	
		
	table,
		td {
		  border: 1px solid black;
		}
		
		table {
		  float: left;
		  width: 30%;
		  margin: 10px;
		}
		
		table:nth-child(3n+1) {
		  clear:left;
		}
		.main h1 {
		font-weight: 600;
		font-family: 'Ubuntu', sans-serif;
		position: relative;  
		font-size: 36px;
		line-height: 40px;
		padding: 15px 15px 15px 15%;
		color: #4d1205;
		border-radius: 0 10px 0 10px;
}
		
		
</style>
</head>
<body>

<div>
	<!-- <p style='color: red;font-size: 20px;'>Enter a Star name, and an optional year of birth to add a new Star!</p> -->
	<h1 class="main" style="color: #996c62;">Enter a star name, and an optional year of birth to add a new star!</h1>
	<form method="post" class="searchbox" action="_dashboard">
	  <input type="text" placeholder="Star name" name="Addstars" onfocus="this.placeholder=''" onblur="this.placeholder='Star name'">
	  <input type="text" placeholder="Birth year" name="Addstarsyear" onfocus="this.placeholder=''" onblur="this.placeholder='Birth year'">
	  <button id = "searchButton" type="submit"><i class="fa fa-search"></i></button>
	</form> 
	
	<h1 class="main" style="color: #996c62;">Enter the details of a movie, and its star information, to add a new movie!</h1>
	<form method="post" class="searchbox" action="_dashboard">
	  <input type="text" placeholder="Movie name" name="Addmoviename" onfocus="this.placeholder=''" onblur="this.placeholder='Movie name'">
	  <input type="text" placeholder="Movie year" name="Addmovieyear" onfocus="this.placeholder=''" onblur="this.placeholder='Movie year'">
	  <input type="text" placeholder="Director" name="Addmoviedirector" onfocus="this.placeholder=''" onblur="this.placeholder='Director'">
	  <input type="text" placeholder="Star" name="Addmoviestar" onfocus="this.placeholder=''" onblur="this.placeholder='Star'">
	  <input type="text" placeholder="Birth year" name="Addmoviestaryear" onfocus="this.placeholder=''" onblur="this.placeholder='Birth year'">
	  <input type="text" placeholder="Genre" name="Addmoviegenre" onfocus="this.placeholder=''" onblur="this.placeholder='Genre'">
	  <button id = "searchButton" type="submit"><i class="fa fa-search"></i></button>
	</form> 
	
	<p>${errormsg}</p>
</div>

<div>
<c:choose>
<c:when test="${tables.size() > 0}" >
<c:forEach items="${tables}" var="tableEntry">
<table class="tablesorter pure-table pure-table-bordered">
<tr bgcolor="#d23913">
	<th></th>
</tr>
<c:forEach items="${tableEntry}" var="columns">
	<tr bgcolor="#d23913"><td>${columns}</td></tr>
</c:forEach>
</table>
</c:forEach>
</c:when>
<c:otherwise>
<p class="noResults">Cannot display tables</p>
</c:otherwise>
</c:choose>
</div>


</body>
</html>