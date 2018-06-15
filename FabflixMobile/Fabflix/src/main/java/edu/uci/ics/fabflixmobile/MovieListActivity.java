package edu.uci.ics.fabflixmobile;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;


public class MovieListActivity extends ActionBarActivity {
    static int currentPage = 1;
    static boolean maxPageReached = false;
    static int maxPage = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        currentPage = 1;
        maxPageReached = false;
        setContentView(R.layout.movie_list);
        Bundle bundle = getIntent().getExtras();
        JSONObject movies;
        try {
            movies = new JSONObject(bundle.getString("movies"));
            displayMovies(movies, bundle.getString("query"));
        } catch (JSONException e) {
            Log.d("json.error", e.toString());
        }
    }

    protected void goToSingleMovie(View view, Movie singleMovie) {

        Intent goToIntent = new Intent(this, SingleMovieActivity.class);
        goToIntent.putExtra("title", singleMovie.getTitle());
        goToIntent.putExtra("director", singleMovie.getDirector());
        goToIntent.putExtra("year", singleMovie.getYear());
        goToIntent.putExtra("stars", singleMovie.getStars());
        goToIntent.putExtra("genres", singleMovie.getGenres());
        startActivity(goToIntent);
    }

    public void nextPage(View view) {
        if (maxPageReached && currentPage == maxPage) {
            Log.d("max", String.valueOf(maxPageReached));
            return;
        }

        final Bundle bundle = getIntent().getExtras();
        final RequestQueue queue = NetworkManager.sharedManager(this).queue;
        final View currentView = view;
        final Context context = getApplicationContext();
        currentPage += 1;
        //10.0.2.2
        String url = "https://ec2-54-219-171-102.us-west-1.compute.amazonaws.com:8443/Fabflix/AndroidMovieList?title=" + bundle.getString("query").replaceAll(" ", "+") + "&offset=" + String.valueOf((Integer.valueOf(currentPage) - 1) * 10);

        final StringRequest searchRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.d("response", response);
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            if (jsonResponse.length() == 0) {
                                if (currentPage > 1)
                                    currentPage -= 1;
                                maxPageReached = true;
                                maxPage = currentPage;
                                return;
                            }
                            displayMovies(jsonResponse, bundle.getString("query"));
                            Toast.makeText(context, "Page: " + currentPage, Toast.LENGTH_LONG).show();
                        } catch (JSONException e) {
                            Log.d("error_json_movielist", e.toString());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Log.d("security.error", error.toString());
                    }
                }
        );

        queue.add(searchRequest);
    }

    public void prevPage(View view) {
        if (currentPage == 1) {
            Log.d("Not changing pages @ 0", String.valueOf(currentPage));
            return;
        }
        currentPage -= 1;
        final Bundle bundle = getIntent().getExtras();
        final RequestQueue queue = NetworkManager.sharedManager(this).queue;
        final View currentView = view;
        final Context context = getApplicationContext();
        String url = "https://ec2-54-219-171-102.us-west-1.compute.amazonaws.com:8443/Fabflix/AndroidMovieList?title=" + bundle.getString("query").replaceAll(" ", "+") + "&offset=" + String.valueOf((Integer.valueOf(currentPage) - 1) * 10);

        final StringRequest searchRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.d("response", response);
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            displayMovies(jsonResponse, bundle.getString("query"));
                            Toast.makeText(context, "Page: " + currentPage, Toast.LENGTH_LONG).show();
                        } catch (JSONException e) {
                            Log.d("error_json_movielist", e.toString());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Log.d("security.error", error.toString());
                    }
                }
        );

        queue.add(searchRequest);
    }

    protected void displayMovies(final JSONObject movies, String query) {
        TextView textview = (TextView)findViewById(R.id.textView2);
        textview.setText("Search results for movies matching title\n'" + query + "'");
        final ArrayList<Movie> movieList = new ArrayList<>();
        Log.d("length", String.valueOf(movies.length()));
        if (movies.length() < 10 && movies.length() > 0) {
            maxPageReached = true;
            maxPage = currentPage;
        }

        Iterator<String> iter = movies.keys();
        while (iter.hasNext()) {
            String key = iter.next();
            try {
                Object value = movies.get(key);
                JSONObject js = new JSONObject(value.toString());
                movieList.add(new Movie(js.get("title").toString(), "Year: " + js.get("year").toString(), "Director: " + js.get("director").toString(), "Genres: " + js.get("genres").toString(), "Stars: " + js.get("stars").toString()));
            } catch (JSONException e) {
                Log.d("movielist_json_error", e.toString());
            }
        }

        MovieAdapter adapter = new MovieAdapter(movieList, this);

        ListView listView = (ListView)findViewById(R.id.list);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Movie mov = movieList.get(position);
                if (movies.length() >= 10) {
                    maxPageReached = false;
                    maxPage = currentPage;
                }
                goToSingleMovie(view, mov);
            }
        });
    }
}
