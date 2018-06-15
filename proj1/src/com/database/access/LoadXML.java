package com.database.access;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.xml.stream.*;
import java.sql.Connection;
import java.sql.Statement;
//import com.mysql.jdbc.Connection;


public class LoadXML {

	public static HashMap<String, ArrayList<String>> parseMovies() throws XMLStreamException, IOException, SQLException { 
		XMLInputFactory inputFactory = XMLInputFactory.newInstance();
        InputStream in = new FileInputStream("WebContent/stanford-movies/mains243.xml");
        XMLStreamReader streamReader = inputFactory.createXMLStreamReader(in);
        HashMap<String, ArrayList<String>> movies = new HashMap<String, ArrayList<String>>();
        Queries queries = new Queries();
        String max = queries.getMaxMovieId();
        String currTitle = "";
        boolean year = false;
        boolean director = false;
        streamReader.next();
        
        ArrayList<String> tmp = new ArrayList<String>();

        while (streamReader.hasNext()) {
            if (streamReader.isStartElement()) {
                switch (streamReader.getLocalName()) {
	                case "t":
	                	try {
	                		if (!tmp.isEmpty()) {
	                			if (director && year && !currTitle.isEmpty()) {
	                				tmp.add(0, max);
	                				if (tmp.size() == 4) {
	                					tmp.add("N/A");
	                				}
		                			movies.put(currTitle, tmp);
		                			max = max.substring(0,2) + String.valueOf(Integer.parseInt(max.substring(2)) + 1);
		                			director = false;
		                			year = false;
	                			} else {
	                				System.out.printf("Movie: %s was not added\n", tmp.get(0));
	                			}
			                    tmp = new ArrayList<String>();
	                		}
		                    currTitle = streamReader.getElementText();
		                    tmp.add(currTitle);
						} catch (Exception e) {
            				System.out.println(e);
						}
	                    break;
	                case "dirn":
	                	try {
	                		String dir = streamReader.getElementText();
	                		if (!director && !dir.isEmpty()) {
	                			tmp.add(dir);
	                			director = true;
	                		}
						} catch (Exception e) {
							System.out.println(e);
						}
	                    break;
	                case "year":
	                	try {
	                		String yr = streamReader.getElementText();
	                		if (yr.matches("\\d+")) {
	                			tmp.add(yr);
	                			year = true;
	                		}
						} catch (Exception e) {
							System.out.println(e);
						}
	                    break;
	                case "cat":
	                	try {
	                		tmp.add(streamReader.getElementText());
	                	} catch (Exception e) {
	                		System.out.println(e);
	                		
	                	}
                }
            }
            streamReader.next();
        }

        return movies;
	}
	
	public static HashMap<String, ArrayList<String>> parseCast() throws XMLStreamException, IOException, SQLException { 
		XMLInputFactory inputFactory = XMLInputFactory.newInstance();
        InputStream in = new FileInputStream("WebContent/stanford-movies/casts124.xml");
        XMLStreamReader streamReader = inputFactory.createXMLStreamReader(in);
        ArrayList<ArrayList<String>> stars = new ArrayList<ArrayList<String>>();
        String max = "";
        String currMovie = "";
        HashMap<String, ArrayList<String>> actors = parseActors();
        ArrayList<String> tmp = new ArrayList<String>();

        while (streamReader.hasNext()) {
            if (streamReader.isStartElement()) {
                switch (streamReader.getLocalName()) {
	                case "t":
	                	try {
	                		if (!tmp.isEmpty()) {
			                    stars.add(tmp);
			                    tmp = new ArrayList<String>();
	                		}
	                		currMovie = streamReader.getElementText();
						} catch (Exception e) {
							System.out.println(e);
						}
	                    break;
	                case "a":
	                	try {
	                		if (!currMovie.isEmpty()) {
	                			actors.get(streamReader.getElementText()).add(currMovie);
	                		}
						} catch (Exception e) {
						}
	                    break;
	                	
                }
            }
            streamReader.next();
        }
        
        return actors;
	}

	
	public static HashMap<String, ArrayList<String>> parseActors() throws XMLStreamException, IOException, SQLException { 
		XMLInputFactory inputFactory = XMLInputFactory.newInstance();
        InputStream in = new FileInputStream("WebContent/stanford-movies/actors63.xml");
        XMLStreamReader streamReader = inputFactory.createXMLStreamReader(in);
        HashMap<String, ArrayList<String>> actors = new HashMap<String, ArrayList<String>>();
        String currActor = "";
        Queries queries = new Queries();
        String max = queries.getMaxStarId();
        
        ArrayList<String> tmp = new ArrayList<String>();

        while (streamReader.hasNext()) {
            if (streamReader.isStartElement()) {
                switch (streamReader.getLocalName()) {
	                case "stagename":
	                	try {
	                		if (!tmp.isEmpty()) {
	                			tmp.add(0, max);
			                    actors.put(currActor, tmp);
			                    max = max.substring(0,2) + String.valueOf(Integer.parseInt(max.substring(2)) + 1);
			                    tmp = new ArrayList<String>();
	                		}
		                    currActor = streamReader.getElementText();
		                    tmp.add(currActor);
						} catch (Exception e) {
							System.out.println(e);
						}
	                    break;
	                case "dob":
	                	try {
	                		String dob = streamReader.getElementText();
	                		if (dob.matches("\\d+"))
	                			tmp.add(dob);
	                		else
	                			tmp.add("");
						} catch (Exception e) {
							System.out.println(e);
						}
	                    break;
	                	
                }
            }
            streamReader.next();
        }
        
        return actors;
	}
	
