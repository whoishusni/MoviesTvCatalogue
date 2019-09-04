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
import id.husni.moviestvcatalogue.adapter.favorite.MoviesFavoriteAdapter;
import id.husni.moviestvcatalogue.callback.MoviesCallback;
import id.husni.moviestvcatalogue.database.table.MoviesHelper;
import id.husni.moviestvcatalogue.model.favorite.MoviesFavorite;


/**
 * A simple {@link Fragment} subclass.
 */
public class MoviesFavoriteFragment extends Fragment implements MoviesCallback {

    private MoviesFavoriteAdapter adapter;
    private MoviesHelper helper;
    MoviesFavorite moviesFavorite;

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

        helper = MoviesHelper.getInstance(getContext());
        helper.open();

        if (savedInstanceState == null) {
            new MyAsyncData(helper, this).execute();
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
        WeakReference<MoviesHelper> helperWeakReference;
        WeakReference<MoviesCallback> callbackWeakReference;

        MyAsyncData(MoviesHelper helper, MoviesCallback callback) {
            helperWeakReference = new WeakReference<>(helper);
            callbackWeakReference = new WeakReference<>(callback);
        }

        @Override
        protected ArrayList<MoviesFavorite> doInBackground(Void... voids) {
            return helperWeakReference.get().getAllMoviesFavorite();

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
        helper.close();
    }
}
