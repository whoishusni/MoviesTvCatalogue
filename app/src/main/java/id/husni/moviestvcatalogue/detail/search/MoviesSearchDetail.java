package id.husni.moviestvcatalogue.detail.search;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.robertlevonyan.views.chip.Chip;

import id.husni.moviestvcatalogue.R;
import id.husni.moviestvcatalogue.model.search.MovieSearch;
import id.husni.moviestvcatalogue.utilities.AppUtilities;

public class MoviesSearchDetail extends AppCompatActivity {
    private TextView title;
    private TextView release;
    private TextView overview;
    private Chip chip;
    private ImageView imageView;
    private RatingBar ratingBar;

    public static final String EXTRA_MOVIES_SEARCH_DETAIL = "extra_movies_search_detail" ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies_search_detail);

        MovieSearch movieSearch = getIntent().getParcelableExtra(EXTRA_MOVIES_SEARCH_DETAIL);
        Toolbar tbar = findViewById(R.id.tbarMoviesDetailSearch);
        setSupportActionBar(tbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        CollapsingToolbarLayout collapsingToolbarLayout = findViewById(R.id.collapsingMoviesDetailSearch);
        collapsingToolbarLayout.setTitle(movieSearch.getTitle());
        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.customExpandedTeksStyle);

        title = findViewById(R.id.tvMoviesTitleDetailSearch);
        String titleText = movieSearch.getTitle();
        title.setText(titleText);

        release = findViewById(R.id.tvMoviesReleaseDetailSearch);
        String releaseText = movieSearch.getRelease();
        release.setText(releaseText);

        overview = findViewById(R.id.tvMoviesOverviewDetailSearch);
        String overviewText = movieSearch.getOverView();
        overview.setText(overviewText);

        chip = findViewById(R.id.chipRatingMoviesDetailSearch);
        String chipText = String.valueOf(movieSearch.getVoteAverage());
        chip.setText(chipText);

        imageView = findViewById(R.id.imageMoviesDetailSearch);
        Glide.with(this)
                .load(AppUtilities.POSTER_FILM_DETAIL + movieSearch.getPosterPath())
                .into(imageView);

        ratingBar = findViewById(R.id.movieRatingBarDetailSearch);
        float rating = (float) (movieSearch.getVoteAverage() / 2);
        ratingBar.setRating(rating);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}
