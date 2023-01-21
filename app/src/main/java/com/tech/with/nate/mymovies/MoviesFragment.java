package com.tech.with.nate.mymovies;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MoviesFragment extends Fragment implements OnItemClickListener{

    public static final String EXTRA_URL = "movie";
    private static final String JSON_URL = "https://api.themoviedb.org/3/movie/top_rated?api_key=7b7811eea3af3eb54b130ea81cfd49fa";
    private static final String BASE_URL = "https://api.themoviedb.org/3/";
    private static final String API_KEY = "7b7811eea3af3eb54b130ea81cfd49fa";
    private static final int TOTAL_PAGE = 5;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private MovieAdapter adapter;
    List<Movie> allMovies;

    View view;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.movies_fragment, container, false);
        recyclerView = view.findViewById(R.id.recyclerView);
        layoutManager = new GridLayoutManager(getContext(), 4);
        recyclerView.setHasFixedSize(true);

        allMovies = new ArrayList<>();





        recyclerView.setLayoutManager(layoutManager);

//        Getting data from the API
  //      GetData getData = new GetData();
  //      getData.execute();

        //Testing from the api class
        TMDbApi.getAllPagesMovies(new TMDbApi.OnMoviesFetchedListener() {
            @Override
            public void onMoviesFetched(List<Movie> movies) {
                adapter.setMovies(movies);
            }
        });

        return view;
    }

    // Passing the data into the new Activity
    @Override
    public void onItemClick(int position) {
        Intent data = new Intent(this.getContext(), MovieDetails.class);
        Movie clickedMovie = allMovies.get(position);


        data.putExtra(EXTRA_URL,(Parcelable) clickedMovie);
        startActivity(data);
    }


    public class GetData extends AsyncTask<String, String, String>{

        @Override
        protected String doInBackground(String... strings) {

            String current = "";

            try {
                URL url;
                HttpURLConnection urlConnection = null;

                try {



                    url = new URL(JSON_URL);
                    urlConnection = (HttpURLConnection) url.openConnection();


                    InputStream is = urlConnection.getInputStream();
                    InputStreamReader isr = new InputStreamReader(is);

                    int data = isr.read();
                    while (data != -1){
                        current += (char) data;
                        data = isr.read();
                    }
                    return current;


                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }finally {
                    if (urlConnection !=  null){
                        urlConnection.disconnect();
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }


            return current;
        }

        @Override
        protected void onPostExecute(String s) {


            try {
                JSONObject jsonObject = new JSONObject(s);

                //TODO: get API JSON array name
                JSONArray jsonArray = jsonObject.getJSONArray("results");

                for (int i = 0; i < jsonArray.length(); i++){
                    JSONObject object = jsonArray.getJSONObject(i);

                    Movie movie  = new Movie();
                    movie.setMovieName(object.getString("title"));
                    movie.setYear(object.getString("release_date"));
                    movie.setThumbnailUrl(object.getString("poster_path"));
                    allMovies.add(movie);
                }




            } catch (JSONException e) {
                e.printStackTrace();
            }

            setData(allMovies);



        }
    }

    private void setData(List<Movie> movies){
        adapter = new MovieAdapter(movies, getContext());
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(MoviesFragment.this);
    }

}
