package com.database.access;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.net.ssl.HttpsURLConnection;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
//import com.mysql.jdbc.Connection;
//import com.mysql.jdbc.Statement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.jasypt.util.password.StrongPasswordEncryptor;

public class Basic {

	private final String loginUser = "root";
	private final String loginPassword = "admin";
	private final String ec2_DB_loginUrl = "jdbc:mysql://localhost/moviedb";
	
	public Basic() {
		
	}
	/**
	 * 
	 * Create a connection to the Database and return its object
	 * @return
	 */
	public Connection createDatabaseConnection() throws IOException {
        try {
//            Class.forName("com.mysql.jdbc.Driver").newInstance();
//            Connection dbcon = (Connection) DriverManager.getConnection(ec2_DB_loginUrl, loginUser, loginPassword);
        	System.out.println("Regular connection created");
            Context initCtx = new InitialContext();

            Context envCtx = (Context) initCtx.lookup("java:comp/env");
            if (envCtx == null)
                return null;

            DataSource ds = (DataSource) envCtx.lookup("balanced/moviedb");
            if (ds == null)
            	return null;
            
            Connection dbcon = ds.getConnection();
            return dbcon;
        } catch (java.lang.Exception ex) {
                System.out.println("Exception:  " + ex.getMessage());
                return null;
        } // end catch SQLException
        
	}
	public Connection createMasterConnection() throws IOException {
        try {
//            Class.forName("com.mysql.jdbc.Driver").newInstance();
//            Connection dbcon = (Connection) DriverManager.getConnection(ec2_DB_loginUrl, loginUser, loginPassword);
        	System.out.println("Master created");
            Context initCtx = new InitialContext();

            Context envCtx = (Context) initCtx.lookup("java:comp/env");
            if (envCtx == null)
                return null;

            DataSource ds = (DataSource) envCtx.lookup("master/moviedb");
            if (ds == null) {
            	System.out.println("ERORR IN POOL");
            	return null;
            }
            
            Connection dbcon = ds.getConnection();
            return dbcon;
        } catch (java.lang.Exception ex) {
                System.out.println("Exception:  " + ex.getMessage());
                return null;
        } // end catch SQLException
        
	}
	
