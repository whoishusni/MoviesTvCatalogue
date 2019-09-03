package id.husni.moviestvcatalogue.database.table;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import id.husni.moviestvcatalogue.database.DatabaseHelper;
import id.husni.moviestvcatalogue.model.favorite.MoviesFavorite;

import static id.husni.moviestvcatalogue.database.DatabaseContract.MOVIES_FAVE_TABLE_NAME;
import static id.husni.moviestvcatalogue.database.DatabaseContract.OVERVIEW;
import static id.husni.moviestvcatalogue.database.DatabaseContract.POSTER;
import static id.husni.moviestvcatalogue.database.DatabaseContract.RATING;
import static id.husni.moviestvcatalogue.database.DatabaseContract.RELEASE_DATE;
import static id.husni.moviestvcatalogue.database.DatabaseContract.TITLE;
import static id.husni.moviestvcatalogue.database.DatabaseContract._ID;

public class MoviesHelper {

    private static MoviesHelper INSTANCE;
    DatabaseHelper helper;
    SQLiteDatabase database;

    public MoviesHelper(Context context) {
        helper = new DatabaseHelper(context);
    }

    public static MoviesHelper getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (SQLiteOpenHelper.class) {
                if (INSTANCE == null) {
                    INSTANCE = new MoviesHelper(context);
                }
            }
        }
        return INSTANCE;
    }

    public void open()throws SQLException {
        database = helper.getWritableDatabase();
    }

    public void close() {
        if (database.isOpen()) {
            database.close();
        }
    }

    public ArrayList<MoviesFavorite> getAllMoviesFavorite() {
        ArrayList<MoviesFavorite> moviesFavoritesArray = new ArrayList<>();
        Cursor cursor = database.query(MOVIES_FAVE_TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                _ID + " ASC",
                null);
        cursor.moveToFirst();
        MoviesFavorite moviesFavorite;
        do {
            if (cursor.getCount() > 0) {
                moviesFavorite = new MoviesFavorite();
                moviesFavorite.setId(cursor.getInt(cursor.getColumnIndexOrThrow(_ID)));
                moviesFavorite.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(TITLE)));
                moviesFavorite.setVoteAverage(cursor.getString(cursor.getColumnIndexOrThrow(RATING)));
                moviesFavorite.setOverview(cursor.getString(cursor.getColumnIndexOrThrow(OVERVIEW)));
                moviesFavorite.setPosterPath(cursor.getString(cursor.getColumnIndexOrThrow(POSTER)));
                moviesFavorite.setReleaseDate(cursor.getString(cursor.getColumnIndexOrThrow(RELEASE_DATE)));
                cursor.moveToNext();
                moviesFavoritesArray.add(moviesFavorite);
            }
        } while (!cursor.isAfterLast());
        cursor.close();
        return moviesFavoritesArray;
    }

    public long insert(MoviesFavorite moviesFavorite) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(TITLE, moviesFavorite.getTitle());
        contentValues.put(RATING, moviesFavorite.getVoteAverage());
        contentValues.put(OVERVIEW, moviesFavorite.getOverview());
        contentValues.put(POSTER, moviesFavorite.getPosterPath());
        contentValues.put(RELEASE_DATE, moviesFavorite.getReleaseDate());
        return database.insert(MOVIES_FAVE_TABLE_NAME, null, contentValues);
    }

    public int delete(int position) {
        return database.delete(MOVIES_FAVE_TABLE_NAME, _ID + "= '" + position + "'",null);
    }
}
