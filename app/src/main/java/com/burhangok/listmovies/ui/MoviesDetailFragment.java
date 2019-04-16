package com.burhangok.listmovies.ui;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.burhangok.listmovies.R;
import com.burhangok.listmovies.adapters.MoviesAdapter;
import com.burhangok.listmovies.common.Constants;
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

    TextView titleTV,releaseTV,ratingTV;
    ImageView posterIV;
    Button favBTN;

    String movieId;

    getMovieDetailsInterface getServiceI;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentView = inflater.inflate(R.layout.fragment_movies_detail, container, false);

        init();

        getDetails();

        return fragmentView;
    }


    private void init() {
        titleTV =fragmentView.findViewById(R.id.title);
        releaseTV=fragmentView.findViewById(R.id.releasedate);
        ratingTV=fragmentView.findViewById(R.id.userrating);
        posterIV=fragmentView.findViewById(R.id.photo);
        favBTN=fragmentView.findViewById(R.id.favBtn);

        movieId =getArguments().getString("id");

        getServiceI = RetrofitConfig.getClient().create(getMovieDetailsInterface.class);


    }


    private void getDetails() {

        callResultsApi().enqueue(new Callback<MoviesResponse>() {


            @Override
            public void onResponse(Call<MoviesResponse> call, Response<MoviesResponse> response) {


                List<MovieItem> results = (List<MovieItem>) response.body();

                MovieItem movieItem =results.get(0);
                titleTV.setText(movieItem.getTitle());
                ratingTV.setText(Float.toString(movieItem.getRating()));
                releaseTV.setText(movieItem.getReleaseDate());

                // picasso kütüphanesi kullanarak ilgili resmi aldıktan sonra imageview e set ettik
                Picasso.get().load(Constants.MOVIEDB_LARGE_POSTER_URL + movieItem.getPosterPath()).into(posterIV);


            }

            @Override
            public void onFailure(Call<MoviesResponse> call, Throwable t) {
                t.printStackTrace();

            }
        });
    }

    private List<MovieItem> fetchResults(Response<MoviesResponse> response) {
        MoviesResponse result = response.body();
        return result.getMovies();
    }

    private Call<MoviesResponse> callResultsApi() {
        return getServiceI.getMovieDetails(
                this.movieId,
                Constants.MOVIEDB_API_KEY
        );
    }


}