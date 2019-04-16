package com.burhangok.listmovies.ui;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.burhangok.listmovies.R;
import com.burhangok.listmovies.helpers.ChangeFragment;
import com.burhangok.listmovies.models.MoviesResponse;

public class MainActivity extends AppCompatActivity {

    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

    MoviesFragment moviesFragment;
    Bundle bundleData;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.top_rated:
                    Bundle bundleData1 = new Bundle();
                    MoviesFragment moviesFragment1 = new MoviesFragment();
                    bundleData1.putString("type", "top_rated");
                    moviesFragment1.setArguments(bundleData1);
                    changeFragment(moviesFragment1);
                    return true;
                case R.id.upcoming:
                    Bundle bundleData2 = new Bundle();
                    MoviesFragment moviesFragment2 = new MoviesFragment();
                    bundleData2.putString("type", "upcoming");
                    moviesFragment2.setArguments(bundleData2);
                    changeFragment(moviesFragment2);
                    return true;
                case R.id.now_playing:
                    Bundle bundleData3 = new Bundle();
                    MoviesFragment moviesFragment3 = new MoviesFragment();
                    bundleData3.putString("type", "now_playing");
                    moviesFragment3.setArguments(bundleData3);
                    changeFragment(moviesFragment3);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        moviesFragment = new MoviesFragment();
        bundleData = new Bundle();
        bundleData.putString("type", "top_rated");

        moviesFragment.setArguments(bundleData);
        changeFragment(moviesFragment);

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


    }


    private void changeFragment(Fragment fragment) {
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.replace(R.id.fragment_area, fragment);
        fragmentTransaction.commit();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.dashboard, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);

        SearchManager searchManager = (SearchManager) MainActivity.this.getSystemService(Context.SEARCH_SERVICE);

        SearchView searchView = null;
        if (searchItem != null) {
            searchView = (SearchView) searchItem.getActionView();
        }
        if (searchView != null) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(MainActivity.this.getComponentName()));
        }

        SearchView.OnQueryTextListener queryTextListener = new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {

                SearchMoviesFragment searchMoviesFragment = new SearchMoviesFragment();

                Bundle bundle = new Bundle();
                bundle.putString("query", s);

                searchMoviesFragment.setArguments(bundle);

                changeFragment(searchMoviesFragment);

                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {


                return true;
            }
        };

        searchView.setOnQueryTextListener(queryTextListener);

        return super.onCreateOptionsMenu(menu);
    }
}
