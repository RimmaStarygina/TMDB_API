package com.example.tmdb;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;

import com.example.tmdb.adapter.MyAdapter;
import com.example.tmdb.model.MovieObject;

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

public class MainActivity extends AppCompatActivity {

    private static String JSON_URL = "https://api.themoviedb.org/3/movie/upcoming?api_key=0ef079e8bdd6b2e0ea7624f75462713c";

    List<MovieObject> movies;
    RecyclerView recyclerView;
    MyAdapter.RecyclerViewClickListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        movies = new ArrayList<>();
        recyclerView = findViewById(R.id.recyclerView);

        //TODO: check need or not
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //   create Async Task

        GetData getData = new GetData();
        getData.execute();
    }

    public class GetData extends AsyncTask<String, String, String>{

        @Override
        protected String doInBackground(String... strings) {
            //get String of Json
            String current = "";

            // try to get JSON
            try{
                URL url;
                HttpURLConnection urlConnection = null;
                /*TODO: check is it realy needs to be a second try*/
                try{
                    url = new URL(JSON_URL);
                    urlConnection = (HttpURLConnection) url.openConnection();

                    InputStream inputStream = urlConnection.getInputStream();
                    InputStreamReader inputStreamReader = new InputStreamReader(inputStream);

                    int data = inputStreamReader.read();
                    while(data != -1){
                        current += (char) data;
                        data = inputStreamReader.read();
                    }
                    return current;



                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                //close connection
                finally{
                    if(urlConnection != null){
                        urlConnection.disconnect();
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            return current;
        }

        //   pass String of JSON to onPostExecute method, onPostExecute analyze String and get JSON object,
        //   then pass it to Model class, then to Adapter class and Adapter display it in RecyclerView.


        //        WAS CHANGED!!!!!!!!!!!
        @Override
        protected void onPostExecute(String s) {
            try{
//                s = our current String of JSON. JSONObject get s and analyze it
                JSONObject jsonObject = new JSONObject(s);
                JSONArray jsonArray = jsonObject.getJSONArray("results");
                for(int i = 0; i<jsonArray.length(); i++){
                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                    MovieObject movie = new MovieObject();
                    movie.setRate(jsonObject1.getString("vote_average"));
                    movie.setName(jsonObject1.getString("title"));
                    movie.setOverview(jsonObject1.getString("overview"));
                    movie.setImg(jsonObject1.getString("poster_path"));
                    movies.add(movie);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            putDataIntoRecyclerView(movies);
        }
    }

    private void putDataIntoRecyclerView(List<MovieObject> movies){
        setOnClickListener();
        MyAdapter myAdapter = new MyAdapter(this, movies, listener);
//        RecyclerView is the ViewGroup that contains the views corresponding to your data.
//        It's a view itself, so you add RecyclerView into your layout the way you would add any
//        other UI element. Each individual element in the list is defined by a view holder object.
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerView.setAdapter(myAdapter);
    }

    private void setOnClickListener() {
        listener = new MyAdapter.RecyclerViewClickListener() {
            @Override
            public void onClick(View v, int position) {

                Intent intent = new Intent(getApplicationContext(), DetailActivity.class);
                intent.putExtra("movieTitle", movies.get(position).getName());
                intent.putExtra("movieRate", movies.get(position).getRate());
                intent.putExtra("overView", movies.get(position).getOverview());

                intent.putExtra("image", movies.get(position).getImg());

                startActivity(intent);
            }
        };
    }
}
