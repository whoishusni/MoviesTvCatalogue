package id.husni.moviestvcatalogue.activity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import id.husni.moviestvcatalogue.R;
import id.husni.moviestvcatalogue.fragment.FavoriteMainFragment;
import id.husni.moviestvcatalogue.fragment.MoviesFragment;
import id.husni.moviestvcatalogue.fragment.SeriesFragment;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            MoviesFragment moviesFragment = new MoviesFragment();
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.main_frame, moviesFragment)
                    .commit();
        }

        //set toolbar
        Toolbar tbar = findViewById(R.id.mainTbar);
        setSupportActionBar(tbar);

        BottomNavigationView bnv = findViewById(R.id.mainBnv);
        bnv.setOnNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.navMovies:
                MoviesFragment moviesFragment = new MoviesFragment();
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_frame, moviesFragment)
                        .commit();
                return true;

            case R.id.navSeries:
                SeriesFragment seriesFragment = new SeriesFragment();
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_frame, seriesFragment)
                        .commit();
                return true;

            case R.id.navFavorite:
                FavoriteMainFragment favoriteMainFragment = new FavoriteMainFragment();
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_frame, favoriteMainFragment)
                        .commit();
                return true;
        }
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_item, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.actChangeLang:
                Intent langIntent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
                startActivity(langIntent);
                break;
            case R.id.actReminder:
                Intent reminderIntent = new Intent(MainActivity.this, ReminderActivity.class);
                startActivity(reminderIntent);
                break;
            case R.id.actSearchMovie:
                Intent searchMovieIntent = new Intent(MainActivity.this, MovieSearchActivity.class);
                startActivity(searchMovieIntent);
            case R.id.actSearchSeries:
                Intent searchSeriesIntent = new Intent(MainActivity.this, SeriesSearchActivity.class);
                startActivity(searchSeriesIntent);
        }
        return super.onOptionsItemSelected(item);
    }
}
