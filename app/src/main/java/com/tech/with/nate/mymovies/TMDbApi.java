package com.tech.with.nate.mymovies;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class TMDbApi {

    private static final String API_KEY = "7b7811eea3af3eb54b130ea81cfd49fa";
    private static final String BASE_URL = "https://api.themoviedb.org/3/movie/";
//    https://api.themoviedb.org/3/movie/upcoming?api_key=7b7811eea3af3eb54b130ea81cfd49fa

    private static final String POPULAR = "popular";
    private static final String TOP_RATED = "top_rated";
    private static final String UPCOMING = "upcoming";


    public static void getAllPagesMovies(final OnMoviesFetchedListener listener) {
        new AsyncTask<Void, Void, List<Movie>>() {
            @Override
            protected List<Movie> doInBackground(Void... voids) {
                HttpURLConnection urlConnection = null;
                BufferedReader reader = null;
                String jsonStr = null;
                List<Movie> movies = new ArrayList<>();
                int page = 1;
                while (true) {
                    try {
                        URL url = new URL(BASE_URL + POPULAR + "?api_key=" + API_KEY + "&page=" + page);
                        urlConnection = (HttpURLConnection) url.openConnection();
                        urlConnection.setRequestMethod("GET");
                        urlConnection.connect();
                        InputStream inputStream = urlConnection.getInputStream();
                        StringBuilder buffer = new StringBuilder();
                        if (inputStream == null) {
                            return null;
                        }
                        reader = new BufferedReader(new InputStreamReader(inputStream));
                        String line;
                        while ((line = reader.readLine()) != null) {
                            buffer.append(line).append("\n");
                        }
                        if (buffer.length() == 0) {
                            return null;
                        }
                        jsonStr = buffer.toString();
                        JSONObject jsonObject = new JSONObject(jsonStr);
                        JSONArray results = jsonObject.getJSONArray("results");
                        if (results.length() == 0) {
                            break;
                        }
                        for (int i = 0; i < results.length(); i++) {
                            JSONObject movieJson = results.getJSONObject(i);
                            Movie movie = new Movie();
                            movie.setId(movieJson.getInt("id"));
                            movie.setMovieName(movieJson.getString("title"));
                            movie.setThumbnailUrl(movieJson.getString("poster_path"));
                            movie.setDesc(movieJson.getString("overview"));
                           // movie.setReleaseDate(movieJson.getString("release_date"));
                            movies.add(movie);
                        }
                        page++;
                    } catch (IOException e) {
                        Log.e(TMDbApi.class.getSimpleName(), "Error", e);
                        break;
                    } catch (JSONException e) {
                        Log.e(TMDbApi.class.getSimpleName(), "Error", e);
                        break;
                    } finally {
                        if (urlConnection != null) {
                            urlConnection.disconnect();
                        }
                        if (reader != null) {
                            try {
                                reader.close();
                            } catch (final IOException e) {
                                Log.e(TMDbApi.class.getSimpleName(), "Error closing stream", e);
                            }
                        }
                    }
                }
                return movies;
            }

            @Override
            protected void onPostExecute(List<Movie> movies) {
                super.onPostExecute(movies);
                if (listener != null) {
                    listener.onMoviesFetched(movies);
                }
            }
        }.execute();
    }

    public interface OnMoviesFetchedListener {
        void onMoviesFetched(List<Movie> movies);
    }
}

