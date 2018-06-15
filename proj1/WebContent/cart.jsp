<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<!-- <script src="scripts/sorttable.js"></script> -->
<script type="text/javascript" src="//ajax.googleapis.com/ajax/libs/jquery/2.0.0/jquery.min.js"></script>
<script type="text/javascript" src="scripts/jquery.tablesorter.js"></script> 
<link rel="stylesheet" href="https://unpkg.com/purecss@1.0.0/build/pure-min.css" integrity="sha384-nn4HPE8lTHyVtfCBi5yW9d20FjT8BJwUXyWZT9InLYax14RDjBj46LmSztkmNP9w" crossorigin="anonymous">
<link href="https://fonts.googleapis.com/css?family=Ubuntu" rel="stylesheet">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Fabflix: Movies</title>
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
	
	.emptyCart {
		color: #BF0000;
		font-family: 'Ubuntu', sans-serif;
		font-size: 25px;
		padding-top: 20px;
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
	
	input[type=submit]:hover, .checkout:hover {
		background-color: #750B00;
	}
	
	.checkoutDiv {
		text-align: center;
	}

	.pagination {
   	 	display: inline-block;
	}

	.pagination a {
	    color: black;
	    float: left;
	    padding: 8px 16px;
	    text-decoration: none;
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
	
	#pgs{
	 width:100px;   
	}
</style>
</head>

<body>

<c:set var = "TITLE_INDEX" scope = "session" value = "${2}" />
<c:set var ="CURR_PAGE" scope = "session" value = "${page}" />
<div>
	<b><a class = "title" href="Home"> Fabflix </a></b>
	<H1>Shopping Cart</H1>
	
	<form action="Search" method="get">
		<input type="submit" value="Search">
	</form>
</div>
<br><br>
<div>
<c:choose>
<c:when test="${tableInfo.size() > 0}" >
<table id="movieTable" class = "tablesorter pure-table pure-table-bordered">
<thead>
	<tr id = "headingRow">
		<c:forEach items="${colNames}" var="col" varStatus="h_loop">
		<c:choose>
			<c:when test="${h_loop.index != 2 && h_loop.index != 7 && h_loop.index != 0}">
				<th class="sorttable_nosort" id = "heading"><b>${col}</b></th>
			</c:when>
			<c:otherwise>
				<th id = "heading"><b>${col}</b></th>
			</c:otherwise>
		</c:choose>
		</c:forEach>
	</tr>
	</thead>
	<tbody>
	<c:forEach items="${tableInfo}" var="TableInfo" varStatus="loop1">
		<tr id = "infoRow">
			<c:forEach items="${TableInfo}" var="Tinfo" varStatus="loop2">
				<c:choose>
					<c:when test="${loop2.index == 3}">
					<td id = "info">
							<form method="get" action="Cart" class="inline">
							  <input type="hidden" name="changeQuantity" value="reduce">
							  <input type="hidden" name="title" value="${tableInfo.get(loop1.index).get(1)}">
							  <button type="submit" class="link-button">						    
									${Tinfo}
							  </button>
							</form>
						</td>
					</c:when>
					<c:when test="${loop2.index == 4}">
					<td id = "info">
							<form method="get" action="Cart" class="inline">
							  <input type="hidden" name="changeQuantity" value="increase">
							  <input type="hidden" name="title" value="${tableInfo.get(loop1.index).get(1)}">
							  <button type="submit" class="link-button">						    
									${Tinfo}
							  </button>
							</form>
						</td>
					</c:when>
					<c:otherwise>
						<td sorttable_customkey="${Tinfo}" id = "info">${Tinfo}</td>
					</c:otherwise>
				</c:choose>
			</c:forEach>
		</tr>
	</c:forEach>
	</tbody>
</table>
<br>
<div class = "checkoutDiv">
	<form method="get" action="Checkout" class="inline">
	<input type="hidden" name="checkout" value="true">
	<button class="checkout" type="submit">Checkout</button>
	</form>
</div>
</c:when>
<c:otherwise>
<p class = "emptyCart">Cart is Empty</p>
</c:otherwise>

</c:choose>
<div>
<p></p>
</div>
</div>

</body>
</html>