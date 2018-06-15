package com.fablix.servlets;

import java.io.*;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Cookie;

import com.database.access.Basic;
import com.database.access.Queries;
import com.database.access.RecaptchaVerifyUtils;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

/**
 * Servlet implementation class LoginPage
 */
@WebServlet(urlPatterns= {"/login"})
public class LoginPage extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginPage() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.sendRedirect("/Fabflix/");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Basic DBObj = new Basic();
		String id;	
        
		String user = request.getParameter("user");
		String pass = request.getParameter("pass");
		System.out.println(user);
		System.out.println(pass);
		id = DBObj.performLogin(user, pass);
		String gRecaptchaResponse = request.getParameter("g-recaptcha-response");
		try {
            RecaptchaVerifyUtils.verify(gRecaptchaResponse);
		} catch (Exception e) {
			request.setAttribute("errormsg", "Recaptcha error");
			id = null;
			RequestDispatcher view = request.getRequestDispatcher("errorlogin.jsp");
	        view.forward(request, response);
	        return;
		}
		if ( id != null ) {
			Cookie loginCookie = new Cookie("user", id);
			loginCookie.setMaxAge(60*60 * 24);
			response.addCookie(loginCookie);
			response.sendRedirect("/Fabflix/Home");
		} else {
			request.setAttribute("errormsg", "Incorrect username or password. Please try again");
			RequestDispatcher view = request.getRequestDispatcher("errorlogin.jsp");
	        view.forward(request, response);
		}
	}

}
