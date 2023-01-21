package com.tech.with.nate.mymovies;

import static com.tech.with.nate.mymovies.MoviesFragment.EXTRA_URL;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class MovieDetails extends AppCompatActivity {

    private Movie movie;
    private TextView movie_name;
    private TextView watch_time;
    private TextView imdbRating;
    private TextView release_date;
    private TextView desc;
    private ImageView thumbnail;

    //JSON



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        setUI();

        movie = (Movie) getIntent().getSerializableExtra(EXTRA_URL);


    }


    private void setUI(){
        movie_name = findViewById(R.id.movie_name);
        watch_time = findViewById(R.id.watch_time);
        imdbRating = findViewById(R.id.imdbRating);
        release_date = findViewById(R.id.release_date);
        desc = findViewById(R.id.short_desc);
        thumbnail = findViewById(R.id.thumbnail);
    }

}