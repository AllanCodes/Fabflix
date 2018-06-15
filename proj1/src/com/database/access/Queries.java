package com.database.access;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.plaf.synth.SynthScrollBarUI;

import com.google.gson.JsonObject;
//import com.mysql.jdbc.Connection;
//import com.mysql.jdbc.Statement;
import java.sql.Connection;
import java.sql.Statement;


public class Queries {

	public Queries() {
		
	}
	
	public String convertToQuery(String word) {
		
		if (word == null || word.trim().length() == 0) {
			return "";
		}
		return word;
		
	}
	
	/**
	 * For the star page, grab the movies and birthyear of a star
	 * @param statement
	 * @param star
	 * @return
	 * @throws SQLException
	 */
	public ArrayList<ArrayList<String>> StarQuery(Connection dbcon, Statement statement, String star, HttpServletRequest request) throws SQLException {
		String query_for_movies_stars = "select s.name, s.birthYear, group_concat(m.title separator ', ') as movieTitles from movies m " +
		"join stars s on s.name=? and s.id=? join stars_in_movies sim on s.id=sim.starId  AND sim.movieId=m.id group by s.birthYear";
		
		String query_for_stars = "select * from stars where name=?";
		PreparedStatement prep = dbcon.prepareStatement(query_for_stars);
		prep.setString(1, star);
		ResultSet results = prep.executeQuery();
		ArrayList<ArrayList<String>> stars = new ArrayList<ArrayList<String>>();
		while (results.next()) {
			ArrayList<String> tmp = new ArrayList<String>();
			tmp.add(results.getString("id"));
			tmp.add(results.getString("name"));
			tmp.add(results.getString("birthYear"));
			System.out.println(results.getString("birthYear"));
			stars.add(tmp);
		}
		int i = 1;
		for (ArrayList<String> lst : stars) {
			request.setAttribute("starName", lst.get(1));
			prep = dbcon.prepareStatement("select s.name, s.birthYear, group_concat(m.title separator ', ') as movieTitles from movies m join stars s on s.name=? and s.birthYear=? and s.id=? join stars_in_movies sim on s.id=sim.starId  AND sim.movieId=m.id group by s.birthYear");
			PreparedStatement prep2 = dbcon.prepareStatement(query_for_movies_stars);
			if (lst.get(2) != null) {
				boolean chk = false;
				prep.setString(1, lst.get(1));
				prep.setString(2, lst.get(2));
				prep.setString(3, lst.get(0));
				results = prep.executeQuery();
				while(results.next()) {
					chk = true;
					lst.add(results.getString("movieTitles"));
				}	
				if (!chk) {
					lst.add("");
				}
			} else {
				boolean chk = false;
				prep2.setString(1, lst.get(1));
				prep2.setString(2, lst.get(0));
				results = prep2.executeQuery();
				while(results.next()) {
					chk = true;
					if (results.getString("movieTitles") != null)
						lst.add(results.getString("movieTitles"));
				}
				if (!chk) {
					lst.add("");
				}
			}
			lst.set(0, String.valueOf(i));
			i++;
		}
		return stars;

	}
	
	public ResultSet ExplicitStarQuery(Connection dbcon, String star) throws SQLException {
		String query;
		query = "select * from stars where name=?";
		PreparedStatement prep = dbcon.prepareStatement(query);
		prep.setString(1, star);
		ResultSet rs = prep.executeQuery();
		return rs;
	}
	public ResultSet MovieQuery(Connection db, Statement statement, String title) throws SQLException {
		
		String query;
		if (title.equals("Digit")) {
			query = "SELECT * from movieList2 where title REGEXP '^[0-9]'";
			PreparedStatement prep = db.prepareStatement(query);
			ResultSet rs = prep.executeQuery();
			return rs;
		} else {
			query = "SELECT * from movieList2 where title=?";
			PreparedStatement prep = db.prepareStatement(query);
			prep.setString(1, title);
			ResultSet rs = prep.executeQuery();
			return rs;
		}
	}	
	
