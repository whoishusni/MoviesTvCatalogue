package id.husni.consumerapp.database;

import android.database.Cursor;
import android.provider.BaseColumns;

import java.util.ArrayList;

import id.husni.consumerapp.model.MoviesFavorite;
import id.husni.consumerapp.model.SeriesFavorite;

import static id.husni.consumerapp.database.DatabaseContract.OVERVIEW;
import static id.husni.consumerapp.database.DatabaseContract.POSTER;
import static id.husni.consumerapp.database.DatabaseContract.RATING;
import static id.husni.consumerapp.database.DatabaseContract.RELEASE_DATE;
import static id.husni.consumerapp.database.DatabaseContract.TITLE;

public class MappingHelper {
    public static ArrayList<MoviesFavorite> mapMoviesArrayToCursor(Cursor cursor) {
        ArrayList<MoviesFavorite> moviesFavorites = new ArrayList<>();

        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndexOrThrow(BaseColumns._ID));
            String title = cursor.getString(cursor.getColumnIndexOrThrow(TITLE));
            String overview = cursor.getString(cursor.getColumnIndexOrThrow(OVERVIEW));
            String releaseDate = cursor.getString(cursor.getColumnIndexOrThrow(RELEASE_DATE));
            String poster = cursor.getString(cursor.getColumnIndexOrThrow(POSTER));
            String rating = cursor.getString(cursor.getColumnIndexOrThrow(RATING));
            moviesFavorites.add(new MoviesFavorite(id,title,overview,releaseDate,poster,rating));
        }
        return moviesFavorites;
    }

    public static ArrayList<SeriesFavorite> mapSeriesArrayToCursor(Cursor cursor) {
        ArrayList<SeriesFavorite> seriesFavorites = new ArrayList<>();
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndexOrThrow(BaseColumns._ID));
            String title = cursor.getString(cursor.getColumnIndexOrThrow(TITLE));
            String overview = cursor.getString(cursor.getColumnIndexOrThrow(OVERVIEW));
            String releaseDate = cursor.getString(cursor.getColumnIndexOrThrow(RELEASE_DATE));
            String poster = cursor.getString(cursor.getColumnIndexOrThrow(POSTER));
            String rating = cursor.getString(cursor.getColumnIndexOrThrow(RATING));
            seriesFavorites.add(new SeriesFavorite(id,title,overview,releaseDate,poster,rating));
        }
        return seriesFavorites;
    }
}
