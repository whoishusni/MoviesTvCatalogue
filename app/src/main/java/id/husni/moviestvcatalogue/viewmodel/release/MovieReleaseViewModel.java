package id.husni.moviestvcatalogue.viewmodel.release;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import cz.msebera.android.httpclient.Header;
import id.husni.moviestvcatalogue.BuildConfig;
import id.husni.moviestvcatalogue.model.release.MovieRelease;
import id.husni.moviestvcatalogue.utilities.AppUtilities;

public class MovieReleaseViewModel extends ViewModel {
    private MutableLiveData<ArrayList<MovieRelease>> mutableLiveData = new MutableLiveData<>();

    public void setReleaseData() {
        final ArrayList<MovieRelease> movieReleaseArrayList = new ArrayList<>();
        AsyncHttpClient client = new AsyncHttpClient();
        String URL = "https://api.themoviedb.org/3/discover/movie?api_key=" + BuildConfig.API_TMDB + "&primary_release_date.gte=" + getFormattedDate() + "&primary_release_date.lte=" + getFormattedDate();
        client.get(URL, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String result = new String(responseBody);
                    JSONObject object = new JSONObject(result);
                    JSONArray array = object.getJSONArray("results");

                    for (int i = 0; i < array.length(); i++) {
                        JSONObject jsonObject = array.getJSONObject(i);
                        MovieRelease movieRelease = new MovieRelease(jsonObject);
                        movieReleaseArrayList.add(movieRelease);
                    }
                    mutableLiveData.postValue(movieReleaseArrayList);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d(AppUtilities.TAG, "onFailure: can get release movie");
            }
        });
    }

    public LiveData<ArrayList<MovieRelease>> getReleaseData() {
        return mutableLiveData;
    }

    private String getFormattedDate() {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        return sdf.format(date);
    }


}
