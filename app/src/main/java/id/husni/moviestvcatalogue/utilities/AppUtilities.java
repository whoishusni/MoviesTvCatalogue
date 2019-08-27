package id.husni.moviestvcatalogue.utilities;

import id.husni.moviestvcatalogue.BuildConfig;

public class AppUtilities {
    public static final String URL_MOVIES_TMDB = "https://api.themoviedb.org/3/discover/movie?api_key=" + BuildConfig.API_TMDB + "&language=en-US";
    public static final String URL_TV_TMDB = "https://api.themoviedb.org/3/discover/tv?api_key=" + BuildConfig.API_TMDB + "&language=en-US";
    public static final String POSTER_FILM = "https://image.tmdb.org/t/p/w185/";

}