	public ResultSet FullTextSearchQuery(Connection db, Statement statement, String title, boolean offset, String android) throws SQLException {
		String ft_query;
		if (offset) {
			ft_query = "select * from movieList2 where match(title) against %s limit 10";
		} else {
			ft_query = "select * from movieList2 where match(title) against %s " + android;
		}

		PreparedStatement prep;
		ResultSet results;
		
		if (title.isEmpty()) {
			return null;
		} else {
			String entries[] = title.split(" ");
			String query_title = "('";
			for (int i = 0; i < entries.length; i++) {
				if (entries[i].length() <= 3) {
					query_title += "'?'" + "*";
				} else {
					query_title += "+" + "'?'" + "*";
				}
				if (i + 1 != entries.length)	
					query_title += " ";
			}

			query_title += "' in boolean mode)";
			prep = db.prepareStatement(String.format(ft_query, query_title));
			System.out.println(prep.toString());
			for (int i = 0; i < entries.length; i++) {
				prep.setString(i+1, entries[i]);
			}
			System.out.println(prep.toString());
			

			try {
//				results = statement.executeQuery(String.format(ft_query, query_title));
				results = prep.executeQuery();
				return results;
			} catch (Exception e) {
				System.out.println("Full Text Search Error");
			}
		}
		return null;
	}
	
	
	public ResultSet SearchQuery(Connection db, Statement statement, String title, String year, String director, String star) throws SQLException {
		
		String q_title, q_director, q_star, q_year;
		q_title = this.convertToQuery(title);
		q_director = this.convertToQuery(director);
		q_star = this.convertToQuery(star);
		q_year = this.convertToQuery(year);
		String query;
		
		if (q_title.isEmpty() && q_director.isEmpty() && q_star.isEmpty() && q_year.isEmpty()) {
			query = "select * from movieList where title='fewfewfewweew';";
			PreparedStatement prep = db.prepareStatement(query);
			return prep.executeQuery();
		} else {
			query = "SELECT * from movieList2 where title LIKE ? AND director LIKE ? AND "
					+ "listStars LIKE ? AND year LIKE ?";
			PreparedStatement prep = db.prepareStatement(query);
			prep.setString(1, "%" + q_title + "%");
			prep.setString(2, "%" + q_director + "%");
			prep.setString(3, "%" + q_star + "%");
			prep.setString(4, "%" + q_year + "%");
			ResultSet rs = prep.executeQuery();
			return rs;
		}
	}
	
	public ArrayList<String> GetGenres(Connection db, Statement statement) throws SQLException {
		
		ArrayList<String> genres = new ArrayList<String>();
		String query = "select * from genres";
		PreparedStatement prep = db.prepareStatement(query);
		ResultSet result = prep.executeQuery();
		
		while (result.next()) {
			genres.add(result.getString("name"));
		}
		return genres;
	}
	
