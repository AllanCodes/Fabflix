package edu.uci.ics.fabflixmobile;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.SearchView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

public class SearchActivity extends ActionBarActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_page);
        final android.widget.SearchView searchView = (SearchView) findViewById(R.id.searchText);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                findViewById(R.id.button2).performClick();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return true;
            }
        });
    }

    protected void showMovieList(View view, String movies, String query) {
        /*
            Go to Movie List page after query is complete and results are received
         */

        Intent goToIntent = new Intent(this, MovieListActivity.class);
        goToIntent.putExtra("movies", movies);
        goToIntent.putExtra("query", query);
        startActivity(goToIntent);
    }


    public void doSearch (View view) {
        final RequestQueue queue = NetworkManager.sharedManager(this).queue;
        final View currentView = view;
        String url = "https://ec2-54-219-171-102.us-west-1.compute.amazonaws.com:8443/Fabflix/AndroidMovieList?title=" + ((android.widget.SearchView) findViewById(R.id.searchText)).getQuery().toString().replaceAll(" ", "+");

        final StringRequest searchRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.d("response", response);
                        showMovieList(currentView, response.toString(), ((android.widget.SearchView) findViewById(R.id.searchText)).getQuery().toString());
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

}

