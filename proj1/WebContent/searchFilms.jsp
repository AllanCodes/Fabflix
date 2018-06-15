<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<!-- <script src="scripts/sorttable.js"></script> -->
<link rel="stylesheet" href="https://unpkg.com/purecss@1.0.0/build/pure-min.css" integrity="sha384-nn4HPE8lTHyVtfCBi5yW9d20FjT8BJwUXyWZT9InLYax14RDjBj46LmSztkmNP9w" crossorigin="anonymous">
<script type="text/javascript" src="//ajax.googleapis.com/ajax/libs/jquery/2.0.0/jquery.min.js"></script>
<script src="scripts/jquery.tablesorter.js"></script> 
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="https://fonts.googleapis.com/css?family=Ubuntu" rel="stylesheet">
<title>Fabflix</title>
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
	
	.title {
		font-size: 50px;
		color: #BF0000;
		font-family: 'Ubuntu', sans-serif;
		position: absolute;
		top: 15px;
		left: 16px;
		cursor: pointer;
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
	
	.noResults {
		color: #BF0000;
		font-family: 'Ubuntu', sans-serif;
		font-size: 25px;
		padding-top: 20px;
		text-align: center;
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

</head>

<body>
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
<c:set var = "TITLE_INDEX" scope = "session" value = "${2}"/>

<div>
	<b><a class = "title" href="Home"> Fabflix </a></b>
	<H1>Movies matching ${searchQuery}</H1>
	<!--
	<form action="Search" method="get">
	<input type="submit" value="Search" >
	</form>
	 -->
</div>
<form method="get" id="pagesform">
 <select id="pgs" name="pageCount" form="pagesform">
  <option onClick="performSelect()" value="50">50</option>
  <option onClick="performSelect()" value="100">100</option>
  <option onClick="performSelect()" value="500">500</option>
  <option onClick="performSelect(value)" value="-1">No limit</option>
</select> 
<div class="pagination">
<input type="hidden" name="currPage" value="${currPage}">
<button onClick="return performSelect(id)" id="backward" type="submit">&laquo;</button>
<button onClick="return performSelect(id)" id="forward" type="submit">&raquo;</button>
</div>
</form>
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
					<c:when test="${loop2.index == 2}">
						<td id = "info">
							<form method="get" action="Movie" class="inline">
							  <input type="hidden" name="title" value="${Tinfo}">
							  <button type="submit" class="link-button">						    
									${Tinfo}
							  </button>
							</form>
						</td>
					</c:when>
					<c:when test="${loop2.index == 8}">
					<td id = "info">
							<form method="get" action="Cart" class="inline">
							  <input type="hidden" name="addMovie" value="${tableInfo.get(loop1.index).get(2)}">
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
</c:when>
<c:otherwise>
<p class="noResults">No Matches Were Found.</p>
</c:otherwise>

</c:choose>

</div>
</body>
<script>
function performSelect(id) {
	var e = document.getElementById("pgs");
	var numPage = e.options[e.selectedIndex].value;
	var currPage = ${currPage};
    let repl = window.location.href.replace(/&pageCount=(-1|500|1000|50|100)|&currPage=[\d]*/g, '');
    if (id === "forward") {
    	currPage += 1;
    } else {
    	if (currPage > 1) currPage -= 1;
    	else currPage = 1;
    }
    history.replaceState(null,null, repl + "&pageCount=" + numPage + "&currPage=" + currPage);
    location.reload(); 
    return false;
}

$(document).ready(function() 
	    { 
	        $("#movieTable").tablesorter(); 
	    } 
	); 
$('#movieTable tr:gt(0)').each(function(){
    let v = $('td:nth-child(7)', $(this)).html();
    let arr = v.split(",");
    let fullString = "";
    for (let i = 0; i < arr.length; i++) {
    	if (i + 1 == arr.length) {
        	fullString += "<form method='get' action='Stars' class='inline'> <input type='hidden' name='stars' value='" + arr[i].trim() + "'> <button type='submit' class='link-button'>" + arr[i] + "</button> </form>";
    	} else
        	fullString += "<form method='get' action='Stars' class='inline'> <input type='hidden' name='stars' value='" + arr[i].trim() + "'> <button type='submit' class='link-button'>" + arr[i] + "</button> </form> ";
    }
    $('td:nth-child(7)', $(this)).html(fullString);
})
$('#movieTable tr:gt(0)').each(function(){
    let v = $('td:nth-child(6)', $(this)).html();
    let arr = v.split(",");
    let fullString = "";
    for (let i = 0; i < arr.length; i++) {
    	if (i + 1 == arr.length) {
        	fullString += "<form method='get' action='MovieList' class='inline'> <input type='hidden' name='genre' value='" + arr[i].trim() + "'> <button type='submit' class='link-button'>" + arr[i] + "</button> </form>";
    	} else
        	fullString += "<form method='get' action='MovieList' class='inline'> <input type='hidden' name='genre' value='" + arr[i].trim() + "'> <button type='submit' class='link-button'>" + arr[i] + "</button> </form> ";
    }
    $('td:nth-child(6)', $(this)).html(fullString);
})
</script>
</html>