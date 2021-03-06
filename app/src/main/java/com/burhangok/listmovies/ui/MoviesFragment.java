package com.burhangok.listmovies.ui;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.burhangok.listmovies.R;
import com.burhangok.listmovies.adapters.MoviesAdapter;
import com.burhangok.listmovies.common.Constants;
import com.burhangok.listmovies.databases.RetrofitConfig;
import com.burhangok.listmovies.models.MovieItem;
import com.burhangok.listmovies.models.MoviesResponse;
import com.burhangok.listmovies.services.getMoviesInterface;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class MoviesFragment extends Fragment {


    public MoviesFragment() {
        // Required empty public constructor
    }

    View fragmentView;
    getMoviesInterface getServiceI;

    RecyclerView moviesRV;
    MoviesAdapter adapter;
    LinearLayoutManager mLayoutManager;

    String movieType;
    int pageNumber=1;

    List<MovieItem> results;

     boolean loading = true;
    int pastVisiblesItems, visibleItemCount, totalItemCount;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        fragmentView = inflater.inflate(R.layout.fragment_movies, null);
        movieType = getArguments().getString("type");
        init();
        getMovies();

        final List<MovieItem> newList = new ArrayList<>();

        moviesRV.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if(dy > 0)
                {
                    visibleItemCount = mLayoutManager.getChildCount();
                    totalItemCount = mLayoutManager.getItemCount();
                    pastVisiblesItems = mLayoutManager.findFirstVisibleItemPosition();

                    if (loading)
                    {
                        if ( (visibleItemCount + pastVisiblesItems) >= totalItemCount)
                        {
                            loading = false;
                            pageNumber++;
                            getMovies();

                            int currentSize = adapter.getItemCount();
                            results.addAll(newList);
                            adapter.notifyItemRangeInserted(currentSize, results.size() - 2);

                        }
                    }
                }

            }
        });


        return fragmentView;
    }

    private void getMovies() {

        callResultsApi().enqueue(new Callback<MoviesResponse>() {


            @Override
            public void onResponse(Call<MoviesResponse> call, Response<MoviesResponse> response) {


               results = fetchResults(response);


                adapter = new MoviesAdapter(getContext(), results);


                // recyclerView.setItemAnimator(new DefaultItemAnimator());
                moviesRV.setAdapter(adapter);

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
        return getServiceI.getTopRatedMovies(
                this.movieType,
                Constants.MOVIEDB_API_KEY,
                Constants.MOVIEDB_LANGUAGE,
                this.pageNumber

        );
    }


    public void init() {

        moviesRV = fragmentView.findViewById(R.id.moviesList);
        mLayoutManager = new LinearLayoutManager(getContext());
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        moviesRV.setLayoutManager(mLayoutManager);

        moviesRV.setHasFixedSize(true);

        getServiceI = RetrofitConfig.getClient().create(getMoviesInterface.class);
    }
}
