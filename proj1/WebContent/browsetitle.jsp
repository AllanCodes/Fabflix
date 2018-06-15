<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<!-- <script src="scripts/sorttable.js"></script> -->
<link rel="stylesheet" href="https://unpkg.com/purecss@1.0.0/build/pure-min.css" integrity="sha384-nn4HPE8lTHyVtfCBi5yW9d20FjT8BJwUXyWZT9InLYax14RDjBj46LmSztkmNP9w" crossorigin="anonymous">
<link href="https://fonts.googleapis.com/css?family=Ubuntu" rel="stylesheet">
<script src="scripts/jquery.tablesorter.js"></script> 
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Fabflix</title>
</head>
<style>

	body {
		margin: 0;
		padding: 0;
		background-color: #100E11;
	}
	
	a {
		text-decoration: none;
		color: white;
		font-family: 'Ubuntu', sans-serif;
		font-size: 25px;
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

	.pagination a {
	    color: black;
	    /* float: left; */
	    padding: 8px 16px;
	    text-decoration: none;
	}
	
	.pagination a.active {
	    background-color: #4CAF50;
	    color: white;
 	    border-radius: 5px;
	}
	
	.pagination a:hover:not(.active) {
	    background-color: #ddd;
	    border-radius: 5px;
	}
	
	h1 {
		text-align: center;
		font-family: 'Ubuntu', sans-serif;
		color: white;
	}
	
	input {
		background-color: #BF0000;
		color: white;
		font-size: 20px;
		border: none;
		border-radius: 2px;
		font-family: 'Ubuntu', sans-serif;
		padding-right: 15px;
		padding-left: 15px;
		float: right;
	}
	
	table {
		border-collapse: collapse;
		width: 70%;
		margin: 0px auto;
	}
	
	#headingRow {
		background-color: #750B00;
		border: 1px solid black;
		color: white;
		font-size: 20px;
	}

	#infoRow:nth-child(even) {
		background-color: #F2F2F2;
	}
	
	#infoRow:nth-child(odd) {
		background-color: white;
	}
	
	#infoRow:hover {
		background-color: #E2D7D7;
	}
	.inline {
	  display: inline;
	}
	
	.link-button {
	  background: none;
	  border: none;
	  color: #BF0000;
	  text-decoration: none;
	  cursor: pointer;
	  font-size: 1em;
	  font-family: serif;
	}
	
	.link-button:hover {
		color: white;
	}
	
	.link-button:focus {
	  outline: none;
	}
	.link-button:active {
	  color:red;
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
</style>

<body>
<b><a class = "title" href="Home"> Fabflix </a></b>
<H1>Browse by Title</H1>
<form method="post" action="Cart" class="inline">
		<button type="submit" class="checkout">						    
			Checkout
		</button>
</form>
<div>
<form method="post" action="LogoutPage" class="inline">
		<button type="submit" class="logout">						    
			Logout
		</button>
</form>
</div>
<br><br>
<table id="movieTable" class = "tablesorter pure-table pure-table-bordered">
	<tr id = "headingRow">
		<th align='center'>Browse Movies By Title</th>
	</tr>
	<c:forEach items="${letters}" var="letter" varStatus="loop1">
	<tr id="infoRow">
		<td align="center" sorttable_customkey="${letter}">
			<form method="get" action="MovieList" class="inline">
				<input type="hidden" name="title" value="${letter}">
				  <button type="submit" class="link-button">			    
					${letter}
				  </button>
			</form>
		</td>	
	</tr>
	</c:forEach>
</table>
<br><br>

</body>
<script>
$(document).ready(function() 
	    { 
	        $("#movieTable").tablesorter(); 
	    } 
	); 
</script>
</html>