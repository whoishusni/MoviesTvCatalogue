package id.husni.moviestvcatalogue.fragment.favorite;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import id.husni.moviestvcatalogue.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class SeriesFavoriteFragment extends Fragment {


    public SeriesFavoriteFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_series_favorite, container, false);
    }

}
