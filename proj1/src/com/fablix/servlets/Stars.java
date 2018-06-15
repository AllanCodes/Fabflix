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
 * Servlet implementation class Stars
 */
@WebServlet("/Stars")
public class Stars extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Stars() {
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
        
        if (request.getParameter("stars") == null) {
        	response.sendRedirect("Search");
        	return;
        }
        
		Queries queries = new Queries();
        Connection dbcon = DBObj.createDatabaseConnection();
    	Statement statement = DBObj.createQueryStatement(dbcon);
        ArrayList<ArrayList<String>> tableInfo = new ArrayList<ArrayList<String>>();
        String[] colNames = {"", "Star", "Year of Birth", "Movies"};
       
		try {
			//ResultSet result = queries.StarQuery(dbcon, statement, request.getParameter("stars"));
			tableInfo = queries.StarQuery(dbcon, statement, request.getParameter("stars"), request);
//			boolean found = false;
//			for(int i = 0; result.next(); i++) {
//				found = true;
//				ArrayList<String> tmp = new ArrayList<String>();
//				tmp.add(String.valueOf(i+1));
//				tmp.add(result.getString("name"));
//				request.setAttribute("starName", result.getString("name"));
//				if (result.getString("birthYear") == "null" || result.getString("birthYear") == null) {
//					tmp.add("N/A");
//				} else {
//					tmp.add(result.getString("birthYear"));
//				}
//				if (result.getString("movieTitles") != null) {
//					tmp.add("");
//				} else {
//					tmp.add(result.getString("movieTitles"));
//				}
//
//				tableInfo.add(tmp);
//			}
//			result.close();
//			//if (!found) {
//				result = queries.ExplicitStarQuery(dbcon, request.getParameter("stars"));
//				for(int i = 0; result.next(); i++) {
//					ArrayList<String> tmp = new ArrayList<String>();
//					tmp.add(String.valueOf(i+1));
//					tmp.add(result.getString("name"));
//					request.setAttribute("starName", result.getString("name"));
//					if (result.getString("birthYear") == "null" || result.getString("birthYear") == null) {
//						tmp.add("N/A");
//					} else {
//						tmp.add(result.getString("birthYear"));
//					}
//					tmp.add("");
//
//					tableInfo.add(tmp);
//				}
//			//}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
		request.setAttribute("colNames", colNames);
        request.setAttribute("tableInfo", tableInfo);
		RequestDispatcher view = request.getRequestDispatcher("Star.jsp");
        view.forward(request, response);


	}
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}

}
