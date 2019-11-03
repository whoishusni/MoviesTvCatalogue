package id.husni.moviestvcatalogue.utilities;

import id.husni.moviestvcatalogue.BuildConfig;

public class AppUtilities {
    public static final String URL_MOVIES_TMDB = "https://api.themoviedb.org/3/discover/movie?api_key=" + BuildConfig.API_TMDB + "&language=en-US";
    public static final String URL_MOVIES_TMDB_ID = "https://api.themoviedb.org/3/discover/movie?api_key=" + BuildConfig.API_TMDB + "&language=id-ID";
    public static final String URL_TV_TMDB = "https://api.themoviedb.org/3/discover/tv?api_key=" + BuildConfig.API_TMDB + "&language=en-US";
    public static final String URL_TV_TMDB_ID = "https://api.themoviedb.org/3/discover/tv?api_key=" + BuildConfig.API_TMDB + "&language=id-ID";
    public static final String POSTER_FILM = "https://image.tmdb.org/t/p/w185/";
    public static final String POSTER_FILM_DETAIL = "https://image.tmdb.org/t/p/w500/";
    public static final String TAG = "tag";

    public static final int ADD_REQUEST_CODE = 100;
    public static final int ADD_RESULT_CODE = 101;
    public static final int DELETE_RESULT_CODE = 200;
    public static final int DAILY_REQUEST_CODE = 300;

}
