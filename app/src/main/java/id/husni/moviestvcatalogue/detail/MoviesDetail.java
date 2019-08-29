package id.husni.moviestvcatalogue.detail;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

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
import id.husni.moviestvcatalogue.database.table.MoviesHelper;
import id.husni.moviestvcatalogue.model.Movies;
import id.husni.moviestvcatalogue.model.favorite.MoviesFavorite;
import id.husni.moviestvcatalogue.utilities.AppUtilities;

public class MoviesDetail extends AppCompatActivity {

    public static final String EXTRA_MOVIES_DETAIL = "extra_movies_detail";
    public static final String EXTRA_POSITION = "extra_position" ;

    TextView tvTitle,tvRelease,tvOverview;
    RatingBar ratingBar;
    ImageView imageMoviesDetail;
    Chip chip;
    MoviesHelper helper;
    Movies movies;
    MoviesFavorite moviesFavorite;
    int position;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies_detail);

        helper = MoviesHelper.getInstance(this);
        helper.open();

        if (moviesFavorite != null) {
            position = getIntent().getIntExtra(EXTRA_POSITION, 0);
        } else {
            moviesFavorite = new MoviesFavorite();
        }

        movies = getIntent().getParcelableExtra(EXTRA_MOVIES_DETAIL);

        Toolbar tbar = findViewById(R.id.tbarMoviesDetail);
        setSupportActionBar(tbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        CollapsingToolbarLayout collapsingTool = findViewById(R.id.collapsingMoviesDetail);
        collapsingTool.setTitle(movies.getTitle());
        collapsingTool.setExpandedTitleTextAppearance(R.style.customExpandedTeksStyle);

        tvTitle = findViewById(R.id.tvMoviesTitleDetail);
        tvRelease = findViewById(R.id.tvMoviesReleaseDetail);
        tvOverview = findViewById(R.id.tvMoviesOverviewDetail);
        ratingBar = findViewById(R.id.movieRatingBarDetail);
        imageMoviesDetail = findViewById(R.id.imageMoviesDetail);
        chip = findViewById(R.id.chipRatingMoviesDetail);


        tvTitle.setText(movies.getTitle());
        tvRelease.setText(movies.getReleaseDate());
        tvOverview.setText(movies.getOverview());
        float ratingStar = (float) (movies.getVoteAverage() / 2);
        ratingBar.setRating(ratingStar);
        Glide.with(this)
                .load(AppUtilities.POSTER_FILM_DETAIL + movies.getPosterPath())
                .into(imageMoviesDetail);
        chip.setText(String.valueOf(movies.getVoteAverage()));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //TODO: function INSERT DATA
        moviesFavorite.setTitle(movies.getTitle());
        moviesFavorite.setVoteAverage(movies.getVoteAverage());
        moviesFavorite.setOverview(movies.getOverview());
        moviesFavorite.setPosterPath(movies.getPosterPath());
        moviesFavorite.setReleaseDate(movies.getReleaseDate());

        final Intent intent = new Intent();
        intent.putExtra(EXTRA_MOVIES_DETAIL, moviesFavorite);
        intent.putExtra(EXTRA_POSITION, position);


        getMenuInflater().inflate(R.menu.menu_for_like, menu);
        MenuItem menuItem = menu.findItem(R.id.actLike);
        LikeButton likeButton = menuItem.getActionView().findViewById(R.id.customLikeButton);

        likeButton.setOnLikeListener(new OnLikeListener() {
            @Override
            public void liked(LikeButton likeButton) {
                long result = helper.insert(moviesFavorite);
                if (result > 0) {
                    moviesFavorite.setId((int) result);
                    setResult(AppUtilities.ADD_RESULT_CODE, intent);
                    Toast.makeText(MoviesDetail.this, movies.getTitle() + getResources().getString(R.string.addedtofavorite), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void unLiked(LikeButton likeButton) {
                Toast.makeText(MoviesDetail.this, "Unliked", Toast.LENGTH_SHORT).show();
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
