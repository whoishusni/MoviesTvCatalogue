package id.husni.moviestvcatalogue.activity;

import android.app.SearchManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import id.husni.moviestvcatalogue.R;
import id.husni.moviestvcatalogue.adapter.search.MoviesSearchAdapter;
import id.husni.moviestvcatalogue.model.search.MovieSearch;
import id.husni.moviestvcatalogue.viewmodel.search.MoviesSearchViewModel;

public class MovieSearchActivity extends AppCompatActivity {
    public static final String QUERY_EXTRA = "queryExtra" ;
    private MoviesSearchAdapter adapter;
    private ProgressBar progressBar;
    private TextView emptyText;
    private MoviesSearchViewModel model;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_search);

        Toolbar tbar = findViewById(R.id.tbarMovieSearch);
        setSupportActionBar(tbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(getResources().getString(R.string.searchMovie));
        }
        progressBar = findViewById(R.id.progressBarMoviesSearch);

        emptyText = findViewById(R.id.emptyTextMovieSearch);

        adapter = new MoviesSearchAdapter(this);

        recyclerView = findViewById(R.id.recyclerMovieSearch);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        String queryExtra = getIntent().getStringExtra(QUERY_EXTRA);

        model = ViewModelProviders.of(this).get(MoviesSearchViewModel.class);
        model.setData(queryExtra);
        showLoading(true);
        model.getData().observe(this,myObserver);

    }

    private Observer<ArrayList<MovieSearch>> myObserver = new Observer<ArrayList<MovieSearch>>() {
        @Override
        public void onChanged(final ArrayList<MovieSearch> movieSearches) {
                adapter.setMovieSearches(movieSearches);
                showLoading(false);
        }
    };

    private void showLoading(boolean isLoading) {
        if (isLoading) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }

    private void showText(boolean showText) {
        if (showText) {
            emptyText.setVisibility(View.VISIBLE);
        } else {
            emptyText.setVisibility(View.GONE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);
        SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
        if (searchManager != null) {
            SearchView searchView = (SearchView) menu.findItem(R.id.searchData).getActionView();
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
            searchView.setQueryHint(getResources().getString(R.string.search));
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    if (newText.isEmpty()) {
                        recyclerView.setAdapter(null);
                        showText(true);
                    } else {
                        recyclerView.setAdapter(adapter);
                        model.setData(newText);
                        showLoading(true);
                        showText(false);
                    }
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
