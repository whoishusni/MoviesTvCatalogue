package id.husni.consumerapp.database;

import android.net.Uri;
import android.provider.BaseColumns;

public class DatabaseContract implements BaseColumns {
    public static final String MOVIES_FAVE_TABLE_NAME = "moviesfave";
    public static final String SERIES_FAVE_TABLE_NAME = "seriesfave";
    public static final String TITLE = "title";
    public static final String RATING = "rating";
    public static final String OVERVIEW = "overview";
    public static final String POSTER = "poster";
    public static final String RELEASE_DATE = "releasedate";

    private static final String CATALOGUE_SCHEME = "content" ;
    public static final String CATALOGUE_AUTHORITY = "id.husni.moviestvcatalogue";

    public static final Uri URI_MOVIES = new Uri.Builder()
            .scheme(CATALOGUE_SCHEME)
            .authority(CATALOGUE_AUTHORITY)
            .appendPath(MOVIES_FAVE_TABLE_NAME)
            .build();

    public static final Uri URI_SERIES = new Uri.Builder()
            .scheme(CATALOGUE_SCHEME)
            .authority(CATALOGUE_AUTHORITY)
            .appendPath(SERIES_FAVE_TABLE_NAME)
            .build();
}
