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
import id.husni.moviestvcatalogue.adapter.favorite.MoviesFavoriteAdapter;
import id.husni.moviestvcatalogue.callback.MoviesCallback;
import id.husni.moviestvcatalogue.database.MappingHelper;
import id.husni.moviestvcatalogue.model.favorite.MoviesFavorite;

import static id.husni.moviestvcatalogue.database.DatabaseContract.URI_MOVIES;


/**
 * A simple {@link Fragment} subclass.
 */
public class MoviesFavoriteFragment extends Fragment implements MoviesCallback {

    private MoviesFavoriteAdapter adapter;

    public MoviesFavoriteFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movies_favorite, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        adapter = new MoviesFavoriteAdapter(getContext());
        RecyclerView recyclerView = view.findViewById(R.id.recyclerMoviesFavorite);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        HandlerThread handlerThread = new HandlerThread("MoviesObserver");
        handlerThread.start();
        Handler handler = new Handler(handlerThread.getLooper());

        MoviesObserver moviesObserver = new MoviesObserver(handler, getContext());
        getContext().getContentResolver().registerContentObserver(URI_MOVIES, true, moviesObserver);

        if (savedInstanceState == null) {
            new MyAsyncData(getContext(), this).execute();
        } else {
            ArrayList<MoviesFavorite> moviesFaveList = new ArrayList<>();
            if (moviesFaveList != null) {
                adapter.setMoviesFavoriteArrayList(moviesFaveList);
            }
        }
    }

    @Override
    public void onPostExecute(ArrayList<MoviesFavorite> moviesFavorites) {
        adapter.setMoviesFavoriteArrayList(moviesFavorites);
    }

    private static class MyAsyncData extends AsyncTask<Void,Void, ArrayList<MoviesFavorite>>{
        WeakReference<Context> contextWeakReference;
        WeakReference<MoviesCallback> callbackWeakReference;

        MyAsyncData(Context context, MoviesCallback callback) {
            contextWeakReference = new WeakReference<>(context);
            callbackWeakReference = new WeakReference<>(callback);
        }

        @Override
        protected ArrayList<MoviesFavorite> doInBackground(Void... voids) {
            Context context = contextWeakReference.get();
            Cursor cursor = context.getContentResolver().query(URI_MOVIES, null, null, null, null);
            return MappingHelper.mapMoviesArrayToCursor(cursor);

        }

        @Override
        protected void onPostExecute(ArrayList<MoviesFavorite> moviesFavorites) {
            super.onPostExecute(moviesFavorites);
            callbackWeakReference.get().onPostExecute(moviesFavorites);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private class MoviesObserver extends ContentObserver {
        Context context;
        public MoviesObserver(Handler handler, Context context) {
            super(handler);
            this.context = context;
        }
    }
}