	public Connection createConnectionAWS() throws IOException {
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
            Connection dbcon = (Connection) DriverManager.getConnection("jdbc:mysql://ec2-54-219-171-102.us-west-1.compute.amazonaws.com:3306/moviedb", loginUser, loginPassword);
            return dbcon;
		} catch (java.lang.Exception ex) {
            System.out.println("Exception:  " + ex.getMessage());
            return null;
		} // end catch SQLException
	}
	/**
	 * Create a query statement to the current database connection.
	 * @return
	 * @throws IOException
	 */
	public Statement createQueryStatement(Connection dbcon) throws IOException {
	
		try {
			Statement statement = (Statement) dbcon.createStatement();
			return statement;
			
		} catch (java.lang.Exception ex) {
			System.out.println("Exception:  " + ex.getMessage());
			return null;
		}
	}
	
	
	public void closeAllConnections(ResultSet result, Connection connection, Statement statement) {
		
		try {
			result.close();
			connection.close();
			statement.close();
			
		} catch (SQLException ex) {
			 while (ex != null) {
	                System.out.println("SQL Exception:  " + ex.getMessage());
	                ex = ex.getNextException();
	            }
		}
	}
	
	/**
	 * Checks username and password against customer table
	 * @param user
	 * @param pass
	 * @return Customer ID
	 */
	public String performLogin(String user, String pass) {
		try {
			Queries q = new Queries();
			String query = "SELECT * FROM customers where email=?";
			Connection db = this.createDatabaseConnection();
			PreparedStatement prep = db.prepareStatement(query);
			prep.setString(1, user);
			ResultSet result = prep.executeQuery();
			if (result.next()) {
				String encrypted_password = result.getString("password");
				boolean passed = new StrongPasswordEncryptor().checkPassword(pass, encrypted_password);
				if (passed)
					return String.valueOf(result.getInt("id"));
				return null;
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
		
	}
	
	public JsonObject verifyAndroidLogin(String user, String pass) {
		try {
			Queries q = new Queries();
			String query = "SELECT * FROM customers where email=?";
			Connection db = this.createDatabaseConnection();
			PreparedStatement prep = db.prepareStatement(query);
			prep.setString(1, user);
			ResultSet result = prep.executeQuery();
			if (result.next()) {
				String encrypted_password = result.getString("password");
				boolean passed = new StrongPasswordEncryptor().checkPassword(pass, encrypted_password);
				if (passed) {
					JsonObject responseJsonObject = new JsonObject();
		            responseJsonObject.addProperty("status", "success");
		            responseJsonObject.addProperty("message", "success");
		            
		            return responseJsonObject;
				} else {
					JsonObject responseJsonObject = new JsonObject();
		            responseJsonObject.addProperty("status", "fail");
		            responseJsonObject.addProperty("message", "Incorrect username or password");
		            return responseJsonObject;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		JsonObject responseJsonObject = new JsonObject();
        responseJsonObject.addProperty("status", "fail");
        responseJsonObject.addProperty("message", "Incorrect username or password");
        return responseJsonObject;
		
	}
	
    public static void verify(String gRecaptchaResponse, String secretKey) throws Exception {
    	final String SITE_VERIFY_URL = "https://www.google.com/recaptcha/api/siteverify";
        if (gRecaptchaResponse == null || gRecaptchaResponse.length() == 0) {
        		throw new Exception("recaptcha verification failed: g-recaptcha-response is null or empty");
        }
        
        URL verifyUrl = new URL(SITE_VERIFY_URL);
        
        // Open Connection to URL
        HttpsURLConnection conn = (HttpsURLConnection) verifyUrl.openConnection();


        // Add Request Header
        conn.setRequestMethod("POST");
        conn.setRequestProperty("User-Agent", "Mozilla/5.0");
        conn.setRequestProperty("Accept-Language", "en-US,en;q=0.5");


        // Data will be sent to the server.
        String postParams = "secret=" + secretKey + "&response=" + gRecaptchaResponse;

        // Send Request
        conn.setDoOutput(true);
        
        // Get the output stream of Connection
        // Write data in this stream, which means to send data to Server.
        OutputStream outStream = conn.getOutputStream();
        outStream.write(postParams.getBytes());

        outStream.flush();
        outStream.close();

        // Response code return from server.
        int responseCode = conn.getResponseCode();
        System.out.println("responseCode=" + responseCode);


        // Get the InputStream from Connection to read data sent from the server.
        InputStream inputStream = conn.getInputStream();
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        
        JsonObject jsonObject = new Gson().fromJson(inputStreamReader, JsonObject.class);
        
        inputStreamReader.close();
        
        System.out.println("Response: " + jsonObject.toString());
        
        if (jsonObject.get("success").getAsBoolean()) {
        		// verification succeed
        		return;
        }
        
        throw new Exception("recaptcha verification failed: response is " + jsonObject.toString());
// 
}
	public String performSuperLogin(String user, String pass) {
		try {
			Queries q = new Queries();
			String query = "SELECT * FROM employees where email=?";
			Connection db = this.createDatabaseConnection();
			PreparedStatement prep = db.prepareStatement(query);
			prep.setString(1, user);
			ResultSet result = prep.executeQuery();
			if (result.next()) {
				String encrypted_password = result.getString("password");
				boolean passed = new StrongPasswordEncryptor().checkPassword(pass, encrypted_password);
				if (passed)
					return result.getString("email");
				return null;
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
		
	}
	/**
	 * Check existence of Cookie
	 * @param request
	 * @param response
	 * @return id of user who has Cookie, or null if no cookie exists
	 * @throws IOException
	 */
	public String checkLoginCookie(HttpServletRequest request, HttpServletResponse response) throws IOException {
		Cookie[] cookies = request.getCookies();
        if (cookies == null) {
        	response.sendRedirect("/Fabflix");
        } else {
	        for (Cookie c : cookies) {
	        	if (c.getName().equals("user")) {
	        		return c.getValue(); 
	        	}
        	}
        }
        return null;
	}
	
	public String checkSuperLoginCookie(HttpServletRequest request, HttpServletResponse response) throws IOException {
		Cookie[] cookies = request.getCookies();
        if (cookies == null) {
        	//response.sendRedirect("/Fabflix/_dashboard");
        } else {
	        for (Cookie c : cookies) {
	        	if (c.getName().equals("superUser")) {
	        		return c.getValue(); 
	        	}
        	}
        }
        return null;
	}
	
	public void logout(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {
		Cookie[] cookies = request.getCookies();
        if (cookies == null) {
        	response.sendRedirect("/Fabflix");
        } else {
        	Cookie lc = null;
	        for (Cookie c : cookies) {
	        	if (c.getName().equals("user")) {
	        		c.setMaxAge(0);
	        		response.addCookie(c);
	        		break;
	        	}
        	}
	        Queries queries = new Queries();
	        queries.deleteCart();
	        response.sendRedirect("/Fabflix");
        }
	}
	
	public void logoutSuper(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {
		Cookie[] cookies = request.getCookies();
        if (cookies == null) {
        	response.sendRedirect("/Fabflix");
        } else {
        	Cookie lc = null;
	        for (Cookie c : cookies) {
	        	if (c.getName().equals("superUser")) {
	        		c.setMaxAge(0);
	        		response.addCookie(c);
	        		break;
	        	}
        	}
	        //response.sendRedirect("/Fabflix/_dashboard");
        }
	}
}
