package id.husni.moviestvcatalogue.callback;

import java.util.ArrayList;

import id.husni.moviestvcatalogue.model.favorite.MoviesFavorite;

public interface MoviesCallback {
    void onPostExecute(ArrayList<MoviesFavorite> moviesFavorites);
}
