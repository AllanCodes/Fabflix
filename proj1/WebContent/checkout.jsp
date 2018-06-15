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
		background-color: #100E11;
	}
	
	a {
		text-decoration: none;
		color: white;
		font-family: 'Ubuntu', sans-serif;
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
		top: 55%;
		left: 50%;
		transform: translate(-50%, -50%);
	}
	
	#infoForm {
		background-color: #F2F2F2;
		padding: 20px;
		
	}
	
	.topRight {
		position: absolute;
		top: 8px;
		right: 16px;
	}
	
	#checkoutButton {
		width: 100%;
		background-color: #BF0000;
		font-size: 18px;
		color: white;
		border: none;
		border-radius: 2px;
		padding: 16px 20px;
	}
	
	input[type=text] {
		width: 100%;
		border-radius: 2px;
		border: 1px solid #E2D7D7;
		padding: 14px 20px;
		box-sizing: border-box;
		margin: 8px 0;
		font-size: 14px;
	}
	
	#checkoutTitle {
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
	
	#checkoutButton:hover {
		background-color: #750B00;
	}
	
</style>
<body>

<b><a class = "title" href="Home"> Fabflix </a></b>
<div class="centered">
	<div id="infoForm">
		<div>
			<h2 id = "checkoutTitle">Checkout</h2>
			<br><br><br>
		</div>
		<form method="post" class="searchbox" action="Checkout">	
			<p>${errorPage}</p>
		  <label id="userLabel">First Name</label>
		  <input type="text" placeholder="John" name="first" onfocus="this.placeholder=''" onblur="this.placeholder='First Name'">
		  <label id="userLabel">Last Name</label>
		  <input type="text" placeholder="Appleseed" name="last" onfocus="this.placeholder=''" onblur="this.placeholder='Last Name'">
		  <label id="userLabel">Credit Card</label>
		  <input type="text" placeholder="2547-947856-35896" name="creditcard" onfocus="this.placeholder=''" onblur="this.placeholder='Credit Card'">
		  <label id="userLabel">Exp. Date</label>
		  <input type="text" placeholder="02/2018" name="expdate" onfocus="this.placeholder=''" onblur="this.placeholder='Exp. Date'">
		  <button id="checkoutButton" type="submit">Checkout</button>
		</form> 
	</div>
</div>
</body>
</html>