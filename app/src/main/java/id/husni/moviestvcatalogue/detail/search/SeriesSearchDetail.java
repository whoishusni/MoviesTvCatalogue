package id.husni.moviestvcatalogue.detail.search;

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
import com.robertlevonyan.views.chip.Chip;

import id.husni.moviestvcatalogue.R;
import id.husni.moviestvcatalogue.model.search.SeriesSearch;
import id.husni.moviestvcatalogue.utilities.AppUtilities;

public class SeriesSearchDetail extends AppCompatActivity {
    private TextView title;
    private TextView release;
    private TextView overview;
    private Chip chip;
    private ImageView imageView;
    private RatingBar ratingBar;

    public static final String EXTRA_SERIES_DETAIL =  "extraSeriesDetail";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_series_search_detail);

        SeriesSearch seriesSearch = getIntent().getParcelableExtra(EXTRA_SERIES_DETAIL);
        Toolbar toolbar = findViewById(R.id.tbarSeriesDetailSearch);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        CollapsingToolbarLayout collapsingToolbarLayout = findViewById(R.id.collapsingSeriesDetailSearch);
        collapsingToolbarLayout.setTitle(seriesSearch.getTitle());
        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.customExpandedTeksStyle);

        title = findViewById(R.id.tvSeriesTitleDetailSearch);
        String titleText = seriesSearch.getTitle();
        title.setText(titleText);

        release = findViewById(R.id.tvSeriesReleaseDetailSearch);
        String releaseText = seriesSearch.getRelease();
        release.setText(releaseText);

        overview = findViewById(R.id.tvSeriesOverviewDetailSearch);
        String overviewText = seriesSearch.getOverView();
        overview.setText(overviewText);

        chip = findViewById(R.id.chipRatingSeriesDetailSearch);
        String chipText = String.valueOf(seriesSearch.getVoteAverage());
        chip.setText(chipText);

        imageView = findViewById(R.id.imageSeriesDetailSearch);
        Glide.with(this)
                .load(AppUtilities.POSTER_FILM_DETAIL + seriesSearch.getPosterPath())
                .into(imageView);

        ratingBar = findViewById(R.id.seriesRatingBarDetailSearch);
        float rating = (float) (seriesSearch.getVoteAverage() / 2);
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
