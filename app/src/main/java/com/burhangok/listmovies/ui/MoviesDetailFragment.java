package com.burhangok.listmovies.ui;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.burhangok.listmovies.R;
import com.burhangok.listmovies.adapters.MoviesAdapter;
import com.burhangok.listmovies.common.Constants;
import com.burhangok.listmovies.databases.LocalDatabaseConfig;
import com.burhangok.listmovies.databases.RetrofitConfig;
import com.burhangok.listmovies.models.MovieItem;
import com.burhangok.listmovies.models.MoviesResponse;
import com.burhangok.listmovies.services.getMovieDetailsInterface;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class MoviesDetailFragment extends Fragment {


    public MoviesDetailFragment() {
        // Required empty public constructor
    }

    View fragmentView;

    TextView titleTV, releaseTV, ratingTV;
    ImageView posterIV;
    Button favBTN;

    String movieId, photo;

    MovieItem movieItem;

    getMovieDetailsInterface getServiceI;

    LocalDatabaseConfig databaseObj;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentView = inflater.inflate(R.layout.fragment_movies_detail, container, false);

        init();

        getDetails();

        favBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean movieFav = databaseObj.controlMovie(movieItem.getTitle());
                if (movieFav) {
                    databaseObj.deleteMovie(movieItem.getTitle());
                    favBTN.setText("ADD TO FAVOURITES");
                    Toast.makeText(v.getContext(), "Movie removed from favourites.", Toast.LENGTH_SHORT).show();

                } else if (!movieFav) {
                    databaseObj.savefavMovie(movieItem);
                    favBTN.setText("REMOVE FROM FAVOURITES");
                    Toast.makeText(v.getContext(), "Movie added from favourites.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return fragmentView;
    }


    private void init() {
        titleTV = fragmentView.findViewById(R.id.title);
        releaseTV = fragmentView.findViewById(R.id.releasedate);
        ratingTV = fragmentView.findViewById(R.id.userrating);
        posterIV = fragmentView.findViewById(R.id.moviePhoto);
        favBTN = fragmentView.findViewById(R.id.favBtn);

        movieId = getArguments().getString("id");
        photo = getArguments().getString("photo");

        getServiceI = RetrofitConfig.getClient().create(getMovieDetailsInterface.class);

        databaseObj = new LocalDatabaseConfig(this.getContext());

    }


    private void getDetails() {

        callResultsApi().enqueue(new Callback<MovieItem>() {


            @Override
            public void onResponse(Call<MovieItem> call, Response<MovieItem> response) {


                movieItem = response.body();

                titleTV.setText(movieItem.getTitle());
                ratingTV.setText(Float.toString(movieItem.getRating()));
                releaseTV.setText(movieItem.getReleaseDate());

                // picasso kütüphanesi kullanarak ilgili resmi aldıktan sonra imageview e set ettik
                Picasso.get().load(Constants.MOVIEDB_LARGE_POSTER_URL + photo).into(posterIV);

                boolean movieFav = databaseObj.controlMovie(movieItem.getTitle());
                if (movieFav) {
                    favBTN.setText("REMOVE FROM FAVOURITES");
                }

            }

            @Override
            public void onFailure(Call<MovieItem> call, Throwable t) {
                t.printStackTrace();

            }
        });
    }


    private Call<MovieItem> callResultsApi() {
        return getServiceI.getMovieDetails(
                this.movieId,
                Constants.MOVIEDB_API_KEY
        );
    }


}