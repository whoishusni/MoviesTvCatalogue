package id.husni.moviestvcatalogue.callback;

import java.util.ArrayList;

import id.husni.moviestvcatalogue.model.favorite.SeriesFavorite;

public interface SeriesCallback {
    void onPostExecute(ArrayList<SeriesFavorite> seriesFavorites);
}
