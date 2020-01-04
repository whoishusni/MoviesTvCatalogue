package id.husni.moviestvcatalogue.pageradapter;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import id.husni.moviestvcatalogue.R;
import id.husni.moviestvcatalogue.fragment.favorite.MoviesFavoriteFragment;
import id.husni.moviestvcatalogue.fragment.favorite.SeriesFavoriteFragment;

public class FavoritePagerAdapter extends FragmentPagerAdapter {
    private Context mcontext;

    public FavoritePagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mcontext = context;
    }

    @StringRes
    private final int [] TAB_TITLES = new int[]{
            R.string.movies,
            R.string.series
    };

    @Override
    public Fragment getItem(int position) {
        Fragment fragments = null;
        switch (position) {
            case 0:
                fragments = new MoviesFavoriteFragment();
                break;
            case 1:
                fragments = new SeriesFavoriteFragment();
                break;
        }

        return fragments;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mcontext.getResources().getString(TAB_TITLES[position]);
    }
}
