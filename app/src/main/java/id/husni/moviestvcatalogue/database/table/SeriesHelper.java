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

import static android.provider.BaseColumns._COUNT;
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

    public Cursor getSeriesFavoriteData() {
        return database.query(SERIES_FAVE_TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                _ID + " ASC");
    }

    public Cursor getSeriesDataById(String id) {
        return database.query(SERIES_FAVE_TABLE_NAME,
                null,
                _ID+"= ?",new String[]{id},
                null,
                null,
                null,
                null);
    }

    public long insertData(ContentValues contentValues) {
        return database.insert(SERIES_FAVE_TABLE_NAME, null, contentValues);
    }

    public int deleteData(String id) {
        return database.delete(SERIES_FAVE_TABLE_NAME, _ID + "= ?", new String[]{id});
    }
}
