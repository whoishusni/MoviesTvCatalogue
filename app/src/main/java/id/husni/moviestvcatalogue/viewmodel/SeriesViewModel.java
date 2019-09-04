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

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;
import id.husni.moviestvcatalogue.model.Series;
import id.husni.moviestvcatalogue.utilities.AppUtilities;

public class SeriesViewModel extends ViewModel {
    private MutableLiveData<ArrayList<Series>> mutableLiveData = new MutableLiveData<>();

    public void setMoviesData() {
        final ArrayList<Series> seriesArrayList = new ArrayList<>();
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(AppUtilities.URL_TV_TMDB, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String result = new String(responseBody);
                try {
                    JSONObject object = new JSONObject(result);
                    JSONArray array = object.getJSONArray("results");

                    for (int i = 0; i < array.length(); i++) {
                        JSONObject objectSeries = array.getJSONObject(i);
                        Series series = new Series(objectSeries);
                        seriesArrayList.add(series);
                        Log.d(AppUtilities.TAG, "onSuccess: catch Series Data");
                    }
                    mutableLiveData.postValue(seriesArrayList);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
    }

    public LiveData<ArrayList<Series>> getSeriesData() {
        return mutableLiveData;
    }
}
