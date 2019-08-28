package id.husni.moviestvcatalogue.utilities;

import id.husni.moviestvcatalogue.BuildConfig;

public class AppUtilities {
    public static final String URL_MOVIES_TMDB = "https://api.themoviedb.org/3/discover/movie?api_key=" + BuildConfig.API_TMDB + "&language=en-US";
    public static final String URL_TV_TMDB = "https://api.themoviedb.org/3/discover/tv?api_key=" + BuildConfig.API_TMDB + "&language=en-US";
    public static final String POSTER_FILM = "https://image.tmdb.org/t/p/w185/";
    public static final String POSTER_FILM_DETAIL = "https://image.tmdb.org/t/p/w500/";
    public static final String TAG = "tag";

    /*pencarian film.
    Movies: https://api.themoviedb.org/3/search/movie?api_key={API KEY}&language=en-US&query={MOVIE NAME}
    Tv Show: https://api.themoviedb.org/3/search/tv?api_key={API KEY}&language=en-US&query={TV SHOW NAME}
    Contoh: https://api.themoviedb.org/3/search/movie?api_key=123456789&language=en-US&query=Avenger*/

    /*film yang rilis pada tanggal hari ini.
    Movies release: https://api.themoviedb.org/3/discover/movie?api_key={API KEY}&primary_release_date.gte={TODAY DATE}&primary_release_date.lte={TODAY DATE}
    Contoh: https://api.themoviedb.org/3/discover/movie?api_key=123456789&primary_release_date.gte=2019-01-31&primary_release_date.lte=2019-01-31
    Catatan: Pastikan format tanggal yang kalian gunakan benar. Format tanggal yang digunakan adalah "yyyy-MM-dd".*/
}
