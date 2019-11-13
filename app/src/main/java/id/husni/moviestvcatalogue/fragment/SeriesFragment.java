package id.husni.moviestvcatalogue.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import id.husni.moviestvcatalogue.R;
import id.husni.moviestvcatalogue.adapter.SeriesAdapter;
import id.husni.moviestvcatalogue.adapter.favorite.SeriesFavoriteAdapter;
import id.husni.moviestvcatalogue.detail.SeriesDetail;
import id.husni.moviestvcatalogue.model.Series;
import id.husni.moviestvcatalogue.model.favorite.SeriesFavorite;
import id.husni.moviestvcatalogue.utilities.AppUtilities;
import id.husni.moviestvcatalogue.utilities.CustomClickListener;
import id.husni.moviestvcatalogue.viewmodel.SeriesViewModel;
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;


/**
 * A simple {@link Fragment} subclass.
 */
public class SeriesFragment extends Fragment {

    private SeriesAdapter adapter;
    private SeriesFavoriteAdapter favoriteAdapter;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private SeriesViewModel model;


    public SeriesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_series, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        adapter = new SeriesAdapter(getContext());
        favoriteAdapter = new SeriesFavoriteAdapter(getContext());

        recyclerView = view.findViewById(R.id.recyclerSeries);
        progressBar = view.findViewById(R.id.progressBarSeries);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        ScaleInAnimationAdapter animationAdapter = new ScaleInAnimationAdapter(adapter);
        recyclerView.setAdapter(animationAdapter);

        model = ViewModelProviders.of(this).get(SeriesViewModel.class);
        model.setMoviesData();
        showLoading(true);
        model.getSeriesData().observe(this, myObserver);
    }

    private Observer<ArrayList<Series>> myObserver = new Observer<ArrayList<Series>>() {
        @Override
        public void onChanged(final ArrayList<Series> series) {
            adapter.setSeriesArrayList(series);
            showLoading(false);
        }
    };

    private void showLoading(boolean loading) {
        if (loading) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == AppUtilities.ADD_REQUEST_CODE) {
            if (resultCode == AppUtilities.ADD_RESULT_CODE) {
                SeriesFavorite seriesFavorite = data.getParcelableExtra(SeriesDetail.EXTRA_SERIES_DETAIL);
                favoriteAdapter.insertData(seriesFavorite);
            }
            else if (resultCode == AppUtilities.DELETE_RESULT_CODE) {
                int position = data.getIntExtra(SeriesDetail.EXTRA_POSITION_SERIES, 0);
                favoriteAdapter.deleteData(position);
            }
        }
    }
}
