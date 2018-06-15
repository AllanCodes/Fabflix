package edu.uci.ics.fabflixmobile;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class SingleMovieActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.single_movie);
        Bundle bundle = getIntent().getExtras();
        Movie movie = new Movie(bundle.getString("title"), bundle.getString("year"), bundle.getString("director"), bundle.getString("genres"), bundle.getString("stars"));
        displayMovie(movie);
    }

    protected void displayMovie(Movie movie) {
        TextView textview = (TextView)findViewById(R.id.textView2);
        textview.setText("About:\n'" + movie.getTitle() + "'");
        final ArrayList<Movie> movieList = new ArrayList<>();
        movieList.add(movie);


        MovieAdapter adapter = new MovieAdapter(movieList, this);

        ListView listView = (ListView)findViewById(R.id.list);
        listView.setAdapter(adapter);

    }
}
