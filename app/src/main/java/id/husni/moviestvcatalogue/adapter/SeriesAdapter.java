package id.husni.moviestvcatalogue.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.robertlevonyan.views.chip.Chip;

import java.util.ArrayList;

import id.husni.moviestvcatalogue.R;
import id.husni.moviestvcatalogue.model.Series;
import id.husni.moviestvcatalogue.utilities.AppUtilities;

public class SeriesAdapter extends RecyclerView.Adapter<SeriesAdapter.ViewHolder> {
    ArrayList<Series> seriesArrayList = new ArrayList<>();
    Context context;

    public SeriesAdapter(Context context) {
        this.context = context;
    }

    public ArrayList<Series> getSeriesArrayList() {
        return seriesArrayList;
    }

    public void setSeriesArrayList(ArrayList<Series> items) {
        if (seriesArrayList != null) {
            seriesArrayList.clear();
        }
        seriesArrayList.addAll(items);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SeriesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.series_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SeriesAdapter.ViewHolder holder, int position) {
        holder.tvTitle.setText(seriesArrayList.get(position).getTitle());
        holder.chipRating.setText(String.valueOf(seriesArrayList.get(position).getVoteAverage()));
        holder.tvAiring.setText(seriesArrayList.get(position).getAiringDate());
        float nilaiRating = (float) (seriesArrayList.get(position).getVoteAverage() / 2);
        holder.ratingBar.setRating(nilaiRating);
        Glide.with(context)
                .load(AppUtilities.POSTER_FILM + seriesArrayList.get(position).getPosterPath())
                .into(holder.imageSeries);
    }

    @Override
    public int getItemCount() {
        return seriesArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvTitle;
        public Chip chipRating;
        public TextView tvAiring;
        public RatingBar ratingBar;
        public ImageView imageSeries;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvTitle = itemView.findViewById(R.id.tvSeriesTitle);
            chipRating = itemView.findViewById(R.id.chipSeriesRating);
            tvAiring = itemView.findViewById(R.id.tvSeriesAiring);
            ratingBar = itemView.findViewById(R.id.seriesRatingBar);
            imageSeries = itemView.findViewById(R.id.seriesImage);
        }
    }
}
