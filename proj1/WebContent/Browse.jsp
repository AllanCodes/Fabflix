<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="https://fonts.googleapis.com/css?family=Ubuntu" rel="stylesheet">
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
		width: 65%;
		top: 50%;
		left: 50%;
		transform: translate(-50%, -50%);
	}
	
	.inline {
	  display: inline;
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
	
	.checkout:hover {
		background-color: #750B00;
	}
	
	.genreSearch {
		background-color: #750B00;
		float: left;
		border: 1px solid #BF0000;
		border-radius: 5px;
		width: 250px;
		height: 150px;
		margin-right: 50px;
		text-align: center;
		cursor: pointer;
		color: white;
		font-family: 'Ubuntu', sans-serif;
		font-size: 25px; 
	}
	
	.titleSearch {
		background-color: #750B00;
		float: right;
		border: 1px solid #BF0000;
		border-radius: 5px;
		width: 250px;
		height: 150px;
		text-align: center;
		cursor: pointer;
		color: white;
		font-family: 'Ubuntu', sans-serif;
		font-size: 25px;
	}
	
	.genreSearch:hover, .titleSearch:hover {
		border: 1px solid white;
	}
</style>
<body>

<b><a class = "title" href="Home"> Fabflix </a></b>

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
<div class = "centered">
	<div class = "geneSearch">
		<form method="post" action="Browse" class="inline">
			<input type="hidden" name="genre" value="true">
				<button type="submit" class="genreSearch">						    
					Browse By Genre <p>Browse the titles in our databases by their respective genres.</p>
				</button>
		</form>
	</div>
	<div class = "tileSearch">
		<form method="post" action="Browse" class="inline">
			<input type="hidden" name="title" value="true">
				<button type="submit" class="titleSearch">						    
					Browse By Title <p>Browse the titles in our databases in alphabetical order.</p>
				</button>
		</form>
	</div>
</div>

<script>
$(".genreSearch").click(function() {
	  window.location = $(this).find("a").attr("href"); 
	  return false;
	});
	
$(".titleSearch").click(function() {
	  window.location = $(this).find("a").attr("href"); 
	  return false;
	});
</script>

</body>
</html>