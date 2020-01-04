package id.husni.moviestvcatalogue.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import id.husni.moviestvcatalogue.R;
import id.husni.moviestvcatalogue.adapter.release.MoviesReleaseAdapter;
import id.husni.moviestvcatalogue.model.release.MovieRelease;
import id.husni.moviestvcatalogue.viewmodel.release.MovieReleaseViewModel;

public class MovieReleaseActivity extends AppCompatActivity {
    private ProgressBar progressBar;
    private MoviesReleaseAdapter adapter;
    private MovieReleaseViewModel viewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_release);
        Toolbar tbar = findViewById(R.id.tbarMovieRelease);
        setSupportActionBar(tbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Today Release");
        }


        progressBar = findViewById(R.id.progressBarMovieRelease);
        RecyclerView recyclerView = findViewById(R.id.movieReleaseRecycler);
        adapter = new MoviesReleaseAdapter(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerView.setAdapter(adapter);

        viewModel = ViewModelProviders.of(this).get(MovieReleaseViewModel.class);
        viewModel.setReleaseData();
        showProgress(true);
        viewModel.getReleaseData().observe(this,movieReleaseObserver);


    }

    private Observer<ArrayList<MovieRelease>> movieReleaseObserver = new Observer<ArrayList<MovieRelease>>() {
        @Override
        public void onChanged(ArrayList<MovieRelease> movieReleases) {
            adapter.setMovieReleases(movieReleases);
            showProgress(false);
        }
    };

    private void showProgress(boolean b) {
        if (b) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            Intent intent = new Intent(MovieReleaseActivity.this, MainActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

}
