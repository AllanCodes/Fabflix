package com.fablix.servlets;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.database.access.Basic;
import com.database.access.Queries;
import com.google.gson.JsonObject;
//import com.mysql.jdbc.Connection;
import java.sql.Connection;
import java.sql.Statement;

/**
 * Servlet implementation class AndroidMovieList
 */
@WebServlet("/AndroidMovieList")
public class AndroidMovieList extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AndroidMovieList() {
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Basic DBObj = new Basic();
		Connection db = DBObj.createDatabaseConnection();
		Queries queries = new Queries();
		String title_query = request.getParameter("title");
		String offset = request.getParameter("offset");
		if (offset == null)
			offset = "";
		else
			offset = "offset " + offset;
		System.out.println(title_query);
		System.out.println(offset);
		Statement statement = DBObj.createQueryStatement(db);
		try {
			ResultSet results = queries.FullTextSearchQuery(db, statement, title_query, false, "limit 10 " + offset);
			JsonObject responseJson = new JsonObject();
			int i = 0;
			while (results != null && results.next()) {
				JsonObject singleJson = new JsonObject();
				singleJson.addProperty("title", results.getString("title"));
				singleJson.addProperty("year", results.getString("year"));
				singleJson.addProperty("director", results.getString("director"));
				singleJson.addProperty("genres", results.getString("listGenres"));
				singleJson.addProperty("stars", results.getString("listStars"));
				responseJson.add(results.getString("title") + String.valueOf(i), singleJson);
				i++;
			}
			response.getWriter().write(responseJson.toString());
		} catch (SQLException e) {
			response.sendError(500, e.getMessage());
		}
	}

}
