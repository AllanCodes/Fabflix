package com.fablix.servlets;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.database.access.Basic;
import com.database.access.Queries;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
//import com.mysql.jdbc.Connection;
import java.sql.Connection;

/**
 * Servlet implementation class SearchJs
 */
@WebServlet(urlPatterns= {"/Search"})
public class SearchJs extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	
    public SearchJs() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Basic DBObj = new Basic();
		String idd = DBObj.checkLoginCookie(request, response);
        if (idd == null) {
        	response.sendRedirect("/Fabflix");
        	return;
        }
        
        try {
        	Queries queries = new Queries();
        	Connection db = DBObj.createDatabaseConnection();
			JsonArray jsonArray = new JsonArray();
			
			String query = request.getParameter("query");
			
			if (query == null || query.trim().isEmpty()) {
				response.getWriter().write(jsonArray.toString());
				return;
			}	
			Statement statement = db.createStatement();
			//perform full text query
			ResultSet results = queries.FullTextSearchQuery(db, statement, query, true, "");
			while (results.next()) {
				jsonArray.add(generateJsonObject(0, results.getString("title"), "Movie"));
			}
					
			response.getWriter().write(jsonArray.toString());
			return;
		} catch (Exception e) {
			response.sendError(500, e.getMessage());
}
	}

	private static JsonObject generateJsonObject(Integer heroID, String heroName, String categoryName) {
		JsonObject jsonObject = new JsonObject();
		jsonObject.addProperty("value", heroName);
		
		JsonObject additionalDataJsonObject = new JsonObject();
		additionalDataJsonObject.addProperty("category", categoryName);
		jsonObject.add("data", additionalDataJsonObject);
		return jsonObject;
	}
}
