package id.husni.moviestvcatalogue.detail.search;

import android.content.ContentValues;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.like.LikeButton;
import com.like.OnLikeListener;
import com.robertlevonyan.views.chip.Chip;

import id.husni.moviestvcatalogue.R;
import id.husni.moviestvcatalogue.model.favorite.MoviesFavorite;
import id.husni.moviestvcatalogue.model.search.MovieSearch;
import id.husni.moviestvcatalogue.utilities.AppUtilities;

import static id.husni.moviestvcatalogue.database.DatabaseContract.OVERVIEW;
import static id.husni.moviestvcatalogue.database.DatabaseContract.POSTER;
import static id.husni.moviestvcatalogue.database.DatabaseContract.RATING;
import static id.husni.moviestvcatalogue.database.DatabaseContract.RELEASE_DATE;
import static id.husni.moviestvcatalogue.database.DatabaseContract.TITLE;
import static id.husni.moviestvcatalogue.database.DatabaseContract.URI_MOVIES;

public class MoviesSearchDetail extends AppCompatActivity {

    public static final String EXTRA_MOVIES_SEARCH_DETAIL = "extra_movies_search_detail" ;
    private MovieSearch movieSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies_search_detail);

        movieSearch = getIntent().getParcelableExtra(EXTRA_MOVIES_SEARCH_DETAIL);

        Toolbar tbar = findViewById(R.id.tbarMoviesDetailSearch);
        setSupportActionBar(tbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);

        }

        CollapsingToolbarLayout collapsingToolbarLayout = findViewById(R.id.collapsingMoviesDetailSearch);
        collapsingToolbarLayout.setTitle(movieSearch.getTitle());
        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.customExpandedTeksStyle);

        TextView title = findViewById(R.id.tvMoviesTitleDetailSearch);
        String titleText = movieSearch.getTitle();
        title.setText(titleText);

        TextView release = findViewById(R.id.tvMoviesReleaseDetailSearch);
        String releaseText = movieSearch.getRelease();
        release.setText(releaseText);

        TextView overview = findViewById(R.id.tvMoviesOverviewDetailSearch);
        String overviewText = movieSearch.getOverView();
        overview.setText(overviewText);

        Chip chip = findViewById(R.id.chipRatingMoviesDetailSearch);
        String chipText = String.valueOf(movieSearch.getVoteAverage());
        chip.setText(chipText);

        ImageView imageView = findViewById(R.id.imageMoviesDetailSearch);
        Glide.with(this)
                .load(AppUtilities.POSTER_FILM_DETAIL + movieSearch.getPosterPath())
                .into(imageView);

        RatingBar ratingBar = findViewById(R.id.movieRatingBarDetailSearch);
        float rating = (float) (movieSearch.getVoteAverage() / 2);
        ratingBar.setRating(rating);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_for_like, menu);
        MoviesFavorite moviesFavorite = new MoviesFavorite();

        moviesFavorite.setTitle(movieSearch.getTitle());
        moviesFavorite.setVoteAverage(String.valueOf(movieSearch.getVoteAverage()));
        moviesFavorite.setOverview(movieSearch.getOverView());
        moviesFavorite.setPosterPath(movieSearch.getPosterPath());
        moviesFavorite.setReleaseDate(movieSearch.getRelease());

        final ContentValues values = new ContentValues();

        values.put(TITLE, moviesFavorite.getTitle());
        values.put(RATING, moviesFavorite.getVoteAverage());
        values.put(OVERVIEW, moviesFavorite.getOverview());
        values.put(POSTER, moviesFavorite.getPosterPath());
        values.put(RELEASE_DATE, moviesFavorite.getReleaseDate());


        MenuItem menuItem = menu.findItem(R.id.actLike);
        LikeButton likeButton = menuItem.getActionView().findViewById(R.id.customLikeButton);
        likeButton.setOnLikeListener(new OnLikeListener() {
            @Override
            public void liked(LikeButton likeButton) {
                getContentResolver().insert(URI_MOVIES, values);
                Toast.makeText(MoviesSearchDetail.this, getResources().getString(R.string.addedtofavorite), Toast.LENGTH_SHORT).show();
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
