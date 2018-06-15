<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="https://fonts.googleapis.com/css?family=Ubuntu" rel="stylesheet">
<link rel="stylesheet" href="https://unpkg.com/purecss@1.0.0/build/pure-min.css" integrity="sha384-nn4HPE8lTHyVtfCBi5yW9d20FjT8BJwUXyWZT9InLYax14RDjBj46LmSztkmNP9w" crossorigin="anonymous">
<script type="text/javascript" src="//ajax.googleapis.com/ajax/libs/jquery/2.0.0/jquery.min.js"></script>
<script src="scripts/jquery.tablesorter.js"></script> 

<title>Checkout Successful</title>
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
	
	p {
		color: white;
		font-family: 'Ubuntu', sans-serif;
		font-size: 15px;
	}
	
	.checkoutSuccessful {
		color: white;
		font-family: 'Ubuntu', sans-serif;
		font-size: 25px;
		text-align: center;
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
		top: 35%;
		left: 50%;
		transform: translate(-50%, -50%);
	}
	
	input[type=submit] {
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
	
	.checkout {
		background-color: #BF0000;
		color: white;
		font-size: 20px;
		border: none;
		border-radius: 3px;
		padding-right: 15px;
		padding-left: 15px;
		cursor: pointer;
		align: center;
		top: 50%;
		left: 50%;
	}
	
	.homeButton {
		width: 100%;
		background-color: #BF0000;
		font-size: 18px;
		color: white;
		border: none;
		border-radius: 2px;
		padding: 16px 20px;
	}
	
	.homeButton:hover {
		background-color: #750B00;
	}
	
	h1 {
		text-align: center;
		font-family: 'Ubuntu', sans-serif;
		color: white;
	}
	
	table {
		border-collapse: collapse;
		width: 100%;
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
	
</style>
<body>

<b><a class = "title" href="Home"> Fabflix </a></b>
<div class="centered">
	<p class="checkoutSuccessful">Checkout Successful.</p>
<table id="movieTable" class = "tablesorter pure-table pure-table-bordered">
	<thead>
	<tr id = "headingRow">
		<th>Title</th>
		<th>Quantity</th>
		<th>ID</th>
	</tr>
	</thead>
	<tbody>
	<c:forEach items="${tableInfo}" var="TableInfo" varStatus="loop1">
		<tr id = "infoRow">
			<c:forEach items="${TableInfo}" var="Tinfo" varStatus="loop2">
			<td>${Tinfo}</td>
			</c:forEach>
		</tr>
	</c:forEach>
	</tbody>
</table>
	<form method="get" action="/Fabflix/Home">
		<button class="homeButton" type="submit">Home</button>
	</form>
</div>
</body>
</html>