package com.example.latheeshvirupakshi.popularmoviesstage1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;


import com.example.latheeshvirupakshi.popularmoviesstage1.Adapter.MovieAdapater;
import com.example.latheeshvirupakshi.popularmoviesstage1.Model.Movie;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class MainActivity extends ActionBarActivity {

    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    private final String DYNAMIC_MOVIE_URL="http://api.themoviedb.org/3/discover/movie?";
    private final String IMAGE_URL="http://image.tmdb.org/t/p/w500/";
    private String sort = null;

    //Enter your api key here after api_key= "&api_key=("your api key")"
    private String api_key ="&api_key=(enter your api key)";

// Creating objects for gridview, adapter and movie array
    private GridView mgridView;
    private MovieAdapater madapter;
    private ArrayList<Movie> movieArrayList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mgridView = (GridView) findViewById(R.id.grid_movies);
        movieArrayList=new ArrayList<>();
        madapter=new MovieAdapater(this,R.layout.movie,movieArrayList);


        mgridView.setAdapter(madapter);

        // OnGriditem click listner send details to Moviedetails through put Extra
        mgridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {


                Movie movie=(Movie)parent.getItemAtPosition(position) ;

                Intent intent=new Intent(MainActivity.this,MovieDetails.class);
                intent.putExtra("title",movie.getTitle());
                intent.putExtra("poster",movie.getImage());
                intent.putExtra("rating",movie.getRating());
                intent.putExtra("date",movie.getDate());
                intent.putExtra("description",movie.getDescription());

                startActivity(intent);

                Toast.makeText(MainActivity.this, "" + position,
                        Toast.LENGTH_SHORT).show();
            }
        });


    }

    // clear the grid data on start and then reterive the data from the url on default sort option

    @Override
    protected void onStart() {
        super.onStart();
        madapter.clear();
        getmovies();
    }

    private void getmovies() {

        Movietask task=new Movietask();
        SharedPreferences preferences= PreferenceManager.getDefaultSharedPreferences(this);
        sort=preferences.getString("sort","sort_by=popularity.desc");

        StringBuilder sb=new StringBuilder();
        sb.append(DYNAMIC_MOVIE_URL);
        sb.append(sort);
        sb.append(api_key);

        task.execute(String.valueOf(sb));
    }
// Inflate the settings option in the menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.settings,menu);
        return true;
    }


    //Background task for the request response from url and get JSON data and set into the paramaters
    public class Movietask extends AsyncTask<String,Void,Integer>
    {

        @Override
        protected Integer doInBackground(String... params) {

                    Integer result=0;
            try
            {
                HttpClient httpclient=new DefaultHttpClient() ;
                HttpResponse httpresponse=httpclient.execute(new HttpGet(params[0]));
                int statuscode=httpresponse.getStatusLine().getStatusCode();

               //If the Response is OK
                if(statuscode==200)
                {
                    String response=getStringFromInputStream(httpresponse.getEntity().getContent());

                    try {
                        JSONObject nresponse=new JSONObject(response);
                        JSONArray posts=nresponse.optJSONArray("results");

                        Movie movie;

                        for (int i=0;i<posts.length();i++)
                        {
                            JSONObject post=posts.optJSONObject(i);

                            String title = post.optString("title");
                            String poster = post.optString("poster_path");
                            String PosterPath = IMAGE_URL + poster;
                            String year = post.optString("release_date");
                            String description = post.optString("overview");
                            String rating = post.optString("vote_average");

                            movie=new Movie();

                            movie.setTitle(title);
                            movie.setDate(year);
                            movie.setDescription(description);
                            movie.setRating(rating);
                            movie.setImage(PosterPath);
                            movieArrayList.add(movie);


                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                                result=1;
                }

                          else {
                                result=0;
                               }

            }

            catch (Exception E)
            {
                Log.d(LOG_TAG,E.getLocalizedMessage()) ;
            }
            return result;
        }

        @Override
        protected void onPostExecute(Integer result) {


            if (result == 1)
            {
                madapter.setdata(movieArrayList);
            }
            else
            {
                Toast.makeText(MainActivity.this, "Error Fetching data", Toast.LENGTH_SHORT).show();
            }
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


       switch (item.getItemId())
       {
           case R.id.item_setting:

               Intent intent=new Intent(MainActivity.this,Settings.class);
               startActivity(intent);
               return true;
           default :
               return super.onOptionsItemSelected(item);
       }

    }


    //converting inputstream to string

    private static String getStringFromInputStream(InputStream is) {

        BufferedReader br = null;
        StringBuilder sb = new StringBuilder();

        String line;
        try {

            br = new BufferedReader(new InputStreamReader(is));
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return sb.toString();

    }


}