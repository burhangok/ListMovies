package com.burhangok.listmovies.databases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.burhangok.listmovies.models.MovieItem;

import java.util.ArrayList;
import java.util.List;

public class LocalDatabaseConfig extends SQLiteOpenHelper {

    public static final String DB_NAME = "listmovies";
    public static final String TABLE_NAME_1 = "favourites";

    public LocalDatabaseConfig(Context context) {
        super(context, DB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {


        String sqlCumlesi = "CREATE TABLE " + TABLE_NAME_1 + " " +
                "(movie_id INTEGER PRIMARY KEY,movie_title TEXT,movie_poster TEXT,movie_releasedate TEXT,movie_rating TEXT" + ")";


        db.execSQL(sqlCumlesi);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


    public void savefavMovie(MovieItem movieItem) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("movie_title", movieItem.getTitle());
        values.put("movie_poster", movieItem.getPosterPath());
        values.put("movie_releasedate", movieItem.getReleaseDate());
        values.put("movie_rating", Float.toString(movieItem.getRating()));

        db.insert(TABLE_NAME_1, null, values);
        db.close();

    }


    public List<MovieItem> getfavMovies() {

        List<MovieItem> movieItemArrayList = new ArrayList<MovieItem>();

        SQLiteDatabase database = this.getWritableDatabase();

        String sqlCumlesi = "SELECT movie_title , movie_poster, movie_releasedate, movie_rating FROM " + TABLE_NAME_1;
        Cursor cursor = database.rawQuery(sqlCumlesi, null);


        while (cursor.moveToNext()) {

            MovieItem movieItem = new MovieItem();
            movieItem.setTitle(cursor.getString(0));
            movieItem.setPosterPath(cursor.getString(1));
            movieItem.setReleaseDate(cursor.getString(2));
            movieItem.setRating(Float.parseFloat(cursor.getString(3)));
            movieItemArrayList.add(movieItem);
        }

        return movieItemArrayList;

    }


    public boolean controlMovie(String movieTitle) {

        SQLiteDatabase database = this.getWritableDatabase();

        Cursor cursor = null;
        String movieTitle1 = "";
        try {
            cursor = database.rawQuery("SELECT movie_title FROM " + TABLE_NAME_1 + " WHERE movie_title=?", new String[]{movieTitle + ""});
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                movieTitle1 = cursor.getString(cursor.getColumnIndex("movie_title"));

                return true;
            }


            return false;
        } finally {
            cursor.close();
            database.close();
        }
    }


    public void deleteMovie(String movieTitle) {
        SQLiteDatabase vt = this.getWritableDatabase();
        String sqlQuery1 = "DELETE FROM " + TABLE_NAME_1 + " WHERE movie_title='" + movieTitle + "'";
        vt.execSQL(sqlQuery1);

    }


}