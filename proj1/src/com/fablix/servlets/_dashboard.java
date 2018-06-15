package com.fablix.servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.database.access.Basic;
import com.database.access.Queries;
//import com.mysql.jdbc.Connection;
import java.sql.Connection;

/**
 * Servlet implementation class _dashboard
 */
@WebServlet("/_dashboard")
public class _dashboard extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public _dashboard() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		Basic DBObj = new Basic();
//        Connection dbcon = DBObj.createDatabaseConnection();
//		String id = DBObj.checkSuperLoginCookie(request, response);
//        if (id == null) {
//        	RequestDispatcher view = request.getRequestDispatcher("dashboardlogin.jsp");
//	        view.forward(request, response);
//        	return;
//        } else {
//        	if (request.getParameter("logout") != null && request.getParameter("logout").equals("true")) {
//            	try {
//    				DBObj.logoutSuper(request, response);
//    				return;
//    			} catch (SQLException e) {
//    				e.printStackTrace();
//    			}
//            }
//        	
//        	Queries queries = new Queries();
//        	
//        	try {
//				ArrayList<ArrayList<String>> tables = queries.getAllTables(dbcon);
//				request.setAttribute("tables", tables);
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}
//        	
//        	if (request.getParameter("Addstars") != null && !request.getParameter("Addstars").isEmpty()) {
//        		try {
//					String chk = queries.addNewStar(dbcon, request.getParameter("Addstars"), request.getParameter("Addstarsyear"));
//					if (chk != null) request.setAttribute("errormsg", "  Star added");
//				} catch (SQLException e) {
//					e.printStackTrace();
//				}
//        	} else if (request.getParameter("Addstarsyear") != null && !request.getParameter("Addstarsyear").isEmpty()) {
//        		request.setAttribute("errormsg", "Please input a star name");
//        	} else {
//            	try {
//            		if (request.getParameter("Addmoviename") != null && !request.getParameter("Addmoviename").isEmpty() &&
//            			request.getParameter("Addmoviedirector") != null && !request.getParameter("Addmoviedirector").isEmpty() &&
//            			request.getParameter("Addmovieyear") != null && !request.getParameter("Addmovieyear").isEmpty() &&
//            			request.getParameter("Addmoviestar") != null && !request.getParameter("Addmoviestar").isEmpty() &&
//            			request.getParameter("Addmoviegenre") != null && !request.getParameter("Addmoviegenre").isEmpty()) 
//            		{
//            			boolean chk = queries.addMovie(dbcon, request.getParameter("Addmoviename"), request.getParameter("Addmoviedirector"), request.getParameter("Addmovieyear"), request.getParameter("Addmoviestaryear"), request.getParameter("Addmoviestar"), request.getParameter("Addmoviegenre"));
//            			if (!chk) request.setAttribute("errormsg", "Movie already exists");
//            			else request.setAttribute("errormsg", "Succesfully added movie");
//            		} else if (request.getParameter("Addmoviename") == "" ||
//            			request.getParameter("Addmoviedirector") == "" ||
//            			request.getParameter("Addmovieyear") ==  "" ||
//            			request.getParameter("Addmoviestar") == "" ||
//            			request.getParameter("Addmoviegenre") == "") 
//            		{
//            			request.setAttribute("errormsg", "Required fields are: Title, Director, Year, Star, and Genre");
//            		}
//    			} catch (SQLException e) {
//    				e.printStackTrace();
//    			}
//        	}
		Basic DBObj = new Basic();
		Queries queries = new Queries();
        String id = DBObj.checkSuperLoginCookie(request, response);
        if (id == null) { // If no cookie, send back to login page
        	RequestDispatcher view = request.getRequestDispatcher("dashboardlogin.jsp");
    	    view.forward(request, response);
        	return;
        }
        doPost(request, response);