	public static void main(String[] args) throws XMLStreamException, IOException, SQLException {
		long startTime = System.nanoTime();

	        HashMap<String, ArrayList<String>> movies = parseMovies();
			HashMap<String, ArrayList<String>> stars = parseCast();
			Basic db = new Basic();
			Queries queries = new Queries();
			Connection dbcon = db.createDatabaseConnection();
			String movie_add_query = "INSERT into movies values(?, ?, ?, ?)";
			String check_movie_query = "SELECT EXISTS(SELECT * FROM movies where title=? and year=?) as count";

			Iterator it = movies.entrySet().iterator();
		    while (it.hasNext()) {
		        Map.Entry pair = (Map.Entry)it.next();
		        if (movies.get(pair.getKey()).size() == 3) {
		        	continue;
		        }
		        String name = pair.getKey().toString();
		        PreparedStatement prep = dbcon.prepareStatement(check_movie_query);
		        prep.setString(1, pair.getKey().toString());
		        prep.setString(2, ((ArrayList<String>)pair.getValue()).get(2));
		        ResultSet results = prep.executeQuery();
		        if (results.next()) {
		        	if (results.getInt("count") > 0) {
		        		System.out.printf("title: %s, id: %s ,already exists\n", movies.get(name).get(1), movies.get(name).get(0));
		        		continue;
		        	}
		        }
		        prep = dbcon.prepareStatement(movie_add_query);
		        prep.setString(1, movies.get(name).get(0));
		        prep.setString(2, movies.get(name).get(1));
		        prep.setString(3, movies.get(name).get(2));
		        prep.setString(4, movies.get(name).get(3));
		        try {
		        	prep.executeUpdate();
		        } catch (Exception e) {
		        	System.out.printf("Movie: id: %s, title: %s not added\n", movies.get(name).get(0), movies.get(name).get(1));
		        }
		    }
		    
		    System.out.println("Finished adding movies");
		    Iterator it2 = stars.entrySet().iterator();
		    while(it2.hasNext()) {
		    	Map.Entry pair = (Map.Entry)it2.next();
		    	String query = "insert into stars (id, name) values(?, ?)";
		    	String query2 = "insert into stars values(?, ?, ?)";
		    	String query3 = "INSERT INTO stars_in_movies (starId, movieId) SELECT * FROM (SELECT ?, ?) "
		    					+ "AS tmp WHERE NOT EXISTS (SELECT movieId FROM stars_in_movies WHERE movieId=? and starId=?) LIMIT 1";
		    	String check_star_no_year = "select * from stars where name=? and birthYear is null";
		    	String check_star_with_year = "select * from stars where name=? and birthYear=?";

		    	String key = pair.getKey().toString();
		    	String starId = stars.get(key).get(0);
		    	String starName = stars.get(key).get(1);
		    	String starYear = stars.get(key).get(2);
		    	PreparedStatement prep;
		    	
		    	if (starYear.isEmpty()) {
		    		try {
		    			prep = dbcon.prepareStatement(check_star_no_year);
		    			prep.setString(1, starName);
		    			ResultSet results = prep.executeQuery();
		    			if (results.next()) {
		    				starId = results.getString("id");
		    			}
		    		} catch(Exception e) {
		    			System.out.printf("Star: %s\n", starName);
		    			continue;
		    		}
		    		try {
		    			prep = dbcon.prepareStatement(query);
		    			prep.setString(1, starId);
		    			prep.setString(2, starName);
		    			prep.executeUpdate();
		    		} catch (Exception e) {
		    			System.out.printf("Duplicate Star: id: %s, name: %s\n", starId, starName);
		    		}
		    	} else {
		    		try {
		    			prep = dbcon.prepareStatement(check_star_with_year);
		    			prep.setString(1, starName);
		    			prep.setString(2, starYear);
		    			ResultSet results = prep.executeQuery();
		    			if (results.next()) {
		    				starId = results.getString("id");
		    			}
		    		} catch(Exception e) {
		    			System.out.printf("Star: %s\n", starName);
		    			continue;
		    		}
		    		
		    		try {
		    			prep = dbcon.prepareStatement(query2);
			   			prep.setString(1, starId);
			   			prep.setString(2, starName);
			   			prep.setString(3, starYear);
			   			prep.executeUpdate();
		    		} catch (Exception e) {
		    			System.out.printf("Duplicate Star: id: %s, name: %s, year: %s\n", starId, starName, starYear);
		    		}
		    	}
	    		int lst_size = ((ArrayList<String>) pair.getValue()).size();
	    		ArrayList<String> lst = ((ArrayList<String>) pair.getValue());
		    	for (int i = 3; i < lst_size; i++) {
		    		String movieId = "";
		    		try{
		    			movieId = movies.get(lst.get(i)).get(0);
		    		} catch (Exception e) {
		    			continue;
		    		}
		    		prep = dbcon.prepareStatement(query3);
		    		prep.setString(1, starId);
		    		prep.setString(2, movieId);
		    		prep.setString(3, movieId);
		    		prep.setString(4, starId);
		    		try {
		    			prep.executeUpdate();
		    		} catch (Exception e) {
		    			System.out.printf("Duplicate Stars_in_movies: starid: %s, movieid: %s, title: %s\n", starId, movieId, lst.get(i));
		    		}
		    	}
		    }
		    
		    System.out.println("finished stars_in_movies");
		    stars = null;
		    System.gc();
			Iterator it3 = movies.entrySet().iterator();
			PreparedStatement prep2 = dbcon.prepareStatement("select MAX(id) as max from genres");
			ResultSet res = prep2.executeQuery();
			int max = 0;
			if (res.next()) {
				max = res.getInt("max");
			}
			HashMap<String, Integer> genreList = new HashMap<String, Integer>();
			prep2 = dbcon.prepareStatement("select * from genres");
			res = prep2.executeQuery();
			while (res.next()) {
				genreList.put(res.getString("name").trim(), res.getInt("id"));
			}
			while(it3.hasNext()) {
				String add_genre = "Insert into genres values(?, ?)";
				String check_genre = "select * from genres where name=?";
				String add_genre_movies = "INSERT INTO genres_in_movies (genreId, movieId) SELECT * FROM (SELECT ?, ?) "
						+ "AS tmp WHERE NOT EXISTS (SELECT movieId FROM genres_in_movies WHERE movieId=? and genreId=?) LIMIT 1";
				
				Map.Entry pair = (Map.Entry)it3.next();
				if (movies.get(pair.getKey()).size() >= 5) {
					for (int i = 4; i < ((ArrayList<String>)pair.getValue()).size(); i++) {
						boolean genre_exists = false;
						int genre_id = 0;
						//int max = 0;
						PreparedStatement prep;
						ResultSet results;
			    		String movieId = "";

			    		try{
			    			movieId = ((ArrayList<String>)pair.getValue()).get(0);
			    		} catch (Exception e) {
			    			continue;
			    		}
			    		if (genreList.get(movies.get(pair.getKey()).get(i).trim()) != null) {
			    			genre_id = genreList.get(movies.get(pair.getKey()).get(i).trim());
							genre_exists = true;
						} else {
							max += 1;
							genre_id = max;
						}
						if (!genre_exists) {
							prep = dbcon.prepareStatement(add_genre);
							prep.setInt(1, max);
							prep.setString(2, movies.get(pair.getKey()).get(i).trim());
							try{
								prep.executeUpdate();
								genreList.put(movies.get(pair.getKey()).get(i).trim(), max);
							} catch (Exception e) {
								max -= 1;
								System.out.printf("genre: genre: %s, genreid: %s not added\n", movies.get(pair.getKey()).get(i));
								continue;
							}
						}

			    		
						prep = dbcon.prepareStatement(add_genre_movies);
						prep.setInt(1, genre_id);
						prep.setString(2, movieId);
						prep.setString(3, movieId);
						prep.setInt(4, genre_id);
						try {
							prep.executeUpdate();
						} catch (Exception e) {
			    			System.out.printf("genres_in_movies: genreid: %s, movieid: %s, title: %s\n", genre_id, movieId, pair.getKey());
						}
					}
				} 
			}
			System.out.println("finished genres_in_movies");

			PreparedStatement prep = dbcon.prepareStatement("{call add_movie(?)}");
			prep.setString(1, "genre");
			prep.execute();

			prep = dbcon.prepareStatement("select * from genres");
			ResultSet results = prep.executeQuery();
			while (results.next()) {
				String name = results.getString("name").trim();
				try {
					prep = dbcon.prepareStatement(String.format("DROP TABLE IF EXISTS `%s`", name));
					prep.execute();
					prep = dbcon.prepareStatement(String.format("CREATE TABLE `%s` select * from movieList2 where listGenres LIKE '%s'", name, "%" + name + "%"));
					prep.execute();
				} catch (Exception e) {
//					System.out.print("Creating tables error:");
//					System.out.println(e);
				}
			}
			
			Iterator it4 = genreList.entrySet().iterator();
			while (it4.hasNext()) {
				boolean delete = false;
				Map.Entry pair = (Map.Entry)it4.next();
				prep = dbcon.prepareStatement(String.format("select * from %s", pair.getKey().toString()));
				try {
					res = prep.executeQuery();
				} catch (Exception e) {
					continue;
				}
				if (res.next()) {
					continue;
				} else {
					delete = true;
				}
				res.close();
				if (delete) {
					prep = dbcon.prepareStatement(String.format("drop table %s", pair.getKey().toString()));
					try {
						prep.execute();
					} catch (Exception e) {
						
					}
				}
			}
			
			
			
			 
			long endTime = System.nanoTime();
			long duration1 = (endTime - startTime);
			System.out.println(duration1 / 1000000);
	}

}
