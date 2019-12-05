package id.husni.moviestvcatalogue.fragment;


import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import id.husni.moviestvcatalogue.R;
import id.husni.moviestvcatalogue.activity.MovieSearchActivity;
import id.husni.moviestvcatalogue.activity.ReminderActivity;
import id.husni.moviestvcatalogue.adapter.MoviesAdapter;
import id.husni.moviestvcatalogue.adapter.favorite.MoviesFavoriteAdapter;
import id.husni.moviestvcatalogue.model.Movies;
import id.husni.moviestvcatalogue.viewmodel.MoviesViewModel;
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;

public class MoviesFragment extends Fragment {
    private MoviesAdapter adapter;
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
        setHasOptionsMenu(true);

        adapter = new MoviesAdapter(getContext());
        MoviesFavoriteAdapter favoriteAdapter = new MoviesFavoriteAdapter(getContext());

        progressBar = view.findViewById(R.id.progressBarMovies);

        RecyclerView recyclerView = view.findViewById(R.id.recyclerMovies);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        ScaleInAnimationAdapter scaleAdapter = new ScaleInAnimationAdapter(adapter);
        recyclerView.setAdapter(scaleAdapter);

        MoviesViewModel model = ViewModelProviders.of(this).get(MoviesViewModel.class);
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
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_item, menu);
        SearchManager searchManager = (SearchManager) getContext().getSystemService(Context.SEARCH_SERVICE);
        if (searchManager != null) {
            SearchView searchView = (SearchView) menu.findItem(R.id.actSearch).getActionView();
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
            searchView.setQueryHint(getResources().getString(R.string.searchMovie));
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    Intent intent = new Intent(getActivity(), MovieSearchActivity.class);
                    intent.putExtra(MovieSearchActivity.QUERY_EXTRA, query);
                    startActivity(intent);
                    return true;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    return false;
                }
            });
        }
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.actChangeLang:
                Intent intent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
                startActivity(intent);
                break;
            case R.id.actReminder:
                Intent reminderIntent = new Intent(getContext(), ReminderActivity.class);
                startActivity(reminderIntent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