//        RequestDispatcher view = request.getRequestDispatcher("dashboard.jsp");
//	    view.forward(request, response);
//        }

        
        
        
        
        
        
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		boolean logged = false;
		if (request.getParameter("loginPost") != null) {
			Basic DBObj = new Basic();
			String id = DBObj.performSuperLogin(request.getParameter("email"), request.getParameter("password"));
			if ( id != null ) {
				Cookie loginCookie = new Cookie("superUser", id);
				loginCookie.setMaxAge(60*60 * 24);
				response.addCookie(loginCookie);
				logged = true;
//				response.sendRedirect("/Fabflix/_dashboard");
			} else {
				RequestDispatcher view = request.getRequestDispatcher("dashboarderror.jsp");
		        view.forward(request, response);
			}
		} 
		
			Basic DBObj = new Basic();
	        Connection dbcon = DBObj.createMasterConnection();
			String id = DBObj.checkSuperLoginCookie(request, response);
	        if (id == null && !logged) {
	        	RequestDispatcher view = request.getRequestDispatcher("dashboardlogin.jsp");
		        view.forward(request, response);
	        	return;
	        } else {
	        	if (request.getParameter("logout") != null && request.getParameter("logout").equals("true")) {
	            	try {
	    				DBObj.logoutSuper(request, response);
	    				response.sendRedirect("/Fabflix");
	    				return;
	    			} catch (SQLException e) {
	    				e.printStackTrace();
	    			}
	            }
	        	
	        	Queries queries = new Queries();
	        	
	        	try {
					ArrayList<ArrayList<String>> tables = queries.getAllTables(dbcon);
					request.setAttribute("tables", tables);
				} catch (SQLException e) {
					e.printStackTrace();
				}
	        	
	        	if (request.getParameter("Addstars") != null && !request.getParameter("Addstars").isEmpty()) {
	        		try {
						String chk = queries.addNewStar(dbcon, request.getParameter("Addstars"), request.getParameter("Addstarsyear"));
						if (chk != null) request.setAttribute("errormsg", "  Star added");
						else request.setAttribute("errormsg", "  Cannot add Star");
					} catch (SQLException e) {
						e.printStackTrace();
					}
	        	} else if (request.getParameter("Addstarsyear") != null && !request.getParameter("Addstarsyear").isEmpty()) {
	        		request.setAttribute("errormsg", "Please input a star name");
	        	} else {
	            	try {
	            		if (request.getParameter("Addmoviename") != null && !request.getParameter("Addmoviename").isEmpty() &&
	            			request.getParameter("Addmoviedirector") != null && !request.getParameter("Addmoviedirector").isEmpty() &&
	            			request.getParameter("Addmovieyear") != null && !request.getParameter("Addmovieyear").isEmpty() &&
	            			request.getParameter("Addmoviestar") != null && !request.getParameter("Addmoviestar").isEmpty() &&
	            			request.getParameter("Addmoviegenre") != null && !request.getParameter("Addmoviegenre").isEmpty()) 
	            		{
	            			boolean chk = queries.addMovie(dbcon, request.getParameter("Addmoviename"), request.getParameter("Addmoviedirector"), request.getParameter("Addmovieyear"), request.getParameter("Addmoviestaryear"), request.getParameter("Addmoviestar"), request.getParameter("Addmoviegenre"));
	            			if (!chk) request.setAttribute("errormsg", "Movie already exists");
	            			else request.setAttribute("errormsg", "Succesfully added movie");
	            		} else if (request.getParameter("Addmoviename") == "" ||
	            			request.getParameter("Addmoviedirector") == "" ||
	            			request.getParameter("Addmovieyear") ==  "" ||
	            			request.getParameter("Addmoviestar") == "" ||
	            			request.getParameter("Addmoviegenre") == "") 
	            		{
	            			request.setAttribute("errormsg", "Required fields are: Title, Director, Year, Star, and Genre");
	            		}
	    			} catch (SQLException e) {
	    				e.printStackTrace();
	    			}
	        	}
	        	RequestDispatcher view = request.getRequestDispatcher("dashboard.jsp");
		        view.forward(request, response);
	        }
		}
		

}
