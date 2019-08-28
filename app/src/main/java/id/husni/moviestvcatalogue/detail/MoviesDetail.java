package id.husni.moviestvcatalogue.detail;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import id.husni.moviestvcatalogue.R;
import id.husni.moviestvcatalogue.model.Movies;
import id.husni.moviestvcatalogue.utilities.AppUtilities;

public class MoviesDetail extends AppCompatActivity {

    public static final String EXTRA_MOVIES_DETAIL = "extra_movies_detail";

    TextView tvTitle,tvNilaiRating,tvRelease,tvOverview;
    RatingBar ratingBar;
    ImageView imageMoviesDetail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies_detail);

        Movies movies = getIntent().getParcelableExtra(EXTRA_MOVIES_DETAIL);

        Toolbar tbar = findViewById(R.id.tbarMoviesDetail);
        setSupportActionBar(tbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        CollapsingToolbarLayout collapsingTool = findViewById(R.id.collapsingMoviesDetail);
        collapsingTool.setTitle(movies.getTitle());
        collapsingTool.setExpandedTitleTextAppearance(R.style.customExpandedTeksStyle);

        tvTitle = findViewById(R.id.tvMoviesTitleDetail);
        tvNilaiRating = findViewById(R.id.tvRatingMoviesDetail);
        tvRelease = findViewById(R.id.tvMoviesReleaseDetail);
        tvOverview = findViewById(R.id.tvMoviesOverviewDetail);
        ratingBar = findViewById(R.id.movieRatingBarDetail);
        imageMoviesDetail = findViewById(R.id.imageMoviesDetail);


        tvTitle.setText(movies.getTitle());
        tvNilaiRating.setText(String.valueOf(movies.getVoteAverage()));
        tvRelease.setText(movies.getReleaseDate());
        tvOverview.setText(movies.getOverview());
        float ratingStar = (float) (movies.getVoteAverage() / 2);
        ratingBar.setRating(ratingStar);
        Glide.with(this)
                .load(AppUtilities.POSTER_FILM_DETAIL + movies.getPosterPath())
                .into(imageMoviesDetail);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
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
