package id.husni.moviestvcatalogue.detail.search;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.ContentValues;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.like.LikeButton;
import com.like.OnLikeListener;
import com.robertlevonyan.views.chip.Chip;

import id.husni.moviestvcatalogue.R;
import id.husni.moviestvcatalogue.model.favorite.SeriesFavorite;
import id.husni.moviestvcatalogue.model.search.SeriesSearch;
import id.husni.moviestvcatalogue.utilities.AppUtilities;

import static id.husni.moviestvcatalogue.database.DatabaseContract.OVERVIEW;
import static id.husni.moviestvcatalogue.database.DatabaseContract.POSTER;
import static id.husni.moviestvcatalogue.database.DatabaseContract.RATING;
import static id.husni.moviestvcatalogue.database.DatabaseContract.RELEASE_DATE;
import static id.husni.moviestvcatalogue.database.DatabaseContract.TITLE;
import static id.husni.moviestvcatalogue.database.DatabaseContract.URI_SERIES;

public class SeriesSearchDetail extends AppCompatActivity {

    public static final String EXTRA_SERIES_DETAIL =  "extraSeriesDetail";
    private SeriesSearch seriesSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_series_search_detail);

        seriesSearch = getIntent().getParcelableExtra(EXTRA_SERIES_DETAIL);
        Toolbar toolbar = findViewById(R.id.tbarSeriesDetailSearch);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
        }

        CollapsingToolbarLayout collapsingToolbarLayout = findViewById(R.id.collapsingSeriesDetailSearch);
        collapsingToolbarLayout.setTitle(seriesSearch.getTitle());
        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.customExpandedTeksStyle);

        TextView title = findViewById(R.id.tvSeriesTitleDetailSearch);
        String titleText = seriesSearch.getTitle();
        title.setText(titleText);

        TextView release = findViewById(R.id.tvSeriesReleaseDetailSearch);
        String releaseText = seriesSearch.getRelease();
        release.setText(releaseText);

        TextView overview = findViewById(R.id.tvSeriesOverviewDetailSearch);
        String overviewText = seriesSearch.getOverView();
        overview.setText(overviewText);

        Chip chip = findViewById(R.id.chipRatingSeriesDetailSearch);
        String chipText = String.valueOf(seriesSearch.getVoteAverage());
        chip.setText(chipText);

        ImageView imageView = findViewById(R.id.imageSeriesDetailSearch);
        Glide.with(this)
                .load(AppUtilities.POSTER_FILM_DETAIL + seriesSearch.getPosterPath())
                .into(imageView);

        RatingBar ratingBar = findViewById(R.id.seriesRatingBarDetailSearch);
        float rating = (float) (seriesSearch.getVoteAverage() / 2);
        ratingBar.setRating(rating);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_for_like, menu);
        MenuItem menuItem = menu.findItem(R.id.actLike);
        LikeButton likeButton = menuItem.getActionView().findViewById(R.id.customLikeButton);

        SeriesFavorite seriesFavorite = new SeriesFavorite();
        seriesFavorite.setTitle(seriesSearch.getTitle());
        seriesFavorite.setVoteAverage(String.valueOf(seriesSearch.getVoteAverage()));
        seriesFavorite.setOverview(seriesSearch.getOverView());
        seriesFavorite.setPosterPath(seriesSearch.getPosterPath());
        seriesFavorite.setAiringDate(seriesSearch.getRelease());

        final ContentValues values = new ContentValues();
        values.put(TITLE, seriesFavorite.getTitle());
        values.put(RATING, seriesFavorite.getVoteAverage());
        values.put(OVERVIEW, seriesFavorite.getOverview());
        values.put(POSTER, seriesFavorite.getPosterPath());
        values.put(RELEASE_DATE, seriesFavorite.getAiringDate());

        likeButton.setOnLikeListener(new OnLikeListener() {
            @Override
            public void liked(LikeButton likeButton) {
                getContentResolver().insert(URI_SERIES, values);
                Toast.makeText(SeriesSearchDetail.this, getResources().getString(R.string.addedtofavorite), Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void unLiked(LikeButton likeButton) {

            }
        });
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
