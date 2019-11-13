package id.husni.moviestvcatalogue.adapter.search;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.robertlevonyan.views.chip.Chip;

import java.util.ArrayList;

import id.husni.moviestvcatalogue.R;
import id.husni.moviestvcatalogue.detail.search.SeriesSearchDetail;
import id.husni.moviestvcatalogue.model.search.SeriesSearch;
import id.husni.moviestvcatalogue.utilities.AppUtilities;
import id.husni.moviestvcatalogue.utilities.CustomItemClick;

public class SeriesSearchAdapter extends RecyclerView.Adapter<SeriesSearchAdapter.ViewHolder> {
    private ArrayList<SeriesSearch> seriesSearches = new ArrayList<>();
    private Context context;

    public SeriesSearchAdapter(Context context) {
        this.context = context;
    }

    public ArrayList<SeriesSearch> getSeriesSearches() {
        return seriesSearches;
    }

    public void setSeriesSearches(ArrayList<SeriesSearch> items) {
        if (seriesSearches.size() > 0) {
            seriesSearches.clear();
        }
        seriesSearches.addAll(items);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SeriesSearchAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.series_item_search, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tvTitle.setText(seriesSearches.get(position).getTitle());
        holder.tvRelease.setText(seriesSearches.get(position).getRelease());
        holder.chipRating.setText(String.valueOf(seriesSearches.get(position).getVoteAverage()));
        float rate = (float) seriesSearches.get(position).getVoteAverage()/2;
        holder.ratingBar.setRating(rate);
        Glide.with(context)
                .load(AppUtilities.POSTER_FILM + seriesSearches.get(position).getPosterPath())
                .into(holder.imageView);
        holder.cvSeriesSearch.setOnClickListener(new CustomItemClick(position, new CustomItemClick.OnItemClick() {
            @Override
            public void onItemClicked(View view, int position) {
                Intent intent = new Intent(context, SeriesSearchDetail.class);
                intent.putExtra(SeriesSearchDetail.EXTRA_SERIES_DETAIL, seriesSearches.get(position));
                if (context instanceof Activity) {
                    context.startActivity(intent);
                }
            }
        }));
    }

    @Override
    public int getItemCount() {
        return seriesSearches.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvTitle;
        public TextView tvRelease;
        public Chip chipRating;
        public RatingBar ratingBar;
        public CardView cvSeriesSearch;
        public ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvSeriesSearchTitle);
            tvRelease = itemView.findViewById(R.id.tvSeriesSearchRelease);
            chipRating = itemView.findViewById(R.id.chipSeriesSearchRating);
            ratingBar = itemView.findViewById(R.id.seriesSearchRatingBar);
            cvSeriesSearch = itemView.findViewById(R.id.cvSeriesSearch);
            imageView = itemView.findViewById(R.id.seriesSearchImage);

        }
    }
}
