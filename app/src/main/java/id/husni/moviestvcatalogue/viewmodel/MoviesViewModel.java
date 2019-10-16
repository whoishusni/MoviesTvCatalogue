package id.husni.moviestvcatalogue.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.IDN;
import java.util.ArrayList;
import java.util.Locale;

import cz.msebera.android.httpclient.Header;
import id.husni.moviestvcatalogue.model.Movies;
import id.husni.moviestvcatalogue.utilities.AppUtilities;

public class MoviesViewModel extends ViewModel {

    private MutableLiveData<ArrayList<Movies>> mutableMovies = new MutableLiveData<>();

    public void setMoviesData() {
        final ArrayList<Movies> moviesArrayList = new ArrayList<>();
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(AppUtilities.URL_MOVIES_TMDB, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String result = new String(responseBody);
                    JSONObject object = new JSONObject(result);
                    JSONArray moviesArray = object.getJSONArray("results");

                    for (int i = 0; i < moviesArray.length(); i++) {
                        JSONObject moviesObject = moviesArray.getJSONObject(i);
                        Movies movies = new Movies(moviesObject);
                        moviesArrayList.add(movies);
                        Log.d(AppUtilities.TAG, "onSuccess: catch movies data view model");
                    }
                    mutableMovies.postValue(moviesArrayList);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d(AppUtilities.TAG, "onFailure: catch movies data view model ");
            }
        });

    }

    public LiveData<ArrayList<Movies>> getMoviesData() {
        return mutableMovies;
    }
}
