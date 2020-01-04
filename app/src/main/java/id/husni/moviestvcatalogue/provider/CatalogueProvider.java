package id.husni.moviestvcatalogue.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;

import id.husni.moviestvcatalogue.database.table.MoviesHelper;
import id.husni.moviestvcatalogue.database.table.SeriesHelper;

import static id.husni.moviestvcatalogue.database.DatabaseContract.CATALOGUE_AUTHORITY;
import static id.husni.moviestvcatalogue.database.DatabaseContract.MOVIES_FAVE_TABLE_NAME;
import static id.husni.moviestvcatalogue.database.DatabaseContract.SERIES_FAVE_TABLE_NAME;
import static id.husni.moviestvcatalogue.database.DatabaseContract.URI_MOVIES;
import static id.husni.moviestvcatalogue.database.DatabaseContract.URI_SERIES;

public class CatalogueProvider extends ContentProvider {
    private static final int MOVIES = 1;
    private static final int MOVIES_WITH_ID = 2;
    private MoviesHelper moviesHelper;

    private static final int SERIES = 3;
    private  static final int SERIES_WITH_ID = 4;
    private SeriesHelper seriesHelper;

    private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        uriMatcher.addURI(CATALOGUE_AUTHORITY, MOVIES_FAVE_TABLE_NAME, MOVIES);
        uriMatcher.addURI(CATALOGUE_AUTHORITY, MOVIES_FAVE_TABLE_NAME + "/#", MOVIES_WITH_ID);
        uriMatcher.addURI(CATALOGUE_AUTHORITY,SERIES_FAVE_TABLE_NAME,SERIES);
        uriMatcher.addURI(CATALOGUE_AUTHORITY, SERIES_FAVE_TABLE_NAME + "/#", SERIES_WITH_ID);
    }

    public CatalogueProvider() {

    }

    @Override
    public boolean onCreate() {
        moviesHelper = MoviesHelper.getInstance(getContext());
        seriesHelper = SeriesHelper.getInstance(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        Cursor cursor;
        switch (uriMatcher.match(uri)) {
            case MOVIES:
                moviesHelper.open();
                cursor = moviesHelper.getAllMoviesFavorite();
                break;
            case MOVIES_WITH_ID:
                moviesHelper.open();
                cursor = moviesHelper.getAllMoviesById(uri.getLastPathSegment());
                break;
            case SERIES:
                seriesHelper.open();
                cursor = seriesHelper.getSeriesFavoriteData();
                break;
            case SERIES_WITH_ID:
                seriesHelper.open();
                cursor = seriesHelper.getSeriesDataById(uri.getLastPathSegment());
                break;
                default:
                    cursor = null;
                    break;
        }
        return cursor;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        Uri uri1 = null;
        long added;
        switch (uriMatcher.match(uri)) {
            case MOVIES:
                moviesHelper.open();
                added = moviesHelper.insert(values);
                getContext().getContentResolver().notifyChange(URI_MOVIES, null);
                uri1 =  Uri.parse(URI_MOVIES + "/" + added);
                break;
            case SERIES:
                seriesHelper.open();
                added = seriesHelper.insertData(values);
                getContext().getContentResolver().notifyChange(URI_SERIES, null);
                uri1 = Uri.parse(URI_SERIES + "/" + added);
                break;
                default:
        }
        return uri1;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int deleted;
        switch (uriMatcher.match(uri)) {
            case MOVIES_WITH_ID:
                moviesHelper.open();
                deleted = moviesHelper.delete(uri.getLastPathSegment());
                getContext().getContentResolver().notifyChange(URI_MOVIES, null);
                break;
            case SERIES_WITH_ID:
                seriesHelper.open();
                deleted = seriesHelper.deleteData(uri.getLastPathSegment());
                getContext().getContentResolver().notifyChange(URI_SERIES, null);
                break;
                default:
                    deleted = 0;
                    break;
        }
        return deleted;
    }
}
