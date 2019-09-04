package id.husni.moviestvcatalogue.database.table;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import id.husni.moviestvcatalogue.database.DatabaseHelper;
import id.husni.moviestvcatalogue.model.favorite.SeriesFavorite;

import static id.husni.moviestvcatalogue.database.DatabaseContract.SERIES_FAVE_TABLE_NAME;
import static id.husni.moviestvcatalogue.database.DatabaseContract.OVERVIEW;
import static id.husni.moviestvcatalogue.database.DatabaseContract.POSTER;
import static id.husni.moviestvcatalogue.database.DatabaseContract.RATING;
import static id.husni.moviestvcatalogue.database.DatabaseContract.RELEASE_DATE;
import static id.husni.moviestvcatalogue.database.DatabaseContract.TITLE;
import static id.husni.moviestvcatalogue.database.DatabaseContract._ID;

public class SeriesHelper {

    private static SeriesHelper INSTANCE;
    private SQLiteDatabase database;
    private DatabaseHelper helper;

    private SeriesHelper(Context context) {
        helper = new DatabaseHelper(context);
    }

    public static SeriesHelper getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (SQLiteOpenHelper.class) {
                if (INSTANCE == null) {
                    INSTANCE = new SeriesHelper(context);
                }
            }
        }
        return INSTANCE;
    }

    public void open() throws SQLException {
        database = helper.getWritableDatabase();
    }

    public void close() {
        if (database.isOpen()) {
            database.close();
        }
    }

    public ArrayList<SeriesFavorite> getSeriesFavoriteData() {
        ArrayList<SeriesFavorite> seriesFavoritesArray = new ArrayList<>();
        Cursor cursor = database.query(SERIES_FAVE_TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                _ID + " ASC",
                null);

        cursor.moveToFirst();
        SeriesFavorite seriesFavorite;
        if (cursor.getCount() > 0) {
            do {
                seriesFavorite = new SeriesFavorite();
                seriesFavorite.setId(cursor.getInt(cursor.getColumnIndexOrThrow(_ID)));
                seriesFavorite.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(TITLE)));
                seriesFavorite.setVoteAverage(cursor.getString(cursor.getColumnIndexOrThrow(RATING)));
                seriesFavorite.setAiringDate(cursor.getString(cursor.getColumnIndexOrThrow(RELEASE_DATE)));
                seriesFavorite.setOverview(cursor.getString(cursor.getColumnIndexOrThrow(OVERVIEW)));
                seriesFavorite.setPosterPath(cursor.getString(cursor.getColumnIndexOrThrow(POSTER)));
                seriesFavoritesArray.add(seriesFavorite);
                cursor.moveToNext();

            }while (!cursor.isAfterLast());
        }
        cursor.close();
        return seriesFavoritesArray;
    }

    public long insertData(SeriesFavorite seriesFavorite) {
        ContentValues values = new ContentValues();
        values.put(TITLE, seriesFavorite.getTitle());
        values.put(RATING, seriesFavorite.getVoteAverage());
        values.put(OVERVIEW, seriesFavorite.getOverview());
        values.put(POSTER, seriesFavorite.getPosterPath());
        values.put(RELEASE_DATE, seriesFavorite.getAiringDate());
        return database.insert(SERIES_FAVE_TABLE_NAME, null, values);
    }

    public int deleteData(int position) {
        return database.delete(SERIES_FAVE_TABLE_NAME, _ID + "= '" + position + "'", null);
    }
}
