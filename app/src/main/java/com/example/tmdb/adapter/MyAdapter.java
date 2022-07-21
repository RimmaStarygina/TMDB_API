package com.example.tmdb.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.tmdb.R;
import com.example.tmdb.model.MovieObject;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    private Context myContext;
    private List<MovieObject> movies;
    private RecyclerViewClickListener listener;

    public MyAdapter(Context myContext, List<MovieObject> movies, RecyclerViewClickListener listener) {
        this.myContext = myContext;
        this.movies = movies;
        this.listener = listener;
    }

    //    handle our recyclerView
    @NonNull
    @Override
//    onCreateViewHolder only creates a new view holder when there are no existing view holders
//    which the RecyclerView can reuse. So, for instance, if your RecyclerView can display 5 items
//    at a time, it will create 5-6 ViewHolders , and then automatically reuse them, each time
//    calling onBindViewHolder
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
//        The LayoutInflater class is used to instantiate the contents of layout XML files into
//        their corresponding View objects. In other words, it takes an XML file as input and builds
//        the View objects from it.
//        LayoutInflater is used to create a new View (or Layout) object from one of your xml layouts.
//        findViewById just gives you a reference to a view than has already been created.
//        You might think that you haven't created any views yet, but whenever you call setContentView
//        in onCreate, the activity's layout along with its subviews gets inflated (created) behind the scenes.
//        So if the view already exists, then use findViewById. If not, then create it with a LayoutInflater.
        LayoutInflater inflater = LayoutInflater.from(myContext);
        view = inflater.inflate(R.layout.movie_cell, parent, false);
        return new MyViewHolder(view);
    }

    @Override
//    Instead of creating new view for each new row, an old view is recycled and reused by binding
//    new data to it. This happens exactly in onBindViewHolder() . Initially you will get new
//    unused view holders and you have to fill them with data you want to display.
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

//        TODO: CHECK!!!
        holder.rate.setText("Rate: " + movies.get(position).getRate());
        holder.name.setText(movies.get(position).getName());

//        Using Glide library to display image
//        https://www.youtube.com/watch?v=eiP-vnSM0OM

//        go to https://bumptech.github.io/glide/doc/download-setup.html
//       1. to download repositories to build.gradle(Project):
//         mavenCentral()
//         maven { url 'https://maven.google.com' }
//        2. to download dependencies to build.gradle(Module)
//        compile 'com.github.bumptech.glide:glide:4.11.0'.
//        If you have an error (No sync project)
//        go to settings.grandle and replace the line: "repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)"
//          with "repositoriesMode.set(RepositoriesMode.PREFER_SETTINGS)"

//        we need to add a link of images. Poster-path does not consist right path to the image, but only the name.
//        go to https://developers.themoviedb.org/3/getting-started/images and copy https://image.tmdb.org/t/p/w500
//        then past image path.

//        WAS CHANGED!!!!!!!!!!!!!!!!!!!!!
        Glide.with(myContext)
                .load("https://image.tmdb.org/t/p/w500" + movies.get(position).getImg()) //load from
                .into(holder.image);  // download to
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }


    //    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    public  class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView rate;
        TextView name;
        ImageView image;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            rate = itemView.findViewById(R.id.rateTextView);
            name = itemView.findViewById(R.id.nameTextView);
            image = itemView.findViewById(R.id.imageView);
            itemView.setOnClickListener(this);
        }

        //        for transfer data to detail activity
        @Override
        public void onClick(View v) {
            listener.onClick(v, getAdapterPosition());
        }
    }

    //    to transfer data to detail activity
    public interface RecyclerViewClickListener{
        void onClick(View v, int position);
    }
}
