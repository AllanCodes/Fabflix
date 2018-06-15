<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<script type="text/javascript" src="//ajax.googleapis.com/ajax/libs/jquery/2.0.0/jquery.min.js"></script>
<link href="https://fonts.googleapis.com/css?family=Ubuntu" rel="stylesheet">
 <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery.devbridge-autocomplete/1.4.7/jquery.autocomplete.min.js"></script>
<title>FabFlix</title>
</head>
<style>

	body {
		margin: 0;
		padding: 0;
		background-color: #100E11
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

	
	input[type=text] {
		width: 200px;
		border-radius: 2px;
		border: 1px solid #E2D7D7;
		padding: 10px 15px;
		box-sizing: border-box;
		margin: 8px 0;
		font-size: 14px;
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

	.mainContainer {
		background-color: #382E2E;
		position: relative;
		text-align: center;
		color: white;
	}
	
	.centered {
		position: absolute;
		width: 65%;
		top: 50%;
		left: 50%;
		transform: translate(-50%, -50%);
	}
	
	label {
		color: black;
		float: left;
		padding-top: 5px;
		font-family: 'Ubuntu', sans-serif;
		font-size: 16px;
	}
	
	button {
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
	
	button:hover {
		background-color: #750B00;
	}
	
	.browse {
		background-color: #750B00;
		float: left;
		border: 1px solid #BF0000;
		border-radius: 5px;
		width: 250px;
		height: 150px;
		margin-right: 50px;
		text-align: center;
		cursor: pointer;
	}
	
	.search {
		background-color: #750B00;
		float: right;
		border: 1px solid #BF0000;
		border-radius: 5px;
		width: 250px;
		height: 150px;
		text-align: center;
		cursor: pointer;
	}
	
	.browse:hover, .search:hover {
		border: 1px solid white;
	}
	
	#searchButton {
		background-color: #BF0000;
		color: white;
		font-size: 20px;
		border: none;
		border-radius: 3px;
		padding-right: 15px;
		padding-left: 15px;
		height: 40px;
		width: 200px;
		cursor: pointer;
		position: absolute;
		top: 100px;
		right: 25px;
		
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
	.dashboard {
		background-color: #BF0000;
		color: white;
		font-size: 20px;
		border: none;
		border-radius: 3px;
		padding-right: 15px;
		padding-left: 15px;
		position: absolute;
		top: 30px;
		right: 255px;
		cursor: pointer;
	}
	.dashboard:hover {
		background-color: #750B00;
	}
	.logout:hover {
		background-color: #750B00;
	}
	
	.autocomplete-suggestions { border: 1px solid #999; background: #FFF; overflow: auto; }
	.autocomplete-suggestion { padding: 2px 5px; white-space: nowrap; overflow: hidden; }
	.autocomplete-selected { background: #F0F0F0; }
	.autocomplete-suggestions strong { font-weight: normal; color: #3399FF; }
	.autocomplete-group { padding: 2px 5px; }
	.autocomplete-group strong { display: block; border-bottom: 1px solid #000; }
</style>
<body>
<div>
	<b><a class = "title" href="Home"> Fabflix </a></b>
	<form method="post" action="Cart" class="inline">
		<button type="submit">						    
			Checkout
		</button>
	</form>
</div>
<div>
<form method="get" action="_dashboard" class="inline">
		<button type="submit" class="dashboard">						    
			Employees
		</button>
</form>
<div>
<form method="post" action="LogoutPage" class="inline">
		<button type="submit" class="logout">						    
			Logout
		</button>
</form>
</div>
<div class = "centered">
	<div class = "browse">
		<br>
		<a href="Browse">Browse</a>
		<p>Browse our large catalog of movies, which includes movies from an extensive variety of genres.</p>
	</div>
	<div class = "search">
		<br>
	<!-- <form method="get" class="searchbox"> -->
	  <input type="text" id="autocomplete" placeholder="Enter Search Here" name="title" onfocus="this.placeholder=''" onblur="this.placeholder='Enter Search Here'">
	  <button id = "searchButton" type="submit"><i class="fa fa-search"></i></button>
	<!-- </form> --> 
	</div>
</div>

<script>
$(".browse").click(function() {
	  window.location = $(this).find("a").attr("href"); 
	  return false;
	});
	
/* $(".search").click(function() {
	  window.location = $(this).find("a").attr("href"); 
	  return false;
	}); */
</script>
<script src="scripts/index.js"></script>
</body>
</html>