	public ArrayList<String> GetTitles(Connection db, Statement statement) throws SQLException {

		ArrayList<String> titles = new ArrayList<String>();
		
		try {
			PreparedStatement prep = db.prepareStatement("select * from movies");
			ResultSet result = statement.executeQuery("select * from movies;");
			
			while (result.next()) {
				titles.add(result.getString("title"));
			}
			
			return titles;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return titles;
		
	}
	
	public ResultSet GetMoviesOfGenre(Connection db, Statement statement, String genre) throws SQLException {
		PreparedStatement prep = db.prepareStatement("select * from `" + genre + "`");
		ResultSet result = prep.executeQuery();
		return result;
		
	}
	
	public boolean addMovieToCart(Connection dbcon, Statement statement, String title, HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {
		
		Basic db = new Basic();
		String id = db.checkLoginCookie(request, response);
		String query = "select * from cart where title=? and id=?";
		PreparedStatement prep = dbcon.prepareStatement(query);
		prep.setString(1, title);
		prep.setString(2, id);
		ResultSet result = prep.executeQuery();
		int chk = 0;
		
		if (result.next()) {
			query = "update cart set quantity = quantity + 1 where id=? and title=?";
			prep = dbcon.prepareStatement(query);
			prep.setString(1, id);
			prep.setString(2, title);
			chk = prep.executeUpdate();
			if (chk > 0)
				return true;
		} else {
			query = "insert into cart values(?, ?, 1)";
			prep.close();
			prep = dbcon.prepareStatement(query);
			prep.setString(1, title);
			prep.setString(2, id);
			chk = prep.executeUpdate(); 
			if (chk > 0)
				return true;
		}
		
		return false;
	}
	
	public boolean changeQuantity(Connection dbcon, Statement statement, String change, String id, String title) throws SQLException, IOException {
		
		String query;
		int chk;
		Basic db = new Basic();
		
		if (change.equals("increase")) {
			query = "update cart set quantity = quantity + 1 where id=? and title=?";
			PreparedStatement prep = dbcon.prepareStatement(query);
			prep.setString(1, id);
			prep.setString(2, title);
			chk = prep.executeUpdate();
			if (chk > 0)
					return true;
		} else if (change.equals("reduce")) {
			query = "update cart set quantity = quantity - 1 where id=? and title=?";
			PreparedStatement prep = dbcon.prepareStatement(query);
			prep.setString(1, id);
			prep.setString(2, title);
			chk = prep.executeUpdate();
			if (chk > 0) {
				prep.close();
				query = "delete from cart where title=? and id=? and quantity=0";
				prep = dbcon.prepareStatement(query);
				prep.setString(1, title);
				prep.setString(2, id);
				prep.executeUpdate();
				return true;
			}
			else return false;
		}
		
		return false;

	}
	
	public ResultSet getCart(Connection dbcon, Statement statement, String id, HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {
		Basic db = new Basic();
		String query = "select * from cart where id=?";
		PreparedStatement prep = dbcon.prepareStatement(query);
		prep.setString(1, id);
		ResultSet result = prep.executeQuery();
		
		return result;
		
	}
	
	public boolean validateCustomerInformation(Connection dbcon, Statement statement, String first, String last, String card, String expdate) throws SQLException {
		String query = "select * from creditcards where firstName=? and lastName=? and expiration=DATE(?) and id=?";
		PreparedStatement prep = dbcon.prepareStatement(query);
		prep.setString(1, first);
		prep.setString(2, last);
		prep.setString(3, expdate);
		prep.setString(4, card);
		ResultSet result = prep.executeQuery();
		if (result.next()) {
			result.close();
			return true;
		}
		return false;
	}
	
	public String getMovieId(Connection dbcon, Statement statement, String title) throws SQLException {
		String query = "select id from movies where title=?";
		PreparedStatement prep = dbcon.prepareStatement(query);
		prep.setString(1, title);
		ResultSet result = prep.executeQuery();
		if (result.next()) {
			String id = result.getString("id");
			result.close();
			return id;
		}
		result.close();
		return null;
	}
	
	public void addSale(Connection dbcon, Statement statement, String id, ArrayList<ArrayList<String>> sales) throws SQLException, IOException {
		Basic db = new Basic();
		for (ArrayList<String> x : sales) {
			String query = "insert into sales (customerId, movieId, saleDate) values(?, ?, ?)";
			PreparedStatement prep = dbcon.prepareStatement(query);
			prep.setString(1, x.get(0));
			prep.setString(2, x.get(1));
			prep.setString(3, x.get(3));
			prep.executeUpdate();
		}
		this.deleteCart();
	}
	
	public void deleteCart() throws IOException, SQLException {
		Basic DBObj = new Basic();
		Connection dbcon = DBObj.createDatabaseConnection();
		PreparedStatement prep = dbcon.prepareStatement("truncate cart");
    	prep.executeUpdate();
	}
	
	public ResultSet getSalesID(Connection dbcon, Statement statement, String id, String movieId) throws SQLException {
		String query = "Select MAX(s.id) as id from sales s, movies m where customerId=? and m.id=s.movieId and m.title=?";
		PreparedStatement prep = dbcon.prepareStatement(query);
		prep.setString(1, id);
		prep.setString(2, movieId);
		ResultSet result = prep.executeQuery();
		return result;
	}
	
	public ArrayList<ArrayList<String>> getAllTables(Connection dbcon) throws SQLException {
		ArrayList<ArrayList<String>> tables_and_attributes = new ArrayList<ArrayList<String>>();
		String query = "select table_name from information_schema.tables where table_schema='moviedb'";
		PreparedStatement prep = dbcon.prepareStatement(query);
		ResultSet results = prep.executeQuery();
		ArrayList<String> tables = new ArrayList<String>();
		
		while(results.next()) {
			tables.add(results.getString("table_name"));
		}
		
		results.close();
		
		for (String col: tables) {
			query = "select * from INFORMATION_SCHEMA.columns where table_name=? and table_schema='moviedb'";
			prep = dbcon.prepareStatement(query);
			prep.setString(1, col);
			results = prep.executeQuery();
			ArrayList<String> tmp = new ArrayList<String>();
			tmp.add(col);
			while(results.next()) {
				tmp.add(results.getString("COLUMN_NAME") + ": " + results.getString("COLUMN_TYPE"));
			}
			tables_and_attributes.add(tmp);
		}	
		return tables_and_attributes;
	}
	
	public String addNewStar(Connection dbcon, String star, String year) throws SQLException, IOException {
		
		Basic db = new Basic();
		String query_for_max = "select MAX(id) as id from stars";		
		PreparedStatement prep = dbcon.prepareStatement(query_for_max);
		ResultSet results = prep.executeQuery();
		
		String max = "";
		if (results.next())	
			max = results.getString("id").substring(0,2) + String.valueOf(Integer.parseInt(results.getString("id").substring(2)) + 1);
		
		if (star == null) {
			return null;
		}
		prep.close();
		
		
		String q = "select * from where";
		if (year == null || year == "null" || year.isEmpty()) {
			prep = dbcon.prepareStatement("select * from stars where name=? and birthYear is NULL");
			prep.setString(1, star);
			results = prep.executeQuery();
			if (results.next()) {
				return null;
			}
			String query = "insert into stars (id, name) values(?, ?)";
			prep = dbcon.prepareStatement(query);
			prep.setString(1, max);
			prep.setString(2, star);
			int chk = prep.executeUpdate();
			if (chk > 0) return max;
			return null;
		} else {
			prep = dbcon.prepareStatement("select * from stars where name=? and birthYear=?");
			prep.setString(1, star);
			prep.setString(2, year);
			results = prep.executeQuery();
			if (results.next()) {
				return null;
			}
			String query = "insert into stars values(?, ?, ?)";
			prep = dbcon.prepareStatement(query);
			prep.setString(1, max);
			prep.setString(2, star);
			prep.setInt(3, Integer.parseInt(year));
			int chk = prep.executeUpdate();
			if (chk > 0) return max;
			return null;
		}	
	}
	
public String getMaxStarId() throws IOException, SQLException {
	Basic basic = new Basic();
	Connection dbcon = basic.createDatabaseConnection();
	String query = "select MAX(id) as id from stars";
	PreparedStatement prep = dbcon.prepareStatement(query);
	ResultSet result = prep.executeQuery();
	String id = "";
	if (result.next()) {
		id = result.getString("id").substring(0,2) + String.valueOf(Integer.parseInt(result.getString("id").substring(2)) + 1);
	}
	return id;
}

public String getMaxMovieId() throws IOException, SQLException {
	Basic basic = new Basic();
	Connection dbcon = basic.createDatabaseConnection();
	String query = "select MAX(id) as id from movies";
	PreparedStatement prep = dbcon.prepareStatement(query);
	ResultSet result = prep.executeQuery();
	String id = "";
	if (result.next()) {
		id = result.getString("id").substring(0,2) + String.valueOf(Integer.parseInt(result.getString("id").substring(2)) + 1);
	}
	return id;
}

public int addNewGenre(Connection dbcon, String genre) throws SQLException, IOException {
		
		Basic db = new Basic();
		String q = "select MAX(id) as genreId from genres";
		String query = "insert into genres (name) values(?)";
		int max = 0;
		PreparedStatement prep = dbcon.prepareStatement(q);
		ResultSet results = prep.executeQuery();
		if (results.next()) {
			max = results.getInt("genreId") + 1;
		}
		prep.close();
		prep = dbcon.prepareStatement(query);
		prep.setString(1, genre);
		System.out.println(genre);
		int chk = prep.executeUpdate();
		if (chk > 0) return max;
		return -1;
}
	
	public boolean addMovie(Connection dbcon, String title, String director, String year, String birthyear,  String star, String genre) throws SQLException, IOException {
		String query_star = "select * from stars where name=? and birthYear is null";
		String query_star2 = "select * from stars where name=? and birthYear=?";
		String query_genre = "select * from genres where name=?";
		String query_movie = "select * from movies where title=? and year=? and director=?";
		Basic dbobj = new Basic();
		PreparedStatement prep;
		ResultSet results;
		if (birthyear == null || birthyear == "null" || birthyear.isEmpty()) {
			prep = dbcon.prepareStatement(query_star);
			prep.setString(1, star);
			results = prep.executeQuery();
		} else {
			prep = dbcon.prepareStatement(query_star2);
			prep.setString(1, star);
			prep.setString(2, birthyear);
			results = prep.executeQuery();
		}
		
		String star_id = "";
		int genre_id = 0;
		boolean add_genre_movie = false;
		boolean add_star_movie = false;
		
		if (results.next()) {
			star_id = results.getString("id");
			add_star_movie = true;
		} else {
			prep.close();
			add_star_movie = true;
			star_id = this.addNewStar(dbcon, star, birthyear);
		}
		
		prep = dbcon.prepareStatement(query_genre);
		prep.setString(1, genre);
		results = prep.executeQuery();
		
		if (results.next()) {
			genre_id = results.getInt("id");
			add_genre_movie = true;
		} else {
			prep.close();
			add_genre_movie = true;
			genre_id = this.addNewGenre(dbcon, genre);
		}
		
		prep = dbcon.prepareStatement(query_movie);
		prep.setString(1, title);
		prep.setString(2, year);
		prep.setString(3, director);
		results = prep.executeQuery();
		
		if (results.next()) {
			System.out.println("oops");
			return false;
			
		}
		
		
		String newMovie_id = "";
		String query_getMovieid = "select MAX(id) as id from movies";
		prep = dbcon.prepareStatement(query_getMovieid);
		results = prep.executeQuery();
		
		if (results.next())
			newMovie_id = results.getString("id").substring(0,2) + String.valueOf(Integer.parseInt(results.getString("id").substring(2)) + 1);
		
			prep = dbcon.prepareStatement("insert into movies values(?, ?, ?, ?)");
			prep.setString(1, newMovie_id);
			prep.setString(2, title);
			prep.setInt(3, Integer.parseInt(year));
			prep.setString(4, director);
			prep.executeUpdate();
		
		if (add_genre_movie) {
			prep = dbcon.prepareStatement("INSERT INTO genres_in_movies (genreId, movieId) SELECT * FROM (SELECT ?, ?) AS tmp WHERE NOT EXISTS (SELECT movieId FROM genres_in_movies WHERE movieId=? and genreId=?) LIMIT 1");
			prep.setInt(1, genre_id);
			prep.setString(2, newMovie_id);
			prep.setInt(4, genre_id);
			prep.setString(3, newMovie_id);
			prep.executeUpdate();
		}
		if (add_star_movie) {
			prep = dbcon.prepareStatement("INSERT INTO stars_in_movies (starId, movieId) SELECT * FROM (SELECT ?, ?) AS tmp WHERE NOT EXISTS (SELECT movieId FROM stars_in_movies WHERE movieId=? and starId=?) LIMIT 1");
			prep.setString(1, star_id);
			prep.setString(2, newMovie_id);
			prep.setString(4, star_id);
			prep.setString(3, newMovie_id);
			prep.executeUpdate();
		}
		System.out.println(newMovie_id);
		
		prep = dbcon.prepareStatement("{call add_movie(?)}");
		prep.setString(1, genre);
		prep.execute();
		
		//make sure other tables we use are correct
		prep = dbcon.prepareStatement(String.format("DROP TABLE IF EXISTS %s", genre));
		prep.execute();
		prep = dbcon.prepareStatement(String.format("CREATE TABLE %s select * from movieList2 where listGenres LIKE '%s'", genre, "%" + genre + "%"));
		prep.execute();
		System.out.println("Finished");
		return true;
	}
	
}
