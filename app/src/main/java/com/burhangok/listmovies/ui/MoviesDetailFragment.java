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
import com.burhangok.listmovies.services.getMovieDetailsInterface;

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

    getMovieDetailsInterface getService;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentView = inflater.inflate(R.layout.fragment_movies_detail, container, false);

        init();

        movieId =getArguments().getString("id");


        return fragmentView;
    }


    private void init() {
        titleTV =fragmentView.findViewById(R.id.title);
        releaseTV=fragmentView.findViewById(R.id.releasedate);
        ratingTV=fragmentView.findViewById(R.id.userrating);
        posterIV=fragmentView.findViewById(R.id.photo);
        favBTN=fragmentView.findViewById(R.id.favBtn);

    }

}