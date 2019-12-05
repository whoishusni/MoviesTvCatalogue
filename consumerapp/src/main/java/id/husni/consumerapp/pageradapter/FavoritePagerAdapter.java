package id.husni.consumerapp.pageradapter;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import id.husni.consumerapp.fragment.favorite.MoviesFavoriteFragment;
import id.husni.consumerapp.fragment.favorite.SeriesFavoriteFragment;

public class FavoritePagerAdapter extends FragmentPagerAdapter {
    private Fragment [] fragments;

    public FavoritePagerAdapter(FragmentManager fm) {
        super(fm);
        fragments = new Fragment[]{
                new MoviesFavoriteFragment(),new SeriesFavoriteFragment()
        };
    }

    @Override
    public Fragment getItem(int position) {
        return fragments[position];
    }

    @Override
    public int getCount() {
        return fragments.length;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Movies";
            case 1:
                return "Tv Series";
        }
        return super.getPageTitle(position);
    }
}
