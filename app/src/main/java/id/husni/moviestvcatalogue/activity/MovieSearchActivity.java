package id.husni.moviestvcatalogue.activity;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import id.husni.moviestvcatalogue.R;
import id.husni.moviestvcatalogue.adapter.search.MoviesSearchAdapter;
import id.husni.moviestvcatalogue.detail.search.MoviesSearchDetail;
import id.husni.moviestvcatalogue.model.search.MovieSearch;
import id.husni.moviestvcatalogue.utilities.CustomClickListener;
import id.husni.moviestvcatalogue.viewmodel.search.MoviesSearchViewModel;

public class MovieSearchActivity extends AppCompatActivity {
    private MoviesSearchAdapter adapter;
    private ProgressBar progressBar;
    private MoviesSearchViewModel model;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_search);

        Toolbar tbar = findViewById(R.id.tbarMovieSearch);
        setSupportActionBar(tbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        progressBar = findViewById(R.id.progressBarMoviesSearch);

        adapter = new MoviesSearchAdapter(this);

        recyclerView = findViewById(R.id.recyclerMovieSearch);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        model = ViewModelProviders.of(this).get(MoviesSearchViewModel.class);
        model.getData().observe(this,myObserver);


    }

    private Observer<ArrayList<MovieSearch>> myObserver = new Observer<ArrayList<MovieSearch>>() {
        @Override
        public void onChanged(final ArrayList<MovieSearch> movieSearches) {
                adapter.setMovieSearches(movieSearches);
                showLoading(false);
            CustomClickListener.add(recyclerView).setOnClickItem(new CustomClickListener.OnClickItem() {
                @Override
                public void onItemClicked(RecyclerView recyclerView, int position, View view) {
                    Intent intent = new Intent(MovieSearchActivity.this, MoviesSearchDetail.class);
                    intent.putExtra(MoviesSearchDetail.EXTRA_MOVIES_SEARCH_DETAIL, movieSearches.get(position));
                    startActivity(intent);
                }
            });
        }
    };

    private void showLoading(boolean isLoading) {
        if (isLoading) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search_movies, menu);
        SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
        if (searchManager != null) {
            SearchView searchView = (SearchView) menu.findItem(R.id.searchMovie).getActionView();
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
            searchView.setQueryHint(getResources().getString(R.string.search));
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    model.setData(newText);
                    showLoading(true);
                    return false;
                }
            });
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}
