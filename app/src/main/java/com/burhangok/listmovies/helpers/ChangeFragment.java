package com.burhangok.listmovies.helpers;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.burhangok.listmovies.R;

public class ChangeFragment {

    Context context;

    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;


    public ChangeFragment(Context context) {
        this.context=context;

    }


    public void change(Fragment fragment) {
        fragmentManager = ((AppCompatActivity) context).getSupportFragmentManager();
        fragmentTransaction =fragmentManager.beginTransaction();
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.replace(R.id.fragment_area,fragment);
        fragmentTransaction.commit();
    }

}
