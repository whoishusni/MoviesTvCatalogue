package id.husni.consumerapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;

import id.husni.consumerapp.fragment.FavoriteMainFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            FavoriteMainFragment moviesFragment = new FavoriteMainFragment();
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.main_frame, moviesFragment)
                    .commit();
        }

        //set toolbar
        Toolbar tbar = findViewById(R.id.mainTbar);
        setSupportActionBar(tbar);

    }
}
