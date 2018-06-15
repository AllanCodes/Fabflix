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
 * Servlet implementation class Cart
 */
@WebServlet("/Cart")
public class Cart extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Cart() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Basic DBObj = new Basic();
		Queries queries = new Queries();
        String id = DBObj.checkLoginCookie(request, response);
        
        if (id == null) { // If no cookie, send back to login page
        	response.sendRedirect("/Fabflix");
        	return;
        }
        
        Connection dbcon = DBObj.createMasterConnection();
    	Statement statement = DBObj.createQueryStatement(dbcon);
    	ResultSet result;
        ArrayList<ArrayList<String>> tableInfo = new ArrayList<ArrayList<String>>();
        String[] columns = {"", "Title", "Quantity", "Reduce", "Increase"};
        
        if (request.getParameter("changeQuantity") == null && request.getParameter("title") == null && request.getParameter("addMovie") == null) {
        	try {
					result = queries.getCart(dbcon, statement, id, request, response);
					for (int i = 0; result.next(); i++) {
						ArrayList<String> tmp = new ArrayList<String>();
						tmp.add(String.valueOf(i+1));
						tmp.add(result.getString("title"));
						tmp.add(String.valueOf(result.getInt("quantity")));
						tmp.add("Reduce");
						tmp.add("Increase");
						tableInfo.add(tmp);
					}
			} catch (SQLException e) {
				e.printStackTrace();
			}
        }
        else if (request.getParameter("changeQuantity") != null && request.getParameter("title") != null) {
        	String quantity_change = request.getParameter("changeQuantity");
        		try {
					queries.changeQuantity(dbcon, statement, quantity_change, id, request.getParameter("title"));
				} catch (SQLException e) {
					e.printStackTrace();
				}
        	try {
					result = queries.getCart(dbcon, statement, id, request, response);
					for (int i = 0; result.next(); i++) {
						ArrayList<String> tmp = new ArrayList<String>();
						tmp.add(String.valueOf(i+1));
						tmp.add(result.getString("title"));
						tmp.add(String.valueOf(result.getInt("quantity")));
						tmp.add("Reduce");
						tmp.add("Increase");
						tableInfo.add(tmp);
					}
			} catch (SQLException e) {
				e.printStackTrace();
			}
        }
        else if (request.getParameter("addMovie") != null) {
    		try {
				boolean chk = queries.addMovieToCart(dbcon, statement, request.getParameter("addMovie"), request, response);
				if (chk) {
					result = queries.getCart(dbcon, statement, id, request, response);
					for (int i = 0; result.next(); i++) {
						ArrayList<String> tmp = new ArrayList<String>();
						tmp.add(String.valueOf(i+1));
						tmp.add(result.getString("title"));
						tmp.add(String.valueOf(result.getInt("quantity")));
						tmp.add("Reduce");
						tmp.add("Increase");
						tableInfo.add(tmp);
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
    	}
    	request.setAttribute("tableInfo", tableInfo);
    	request.setAttribute("colNames", columns);
    	RequestDispatcher view = request.getRequestDispatcher("cart.jsp");
        view.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
