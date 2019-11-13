package id.husni.moviestvcatalogue.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import id.husni.moviestvcatalogue.R;
import id.husni.moviestvcatalogue.adapter.MoviesAdapter;
import id.husni.moviestvcatalogue.adapter.favorite.MoviesFavoriteAdapter;
import id.husni.moviestvcatalogue.detail.MoviesDetail;
import id.husni.moviestvcatalogue.model.Movies;
import id.husni.moviestvcatalogue.model.favorite.MoviesFavorite;
import id.husni.moviestvcatalogue.utilities.AppUtilities;
import id.husni.moviestvcatalogue.utilities.CustomClickListener;
import id.husni.moviestvcatalogue.viewmodel.MoviesViewModel;
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;

public class MoviesFragment extends Fragment {
    private MoviesAdapter adapter;
    private MoviesFavoriteAdapter favoriteAdapter;
    private MoviesViewModel model;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;

    public MoviesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_movies, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        adapter = new MoviesAdapter(getContext());
        favoriteAdapter = new MoviesFavoriteAdapter(getContext());

        progressBar = view.findViewById(R.id.progressBarMovies);

        recyclerView = view.findViewById(R.id.recyclerMovies);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        ScaleInAnimationAdapter scaleAdapter = new ScaleInAnimationAdapter(adapter);
        recyclerView.setAdapter(scaleAdapter);

        model = ViewModelProviders.of(this).get(MoviesViewModel.class);
        model.setMoviesData();
        showLoading(true);
        model.getMoviesData().observe(this, moviesObserver);

    }

    private Observer<ArrayList<Movies>> moviesObserver = new Observer<ArrayList<Movies>>() {
        @Override
        public void onChanged(final ArrayList<Movies> movies) {
            adapter.setMovies(movies);
            showLoading(false);
        }
    };

    private void showLoading(boolean loading) {
        if (loading) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == AppUtilities.ADD_REQUEST_CODE) {
            if (resultCode == AppUtilities.ADD_RESULT_CODE) {
                MoviesFavorite moviesFavorite = data.getParcelableExtra(MoviesDetail.EXTRA_MOVIES_DETAIL);
                favoriteAdapter.insertData(moviesFavorite);
            }
            else if (resultCode == AppUtilities.DELETE_RESULT_CODE) {
                int position = data.getIntExtra(MoviesDetail.EXTRA_POSITION_MOVIES, 0);
                favoriteAdapter.deleteData(position);
            }
        }
    }
}
