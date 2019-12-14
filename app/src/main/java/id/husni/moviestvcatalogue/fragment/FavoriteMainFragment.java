package id.husni.moviestvcatalogue.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import id.husni.moviestvcatalogue.pageradapter.FavoritePagerAdapter;
import id.husni.moviestvcatalogue.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteMainFragment extends Fragment {

    public FavoriteMainFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorite_main, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ViewPager viewPager = view.findViewById(R.id.favoriteViewPager);
        FavoritePagerAdapter favoritePagerAdapter = new FavoritePagerAdapter(getContext(),getFragmentManager());
        viewPager.setAdapter(favoritePagerAdapter);

        TabLayout tabLayout = view.findViewById(R.id.favoriteTabLayout);
        tabLayout.setupWithViewPager(viewPager);

    }
}
