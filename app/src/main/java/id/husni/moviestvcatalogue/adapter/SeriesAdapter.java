package id.husni.moviestvcatalogue.adapter;

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
import id.husni.moviestvcatalogue.detail.SeriesDetail;
import id.husni.moviestvcatalogue.model.Series;
import id.husni.moviestvcatalogue.utilities.AppUtilities;
import id.husni.moviestvcatalogue.utilities.CustomItemClick;

public class SeriesAdapter extends RecyclerView.Adapter<SeriesAdapter.ViewHolder> {
    private ArrayList<Series> seriesArrayList = new ArrayList<>();
    private Context context;

    public SeriesAdapter(Context context) {
        this.context = context;
    }

    public ArrayList<Series> getSeriesArrayList() {
        return seriesArrayList;
    }

    public void setSeriesArrayList(ArrayList<Series> items) {
        if (seriesArrayList.size() > 0) {
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
        holder.cvSeries.setOnClickListener(new CustomItemClick(position, new CustomItemClick.OnItemClick() {
            @Override
            public void onItemClicked(View view, int position) {
                Intent intent = new Intent(context, SeriesDetail.class);
                intent.putExtra(SeriesDetail.EXTRA_SERIES_DETAIL, seriesArrayList.get(position));
                intent.putExtra(SeriesDetail.EXTRA_POSITION_SERIES, position);
                if (context instanceof Activity) {
                    ((Activity) context).startActivityForResult(intent, AppUtilities.ADD_REQUEST_CODE);
                }
            }
        }));
    }

    @Override
    public int getItemCount() {
        return seriesArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CardView cvSeries;
        TextView tvTitle;
        Chip chipRating;
        TextView tvAiring;
        RatingBar ratingBar;
        ImageView imageSeries;

        ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvTitle = itemView.findViewById(R.id.tvSeriesTitle);
            chipRating = itemView.findViewById(R.id.chipSeriesRating);
            tvAiring = itemView.findViewById(R.id.tvSeriesAiring);
            ratingBar = itemView.findViewById(R.id.seriesRatingBar);
            imageSeries = itemView.findViewById(R.id.seriesImage);
            cvSeries = itemView.findViewById(R.id.cvSeries);
        }
    }
}
