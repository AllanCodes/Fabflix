<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
 <!-- Load icon library -->
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<link href="https://fonts.googleapis.com/css?family=Ubuntu" rel="stylesheet">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Fabflix</title>
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
		padding-right: 15px;
		padding-left: 15px;
		height: 40px;
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
		
</style>
<body>

<div>
	<b><a class = "title" href="Home"> Fabflix </a></b>
	
	<form method="post" action="Cart" class="inline">
		<button type="submit" class="checkout">						    
			Checkout
		</button>
	</form>
</div>
<div>
<form method="post" action="LogoutPage" class="inline">
		<button type="submit" class="logout">						    
			Logout
		</button>
</form>
</div>
<div class = "centered">
	<form method="get" class="searchbox" action="MovieList">
	  <input type="text" placeholder="Title.." name="title" onfocus="this.placeholder=''" onblur="this.placeholder='Title..'">
	  <input type="text" placeholder="Director.." name="director" onfocus="this.placeholder=''" onblur="this.placeholder='Director..'">
	  <input type="text" placeholder="Stars..." name="stars" onfocus="this.placeholder=''" onblur="this.placeholder='Stars..'">
	  <input type="text" placeholder="Year.." name="year" onfocus="this.placeholder=''" onblur="this.placeholder='Year..'">
	  <button id = "searchButton" type="submit"><i class="fa fa-search"></i></button>
	</form> 
	
	<form method="get" class="searchbox" action="Stars">
	  <input type="text" placeholder="Stars..." name="stars" onfocus="this.placeholder=''" onblur="this.placeholder='Stars..'">
	  <button id = "searchButton" type="submit"><i class="fa fa-search"></i></button>
	</form> 
</div>
</body>
</html>