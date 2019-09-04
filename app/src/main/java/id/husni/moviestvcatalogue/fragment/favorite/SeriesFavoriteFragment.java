package id.husni.moviestvcatalogue.fragment.favorite;


import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import id.husni.moviestvcatalogue.R;
import id.husni.moviestvcatalogue.adapter.favorite.SeriesFavoriteAdapter;
import id.husni.moviestvcatalogue.callback.SeriesCallback;
import id.husni.moviestvcatalogue.database.table.SeriesHelper;
import id.husni.moviestvcatalogue.model.favorite.SeriesFavorite;
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;


/**
 * A simple {@link Fragment} subclass.
 */
public class SeriesFavoriteFragment extends Fragment implements SeriesCallback {

    SeriesHelper helper;
    SeriesFavorite seriesFavorite;
    SeriesFavoriteAdapter adapter;

    public SeriesFavoriteFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_series_favorite, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerSeriesFavorite);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new SeriesFavoriteAdapter(getContext());
        recyclerView.setAdapter(adapter);

        helper = SeriesHelper.getInstance(getContext());
        helper.open();

        if (savedInstanceState == null) {
            new MyAsyncData(helper, this).execute();
        } else {
            ArrayList<SeriesFavorite> seriesFavoritesList = new ArrayList<>();
            if (seriesFavoritesList != null) {
                adapter.setSeriesFavoriteArrayList(seriesFavoritesList);
            }
        }
    }

    @Override
    public void onPostExecute(ArrayList<SeriesFavorite> seriesFavorites) {
        adapter.setSeriesFavoriteArrayList(seriesFavorites);
    }

    private static class MyAsyncData extends AsyncTask<Void, Void, ArrayList<SeriesFavorite>> {
        WeakReference<SeriesHelper> helperWeakReference;
        WeakReference<SeriesCallback> callbackWeakReference;

        public MyAsyncData(SeriesHelper helper, SeriesCallback callback) {
            helperWeakReference = new WeakReference<>(helper);
            callbackWeakReference = new WeakReference<>(callback);
        }

        @Override
        protected ArrayList<SeriesFavorite> doInBackground(Void... voids) {
            return helperWeakReference.get().getSeriesFavoriteData();
        }

        @Override
        protected void onPostExecute(ArrayList<SeriesFavorite> seriesFavorites) {
            super.onPostExecute(seriesFavorites);
            callbackWeakReference.get().onPostExecute(seriesFavorites);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        helper.close();
    }
}
