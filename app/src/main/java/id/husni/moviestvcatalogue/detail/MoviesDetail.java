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
    public static final String EXTRA_POSITION_MOVIES = "extra_position_movies" ;

    private TextView tvTitle;
    private TextView tvRelease;
    private TextView tvOverview;
    private RatingBar ratingBar;
    private ImageView imageMoviesDetail;
    private Chip chip;
    private MoviesHelper helper;
    private Movies movies;
    private MoviesFavorite moviesFavorite;
    private int position;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies_detail);

        helper = MoviesHelper.getInstance(getApplicationContext());
        helper.open();

        if (moviesFavorite != null) {
            position = getIntent().getIntExtra(EXTRA_POSITION_MOVIES, 0);
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
        moviesFavorite.setTitle(movies.getTitle());
        moviesFavorite.setVoteAverage(String.valueOf(movies.getVoteAverage()));
        moviesFavorite.setOverview(movies.getOverview());
        moviesFavorite.setPosterPath(movies.getPosterPath());
        moviesFavorite.setReleaseDate(movies.getReleaseDate());

        final Intent intent = new Intent();
        intent.putExtra(EXTRA_MOVIES_DETAIL, moviesFavorite);
        intent.putExtra(EXTRA_POSITION_MOVIES, position);


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
                    Toast.makeText(MoviesDetail.this, movies.getTitle() + " " + getResources().getString(R.string.addedtofavorite), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MoviesDetail.this, "Gagal Liked", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void unLiked(LikeButton likeButton) {
                helper.delete(moviesFavorite.getId());
                Toast.makeText(MoviesDetail.this, movies.getTitle()+" "+getResources().getString(R.string.removeFromfavorite), Toast.LENGTH_SHORT).show();

                /*long result = helper.delete(moviesFavorite.getId());
                if (result > 0) {
                    setResult(AppUtilities.DELETE_RESULT_CODE, intent);
                    Toast.makeText(MoviesDetail.this, movies.getTitle()+" "+getResources().getString(R.string.removeFromfavorite), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MoviesDetail.this, "Gagal Unliked", Toast.LENGTH_SHORT).show();
                }*/
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
