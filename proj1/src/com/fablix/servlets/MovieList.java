package com.fablix.servlets;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
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
 * Servlet implementation class MovieList
 */
@WebServlet("/MovieList")
public class MovieList extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MovieList() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	   

		long startTime = System.nanoTime();
		long endTime_JDBC;
		long elapsedTime_JDBC = 0;
		Basic DBObj = new Basic();
		Queries queries = new Queries();
        String id = DBObj.checkLoginCookie(request, response);
        String pages;
        int start = 0;
        int end = 50;
        String searchQuery = "";
        
//        if (id == null) { // If no cookie, send back to login page
//        	response.sendRedirect("/Fabflix");
//        	return;
//        }
		long startTime_JDBC = System.nanoTime();
        Connection dbcon = DBObj.createDatabaseConnection();
    	Statement statement = DBObj.createQueryStatement(dbcon);
        ArrayList<ArrayList<String>> tableInfo = new ArrayList<ArrayList<String>>();
        String[] colNames = {"", "Id", "Title", "Director", "Year", "Genre(s)", "Star(s)", "Rating", "Buy"};

        if (request.getParameter("genre") != null) {
        	int counter = 1;
        		try {
					ResultSet result = queries.GetMoviesOfGenre(dbcon, statement, request.getParameter("genre"));
					while (result.next()) {
						ArrayList<String> tmp = new ArrayList<String>();
						tmp.add(String.valueOf(counter));
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
						counter += 1;
						tableInfo.add(tmp);
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
        } else {
			try {
				ResultSet result = queries.FullTextSearchQuery(dbcon, statement, request.getParameter("title"), false, "");
				endTime_JDBC = System.nanoTime();
				elapsedTime_JDBC = endTime_JDBC - startTime_JDBC;
				if (result != null)
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
        }
        
        try {
			dbcon.close();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
        if (request.getParameter("currPage") == null) {
        	request.setAttribute("currPage", 1);
        } else {
        	request.setAttribute("currPage", request.getParameter("currPage"));
        }
        
        if (request.getParameter("pageCount") != null) {
        	int count = Integer.parseInt(request.getParameter("pageCount"));
        	int curr = Integer.parseInt(request.getParameter("currPage"));
        	switch (count) {
        		case 50:
        		case 100:
        		case 500:
        			if (tableInfo.size() < curr * count) {
        				if (tableInfo.size() > count)
        					request.setAttribute("tableInfo", tableInfo.subList(0, count));
        				else
        					request.setAttribute("tableInfo", tableInfo.subList(0, tableInfo.size()));
        				request.setAttribute("currPage", 1);
        			}
        			else {
        				request.setAttribute("tableInfo", tableInfo.subList((curr - 1) * count, curr * count));
        			}
        			break;
        		default:
        			request.setAttribute("tableInfo", tableInfo.subList(0, tableInfo.size()));
        			break;
        	}
        } else {
        	request.setAttribute("pageCount", 50);
        	if (tableInfo.size() > 50) {
            	request.setAttribute("tableInfo", tableInfo.subList(start, 50));
        	} else {
        		request.setAttribute("tableInfo", tableInfo.subList(start, tableInfo.size()));
        	}
        }
        if (request.getParameter("title") != "" && request.getParameter("title") != null) searchQuery += "title - '" + request.getParameter("title") + "' ";
        if (request.getParameter("director") != "" && request.getParameter("director") != null) searchQuery += "director - '" + request.getParameter("director") + "' ";
        if (request.getParameter("stars") != "" && request.getParameter("stars") != null) searchQuery += "star - '" + request.getParameter("stars") + "' ";
        if (request.getParameter("year") != "" && request.getParameter("year") != null) searchQuery += "year - '" + request.getParameter("year") + "' ";
        if (request.getParameter("genre") != "" && request.getParameter("genre") != null) searchQuery += "genre - '" + request.getParameter("genre") + "' ";

        request.setAttribute("searchQuery", searchQuery);
		request.setAttribute("colNames", colNames);
        //request.setAttribute("tableInfo", tableInfo);
		RequestDispatcher view = request.getRequestDispatcher("searchFilms.jsp");
		long endTime = System.nanoTime();
		long elapsedTime = endTime - startTime;
		String contextPath = getServletContext().getRealPath("/");

 	    String xmlFilePath=contextPath+"Search_times";

 	    System.out.println(xmlFilePath);
 	    String time = String.valueOf(elapsedTime_JDBC) + " " + String.valueOf(elapsedTime) + "\n"; 
 	    
		try {
	        Files.write(Paths.get(xmlFilePath), time.getBytes(), StandardOpenOption.APPEND);
	    }catch (IOException e) {
	 	    File myfile = new File(xmlFilePath);
	 	    myfile.createNewFile();
	 	   Files.write(Paths.get(xmlFilePath), time.getBytes(), StandardOpenOption.APPEND);
	    }
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
