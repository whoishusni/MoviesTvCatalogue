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
import id.husni.moviestvcatalogue.adapter.search.SeriesSearchAdapter;
import id.husni.moviestvcatalogue.model.search.SeriesSearch;
import id.husni.moviestvcatalogue.viewmodel.search.SeriesSearchViewModel;

public class SeriesSearchActivity extends AppCompatActivity {
    SeriesSearchViewModel model;
    ProgressBar progressBar;
    TextView emptyText;
    SeriesSearchAdapter adapter;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_series_search);
        Toolbar toolbar = findViewById(R.id.tbarSeriesSearch);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        progressBar = findViewById(R.id.progressBarSeriesSearch);

        emptyText = findViewById(R.id.emptyTextSeriesSearch);

        adapter = new SeriesSearchAdapter(this);
        recyclerView = findViewById(R.id.recyclerSeriesSearch);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        model = ViewModelProviders.of(this).get(SeriesSearchViewModel.class);
        model.getSearchData().observe(this,searchObserver);
    }

    Observer<ArrayList<SeriesSearch>> searchObserver = new Observer<ArrayList<SeriesSearch>>() {
        @Override
        public void onChanged(ArrayList<SeriesSearch> seriesSearches) {
            adapter.setSeriesSearches(seriesSearches);
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
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search_series, menu);
        SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
        if (searchManager != null) {
            SearchView searchView = (SearchView) menu.findItem(R.id.searchSeries).getActionView();
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
}
