package com.example.tmdb;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.tmdb.model.MovieObject;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    TextView titleTextView, rateTextView, overViewTextView;
    ImageView imageView;
    private List<MovieObject> movies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

       titleTextView = findViewById(R.id.titleTextView);
       rateTextView = findViewById(R.id.rateTextView);
        overViewTextView = findViewById(R.id.overViewTextView);
        imageView = findViewById(R.id.imageView);

        Bundle extras = getIntent().getExtras();
        String movieTitle = extras.getString("movieTitle");
        String movieRate = extras.getString("movieRate");
        String movieOverView = extras.getString("overView");

        titleTextView.setText(movieTitle);
        rateTextView.setText("Rate: " + movieRate);
        overViewTextView.setText(movieOverView);

        String movieImage = extras.getString("image");
        Glide.with(this)
                .load("https://image.tmdb.org/t/p/w500"+movieImage)
                .into(imageView);
    }
}