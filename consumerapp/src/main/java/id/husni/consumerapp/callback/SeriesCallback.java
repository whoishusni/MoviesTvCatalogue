package id.husni.consumerapp.callback;

import java.util.ArrayList;

import id.husni.consumerapp.model.SeriesFavorite;

public interface SeriesCallback {
    void onPostExecute(ArrayList<SeriesFavorite> seriesFavorites);
}
