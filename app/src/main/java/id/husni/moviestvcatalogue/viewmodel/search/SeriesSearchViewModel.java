package id.husni.moviestvcatalogue.viewmodel.search;

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
import id.husni.moviestvcatalogue.BuildConfig;
import id.husni.moviestvcatalogue.model.search.SeriesSearch;
import id.husni.moviestvcatalogue.utilities.AppUtilities;

public class SeriesSearchViewModel extends ViewModel {
    MutableLiveData<ArrayList<SeriesSearch>> mutableLiveData = new MutableLiveData<>();

    public void setData(String search) {
        final ArrayList<SeriesSearch> seriesSearches = new ArrayList<>();
        String URL = "https://api.themoviedb.org/3/search/tv?api_key="+ BuildConfig.API_TMDB +"&language=en-US&query=" + search;
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(URL, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String result = new String(responseBody);
                try {
                    JSONObject object = new JSONObject(result);
                    JSONArray jsonArray = object.getJSONArray("results");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject seriesObject = jsonArray.getJSONObject(i);
                        SeriesSearch seriesSearch = new SeriesSearch(seriesObject);
                        seriesSearches.add(seriesSearch);
                        Log.d(AppUtilities.TAG, "onSuccess: get data API");
                    }
                    mutableLiveData.postValue(seriesSearches);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d(AppUtilities.TAG, "onFailure: get DATA API");
            }
        });
    }
    public LiveData<ArrayList<SeriesSearch>> getSearchData() {
        return mutableLiveData;
    }
}
