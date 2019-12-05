package id.husni.consumerapp.callback;

import java.util.ArrayList;

import id.husni.consumerapp.model.MoviesFavorite;

public interface MoviesCallback {
    void onPostExecute(ArrayList<MoviesFavorite> moviesFavorites);
}
