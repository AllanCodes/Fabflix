package com.fablix.servlets;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.database.access.Basic;
import com.database.access.Queries;
//import com.mysql.jdbc.Connection;
//import com.mysql.jdbc.Statement;
import java.sql.Connection;
import java.sql.Statement;
/**
 * Servlet implementation class Browse
 */
@WebServlet(urlPatterns= {"/Browse"})
public class Browse extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Browse() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Basic DBObj = new Basic();
		String id = DBObj.checkLoginCookie(request, response);
        if (id == null) {
        	response.sendRedirect("/Fabflix");
        	return;
        }
        
        
		RequestDispatcher view = request.getRequestDispatcher("Browse.jsp");
        view.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Basic DBObj = new Basic();
		Queries queries = new Queries();
        String id = DBObj.checkLoginCookie(request, response);

        if (id == null) { // If no cookie, send back to login page
        	response.sendRedirect("/Fabflix");
        	return;
        }
        
        Connection dbcon = DBObj.createDatabaseConnection();
    	Statement statement = DBObj.createQueryStatement(dbcon);
		if (request.getParameter("genre") != null) {
			ArrayList<String> genres;
			try {
				genres = queries.GetGenres(dbcon, statement);
				request.setAttribute("genres", genres);
				RequestDispatcher view = request.getRequestDispatcher("browsegenre.jsp");
		        view.forward(request, response);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else if (request.getParameter("title") != null) {
			ArrayList<String> titles;
			try {
				titles = queries.GetTitles(dbcon, statement);
				Collections.sort(titles);
				String[] letters = {"Digit", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
				request.setAttribute("titles", titles);
				request.setAttribute("letters", letters);
				RequestDispatcher view = request.getRequestDispatcher("browsetitle.jsp");
		        view.forward(request, response);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}
	}

}
