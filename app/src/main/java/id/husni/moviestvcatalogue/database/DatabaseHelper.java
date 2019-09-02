package id.husni.moviestvcatalogue.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import static id.husni.moviestvcatalogue.database.DatabaseContract._ID;
import static id.husni.moviestvcatalogue.database.DatabaseContract.TITLE;
import static id.husni.moviestvcatalogue.database.DatabaseContract.RATING;
import static id.husni.moviestvcatalogue.database.DatabaseContract.OVERVIEW;
import static id.husni.moviestvcatalogue.database.DatabaseContract.POSTER;
import static id.husni.moviestvcatalogue.database.DatabaseContract.RELEASE_DATE;
import static id.husni.moviestvcatalogue.database.DatabaseContract.MOVIES_FAVE_TABLE_NAME;
import static id.husni.moviestvcatalogue.database.DatabaseContract.SERIES_FAVE_TABLE_NAME;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "moviecatalogue";
    private static final int DATABASE_VERSION = 1;

    private static final String CREATE_TABLE_MOVIES = String.format("CREATE TABLE %s " +
                    "(%s INTEGER PRIMARY KEY," +
                    "%s TEXT NOT NULL," +
                    "%s TEXT NOT NULL," +
                    "%s TEXT NOT NULL," +
                    "%s TEXT NOT NULL," +
                    "%s TEXT NOT NULL);",
            MOVIES_FAVE_TABLE_NAME,
            _ID,
            TITLE,
            RATING,
            OVERVIEW,
            POSTER,
            RELEASE_DATE);

    private static final String CREATE_TABLE_SERIES = String.format("CREATE TABLE %s " +
                    "(%s INTEGER PRIMARY KEY," +
                    "%s TEXT NOT NULL," +
                    "%s TEXT NOT NULL," +
                    "%s TEXT NOT NULL," +
                    "%s TEXT NOT NULL," +
                    "%s TEXT NOT NULL);",
            SERIES_FAVE_TABLE_NAME,
            _ID,
            TITLE,
            RATING,
            OVERVIEW,
            POSTER,
            RELEASE_DATE);

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_MOVIES);
        db.execSQL(CREATE_TABLE_SERIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + CREATE_TABLE_MOVIES);
        db.execSQL("DROP TABLE IF EXISTS " + CREATE_TABLE_SERIES);
    }
}
