package id.husni.moviestvcatalogue.fragment.favorite;


import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.HandlerThread;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import id.husni.moviestvcatalogue.R;
import id.husni.moviestvcatalogue.adapter.favorite.SeriesFavoriteAdapter;
import id.husni.moviestvcatalogue.callback.SeriesCallback;
import id.husni.moviestvcatalogue.database.MappingHelper;
import id.husni.moviestvcatalogue.model.favorite.SeriesFavorite;

import static id.husni.moviestvcatalogue.database.DatabaseContract.URI_SERIES;


/**
 * A simple {@link Fragment} subclass.
 */
public class SeriesFavoriteFragment extends Fragment implements SeriesCallback {

    private SeriesFavoriteAdapter adapter;

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

        HandlerThread handlerThread = new HandlerThread("SeriesObserver");
        handlerThread.start();
        Handler handler = new Handler(handlerThread.getLooper());

        SeriesObserver seriesObserver = new SeriesObserver(handler, getContext());
        getContext().getContentResolver().registerContentObserver(URI_SERIES,true,seriesObserver);

        if (savedInstanceState == null) {
            new MyAsyncData(getContext(), this).execute();
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
        WeakReference<Context> contextWeakReference;
        WeakReference<SeriesCallback> callbackWeakReference;

        MyAsyncData(Context context, SeriesCallback callback) {
            contextWeakReference = new WeakReference<>(context);
            callbackWeakReference = new WeakReference<>(callback);
        }

        @Override
        protected ArrayList<SeriesFavorite> doInBackground(Void... voids) {
            Context context = contextWeakReference.get();
            Cursor cursor = context.getContentResolver().query(URI_SERIES, null, null, null, null);
            return MappingHelper.mapSeriesArrayToCursor(cursor);
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
    }

    private class SeriesObserver extends ContentObserver {
        Context context;
        public SeriesObserver(Handler handler, Context context) {
            super(handler);
            this.context = context;
        }
    }
}
