package id.husni.moviestvcatalogue.detail;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

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
import id.husni.moviestvcatalogue.model.Series;
import id.husni.moviestvcatalogue.utilities.AppUtilities;

public class SeriesDetail extends AppCompatActivity {

    public static final String EXTRA_SERIES_DETAIL = "extra_series" ;
    TextView tvTitle,tvAiring,tvOverview;
    RatingBar ratingBar;
    ImageView imageSeriesDetail;
    Chip chip;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_series_detail);

        Series series = getIntent().getParcelableExtra(EXTRA_SERIES_DETAIL);

        Toolbar toolbar = findViewById(R.id.tbarSeriesDetail);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(series.getTitle());

        CollapsingToolbarLayout collapsingToolbarLayout = findViewById(R.id.collapsingSeriesDetail);
        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.customExpandedTeksStyle);

        tvTitle = findViewById(R.id.tvSeriesTitleDetail);
        tvAiring = findViewById(R.id.tvSeriesAiringDetail);
        imageSeriesDetail = findViewById(R.id.imageSeriesDetail);
        tvOverview = findViewById(R.id.tvSeriesOverviewDetail);
        ratingBar = findViewById(R.id.seriesRatingBarDetail);
        chip = findViewById(R.id.chipRatingSeriesDetail);


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
        getMenuInflater().inflate(R.menu.menu_for_like,menu);
        MenuItem item = menu.findItem(R.id.actLike);
        LikeButton likeButton = item.getActionView().findViewById(R.id.customLikeButton);
        likeButton.setOnLikeListener(new OnLikeListener() {
            @Override
            public void liked(LikeButton likeButton) {
                Toast.makeText(SeriesDetail.this, "Liked", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void unLiked(LikeButton likeButton) {
                Toast.makeText(SeriesDetail.this, "Unliked", Toast.LENGTH_SHORT).show();
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
