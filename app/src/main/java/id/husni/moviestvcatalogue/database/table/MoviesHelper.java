package id.husni.moviestvcatalogue.database.table;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import id.husni.moviestvcatalogue.database.DatabaseHelper;

import static id.husni.moviestvcatalogue.database.DatabaseContract.MOVIES_FAVE_TABLE_NAME;
import static id.husni.moviestvcatalogue.database.DatabaseContract._ID;

public class MoviesHelper {

    private static MoviesHelper INSTANCE;
    private DatabaseHelper helper;
    private SQLiteDatabase database;

    private MoviesHelper(Context context) {
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

    public Cursor getAllMoviesFavorite() {
        return database.query(MOVIES_FAVE_TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                _ID + " ASC");
    }

    public Cursor getAllMoviesById(String id) {
        return database.query(MOVIES_FAVE_TABLE_NAME,
                null,
                _ID+"= ?", new String[]{id},
                null,
                null,
                null,
                null);
    }

    public long insert(ContentValues values) {
        return database.insert(MOVIES_FAVE_TABLE_NAME, null, values);
    }

    public int delete(String id) {
        return database.delete(MOVIES_FAVE_TABLE_NAME, _ID + "= ?", new String[]{id});
    }
}
