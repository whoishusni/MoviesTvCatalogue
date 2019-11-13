package id.husni.moviestvcatalogue.detail;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.ContentValues;
import android.content.Intent;
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
import id.husni.moviestvcatalogue.database.table.SeriesHelper;
import id.husni.moviestvcatalogue.model.Series;
import id.husni.moviestvcatalogue.model.favorite.SeriesFavorite;
import id.husni.moviestvcatalogue.utilities.AppUtilities;

import static id.husni.moviestvcatalogue.database.DatabaseContract.*;

public class SeriesDetail extends AppCompatActivity {

    public static final String EXTRA_SERIES_DETAIL = "extra_series" ;
    public static final String EXTRA_POSITION_SERIES = "extra_position_series" ;
    private SeriesHelper helper;
    private SeriesFavorite seriesFavorite;
    private int position;
    private Series series;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_series_detail);

        helper = SeriesHelper.getInstance(getApplicationContext());
        helper.open();

        if (seriesFavorite != null) {
            position = getIntent().getIntExtra(EXTRA_POSITION_SERIES, 0);
        } else {
            seriesFavorite = new SeriesFavorite();
        }

        series = getIntent().getParcelableExtra(EXTRA_SERIES_DETAIL);

        Toolbar toolbar = findViewById(R.id.tbarSeriesDetail);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        CollapsingToolbarLayout collapsingToolbarLayout = findViewById(R.id.collapsingSeriesDetail);
        collapsingToolbarLayout.setTitle(series.getTitle());
        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.customExpandedTeksStyle);

        TextView tvTitle = findViewById(R.id.tvSeriesTitleDetail);
        TextView tvAiring = findViewById(R.id.tvSeriesAiringDetail);
        ImageView imageSeriesDetail = findViewById(R.id.imageSeriesDetail);
        TextView tvOverview = findViewById(R.id.tvSeriesOverviewDetail);
        RatingBar ratingBar = findViewById(R.id.seriesRatingBarDetail);
        Chip chip = findViewById(R.id.chipRatingSeriesDetail);


        tvTitle.setText(series.getTitle());
        tvAiring.setText(series.getAiringDate());
        tvOverview.setText(series.getOverview());
        float nilairBar = (float) (series.getVoteAverage() / 2);
        ratingBar.setRating(nilairBar);
        chip.setText(String.valueOf(series.getVoteAverage()));
        Glide.with(this)
                .load(AppUtilities.POSTER_FILM_DETAIL + series.getPosterPath())
                .into(imageSeriesDetail);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        seriesFavorite.setTitle(series.getTitle());
        seriesFavorite.setVoteAverage(String.valueOf(series.getVoteAverage()));
        seriesFavorite.setOverview(series.getOverview());
        seriesFavorite.setPosterPath(series.getPosterPath());
        seriesFavorite.setAiringDate(series.getAiringDate());

        final Intent intent = new Intent();
        intent.putExtra(EXTRA_SERIES_DETAIL, seriesFavorite);
        intent.putExtra(EXTRA_POSITION_SERIES, position);

        final ContentValues contentValues = new ContentValues();
        contentValues.put(TITLE, seriesFavorite.getTitle());
        contentValues.put(RATING, seriesFavorite.getVoteAverage());
        contentValues.put(OVERVIEW, seriesFavorite.getOverview());
        contentValues.put(POSTER, seriesFavorite.getPosterPath());
        contentValues.put(RELEASE_DATE, seriesFavorite.getAiringDate());

        getMenuInflater().inflate(R.menu.menu_for_like,menu);
        MenuItem item = menu.findItem(R.id.actLike);
        LikeButton likeButton = item.getActionView().findViewById(R.id.customLikeButton);
        likeButton.setOnLikeListener(new OnLikeListener() {
            @Override
            public void liked(LikeButton likeButton) {
                long result = helper.insertData(contentValues);
                if (result > 0) {
                    seriesFavorite.setId((int) result);
                    setResult(AppUtilities.ADD_RESULT_CODE, intent);
                    Toast.makeText(SeriesDetail.this, series.getTitle() +" "+ getResources().getString(R.string.addedtofavorite), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(SeriesDetail.this, "Gagal Like", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void unLiked(LikeButton likeButton) {
                long result = helper.deleteData(String.valueOf(seriesFavorite.getId()));
                if (result > 0) {
                    setResult(AppUtilities.DELETE_RESULT_CODE, intent);
                    Toast.makeText(SeriesDetail.this, series.getTitle()+" "+getResources().getString(R.string.removeFromfavorite), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(SeriesDetail.this, "Gagal Unliked", Toast.LENGTH_SHORT).show();
                }
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
