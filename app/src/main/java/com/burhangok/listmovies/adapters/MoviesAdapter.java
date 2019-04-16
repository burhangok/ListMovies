package com.burhangok.listmovies.adapters;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.burhangok.listmovies.R;
import com.burhangok.listmovies.common.Constants;
import com.burhangok.listmovies.databases.LocalDatabaseConfig;
import com.burhangok.listmovies.helpers.ChangeFragment;
import com.burhangok.listmovies.models.MovieItem;
import com.burhangok.listmovies.ui.MoviesDetailFragment;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MovieViewHolder> {

    List<MovieItem> movies;
    LayoutInflater layoutInflater;
    Context context;

    public boolean movieFav;


    public MoviesAdapter(Context context, List<MovieItem> movies) {
        this.context = context;
        this.movies = movies;
        this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rowView = layoutInflater.inflate(R.layout.movies_row_layout, parent, false);
        return new MovieViewHolder(rowView);
    }

    @Override
    public void onBindViewHolder(final MovieViewHolder holder, int position) {
        final MovieItem movie = movies.get(position);

        final LocalDatabaseConfig databaseObj = new LocalDatabaseConfig(holder.itemView.getContext());

        movieFav = databaseObj.controlMovie(movie.getTitle());
        if (movieFav) {
            holder.favIV.setImageResource(android.R.drawable.star_big_on);
        }

        holder.releaseDateTV.setText(movie.getReleaseDate().split("-")[0]);
        holder.titleTV.setText(movie.getTitle());
        holder.ratingTV.setText(String.valueOf(movie.getRating()));
        // picasso kütüphanesi kullanarak ilgili resmi aldıktan sonra imageview e set ettik
        Picasso.get().load(Constants.MOVIEDB_SMALL_POSTER_URL + movie.getPosterPath()).into(holder.photoIV);

        holder.detailBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("id", Integer.toString(movie.getId()));
                bundle.putString("photo", movie.getPosterPath());

                MoviesDetailFragment detailFragment = new MoviesDetailFragment();
                detailFragment.setArguments(bundle);

                ChangeFragment changeFragment = new ChangeFragment(context);
                changeFragment.change(detailFragment);
            }
        });


    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    class MovieViewHolder extends RecyclerView.ViewHolder {
        TextView releaseDateTV, titleTV, ratingTV;
        ImageView photoIV, favIV;
        Button detailBTN;

        public MovieViewHolder(View itemView) {
            super(itemView);
            releaseDateTV = itemView.findViewById(R.id.releasedate);
            titleTV = itemView.findViewById(R.id.title);
            ratingTV = itemView.findViewById(R.id.rating);
            photoIV = itemView.findViewById(R.id.photo);
            favIV = itemView.findViewById(R.id.favStatus);
            detailBTN = itemView.findViewById(R.id.toDetail);


        }


    }


}