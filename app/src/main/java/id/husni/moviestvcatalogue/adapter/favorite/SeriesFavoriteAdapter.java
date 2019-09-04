package id.husni.moviestvcatalogue.adapter.favorite;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.robertlevonyan.views.chip.Chip;

import java.util.ArrayList;

import id.husni.moviestvcatalogue.R;
import id.husni.moviestvcatalogue.database.table.SeriesHelper;
import id.husni.moviestvcatalogue.model.favorite.SeriesFavorite;
import id.husni.moviestvcatalogue.utilities.AppUtilities;

public class SeriesFavoriteAdapter extends RecyclerView.Adapter<SeriesFavoriteAdapter.ViewHolder> {
    ArrayList<SeriesFavorite> seriesFavoriteArrayList = new ArrayList<>();
    Context context;

    public SeriesFavoriteAdapter(Context context) {
        this.context = context;
    }

    public ArrayList<SeriesFavorite> getSeriesFavoriteArrayList() {
        return seriesFavoriteArrayList;
    }

    public void setSeriesFavoriteArrayList(ArrayList<SeriesFavorite> items) {
        if (seriesFavoriteArrayList.size() >0) {
            seriesFavoriteArrayList.clear();
        }
        seriesFavoriteArrayList.addAll(items);
        notifyDataSetChanged();
    }

    public void insertData(SeriesFavorite seriesFavorite) {
        seriesFavoriteArrayList.add(seriesFavorite);
        notifyItemInserted(seriesFavoriteArrayList.size() - 1);
    }

    public void deleteData(int position) {
        seriesFavoriteArrayList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeRemoved(position, seriesFavoriteArrayList.size());
    }

    @NonNull
    @Override
    public SeriesFavoriteAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.series_item_favorite, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final SeriesFavoriteAdapter.ViewHolder holder, final int position) {
        holder.tvTitle.setText(seriesFavoriteArrayList.get(position).getTitle());
        holder.tvRating.setText(seriesFavoriteArrayList.get(position).getVoteAverage());
        holder.tvAiring.setText(seriesFavoriteArrayList.get(position).getAiringDate());
        float ratingFavorite = Float.parseFloat(seriesFavoriteArrayList.get(position).getVoteAverage());
        float nilaiRating = ratingFavorite / 2;
        holder.ratingBar.setRating(nilaiRating);
        Glide.with(context)
                .load(AppUtilities.POSTER_FILM + seriesFavoriteArrayList.get(position).getPosterPath())
                .into(holder.imageSeriesFavorite);
        holder.deleteSeriesFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SeriesHelper helper = SeriesHelper.getInstance(v.getContext());
                helper.open();
                helper.deleteData(seriesFavoriteArrayList.get(position).getId());
                helper.close();
                deleteData(holder.getAdapterPosition());
                Toast.makeText(context, R.string.removeFromfavorite, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return seriesFavoriteArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvTitle;
        public Chip tvRating;
        public TextView tvAiring;
        public RatingBar ratingBar;
        public ImageView imageSeriesFavorite;
        public ImageButton deleteSeriesFavorite;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvSeriesTitleFavorite);
            tvRating = itemView.findViewById(R.id.chipSeriesRatingFavorite);
            tvAiring = itemView.findViewById(R.id.tvSeriesAiringFavorite);
            ratingBar = itemView.findViewById(R.id.seriesRatingBarFavorite);
            imageSeriesFavorite = itemView.findViewById(R.id.seriesImageFavorite);
            deleteSeriesFavorite = itemView.findViewById(R.id.deleteSeriesFavorite);
        }
    }
}
