package com.fablix.servlets;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

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
 * Servlet implementation class Checkout
 */
@WebServlet("/Checkout")
public class Checkout extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Checkout() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Basic DBObj = new Basic();
        String id = DBObj.checkLoginCookie(request, response);
        if (id == null) { // If no cookie, send back to login page
        	response.sendRedirect("/Fabflix");
        	return;
        }
        
        Connection dbcon = DBObj.createMasterConnection();
    	try {
    		String query = "select * from cart where id=?";
    		PreparedStatement prep = dbcon.prepareStatement(query);
    		prep.setString(1, id);
			ResultSet result = prep.executeQuery();
			if (result.next()) {
				RequestDispatcher view = request.getRequestDispatcher("checkout.jsp");
		        view.forward(request, response);
			} else {
				response.sendRedirect("Cart");
				return;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	
    	
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
        
        Connection dbcon = DBObj.createMasterConnection();
    	Statement statement = DBObj.createQueryStatement(dbcon);
    	try {
			boolean chk = queries.validateCustomerInformation(dbcon, statement, request.getParameter("first"), request.getParameter("last"), request.getParameter("creditcard"), request.getParameter("expdate"));
			if (chk) {
				DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
				Date date = new Date();
				ArrayList<ArrayList<String>> sales = new ArrayList<ArrayList<String>>();
				ResultSet result = queries.getCart(dbcon, statement, id, request, response);
				ArrayList<ArrayList<String>> checkoutRecord = new ArrayList<ArrayList<String>>();
				while (result.next()) {
					ArrayList<String> tmp = new ArrayList<String>();
					ArrayList<String> tmp2 = new ArrayList<String>();
					tmp.add(id);
					tmp.add(result.getString("title"));
					tmp2.add(result.getString("title"));
					tmp2.add(String.valueOf(result.getInt("quantity")));
					tmp.add(dateFormat.format(date));
					sales.add(tmp);
					checkoutRecord.add(tmp2);
				}
				result.close();
				for (ArrayList<String> li : sales) {
					String movieID = queries.getMovieId(dbcon, statement, li.get(1));
					li.add(1, movieID);
				}
				queries.addSale(dbcon, statement, id, sales);
				for (ArrayList<String> li : checkoutRecord) {
					ResultSet result2 = queries.getSalesID(dbcon, statement, id, li.get(0));
					if (result2.next()) {
						li.add(result2.getString("id"));
					}
				}

				request.setAttribute("tableInfo", checkoutRecord);
				RequestDispatcher view = request.getRequestDispatcher("checkoutsuccess.jsp");
		        view.forward(request, response);
				
			} else {
				request.setAttribute("errorPage", "Incorrect Login Credentials");
				RequestDispatcher view = request.getRequestDispatcher("checkout.jsp");
		        view.forward(request, response);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	
	}

}
