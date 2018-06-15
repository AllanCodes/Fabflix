package com.fablix.servlets;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

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
 * Servlet implementation class Movie
 */
@WebServlet("/Movie")
public class Movie extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Movie() {
        super();

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
        
        if (request.getParameter("title") == null) {
        	response.sendRedirect("Search");
        	return;
        }
		Queries queries = new Queries();
        
        Connection dbcon = DBObj.createDatabaseConnection();
    	Statement statement = DBObj.createQueryStatement(dbcon);
        ArrayList<ArrayList<String>> tableInfo = new ArrayList<ArrayList<String>>();
        String[] colNames = {"", "Id", "Title", "Director", "Year", "Genre(s)", "Star(s)", "Rating", "Buy"};
       
		try {
			ResultSet result = queries.MovieQuery(dbcon, statement, request.getParameter("title"));
			request.setAttribute("movieName", request.getParameter("title"));
			for(int i = 0; result.next(); i++) {
				ArrayList<String> tmp = new ArrayList<String>();
				tmp.add(String.valueOf(i+1));
				tmp.add(result.getString("id"));
				tmp.add(result.getString("title"));
				tmp.add(result.getString("director"));
				tmp.add(result.getString("year"));
				tmp.add(result.getString("listGenres"));
				tmp.add(result.getString("listStars"));
				if (result.getString("rating") == "null" || result.getString("rating") == null) {
					tmp.add("N/A");
				} else {
					tmp.add(result.getString("rating"));
				}
				tmp.add("Add");
				tableInfo.add(tmp);
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
		request.setAttribute("colNames", colNames);
        request.setAttribute("tableInfo", tableInfo);
		RequestDispatcher view = request.getRequestDispatcher("Movie.jsp");
        view.forward(request, response);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		Basic DBObj = new Basic();
//		Queries queries = new Queries();
//        String id = DBObj.checkLoginCookie(request, response);
//
//        if (id == null) { // If no cookie, send back to login page
//        	response.sendRedirect("/Fabflix");
//        	return;
//        }
//        
//        Connection dbcon = DBObj.createDatabaseConnection();
//    	Statement statement = DBObj.createQueryStatement(dbcon);
//        ArrayList<ArrayList<String>> tableInfo = new ArrayList<ArrayList<String>>();
//        String[] colNames = {"", "Id", "Title", "Director", "Year", "Genre(s)", "Star(s)", "Rating"};
//       
//		try {
//			ResultSet result = queries.MovieQuery(statement, request.getParameter("title"));
//			for(int i = 0; result.next(); i++) {
//				ArrayList<String> tmp = new ArrayList<String>();
//				tmp.add(String.valueOf(i+1));
//				tmp.add(result.getString("id"));
//				tmp.add(result.getString("title"));
//				tmp.add(result.getString("director"));
//				tmp.add(result.getString("year"));
//				tmp.add(result.getString("listGenres"));
//				tmp.add(result.getString("listStars"));
//				if (result.getString("rating") == "null" || result.getString("rating") == null) {
//					tmp.add("N/A");
//				} else {
//					tmp.add(result.getString("rating"));
//				}
//				
//				tableInfo.add(tmp);
//			}
//		} catch (SQLException e1) {
//			e1.printStackTrace();
//		}
//		
//		request.setAttribute("colNames", colNames);
//        request.setAttribute("tableInfo", tableInfo);
//		RequestDispatcher view = request.getRequestDispatcher("Movie.jsp");
//        view.forward(request, response);

	}

}
