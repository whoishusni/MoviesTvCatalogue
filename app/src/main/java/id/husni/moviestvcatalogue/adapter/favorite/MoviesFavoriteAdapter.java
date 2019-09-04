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
import id.husni.moviestvcatalogue.database.table.MoviesHelper;
import id.husni.moviestvcatalogue.database.table.SeriesHelper;
import id.husni.moviestvcatalogue.model.favorite.MoviesFavorite;
import id.husni.moviestvcatalogue.utilities.AppUtilities;

public class MoviesFavoriteAdapter extends RecyclerView.Adapter<MoviesFavoriteAdapter.ViewHolder> {
    ArrayList<MoviesFavorite> moviesFavoriteArrayList = new ArrayList<>();
    Context context;

    public MoviesFavoriteAdapter(Context context) {
        this.context = context;
    }

    public ArrayList<MoviesFavorite> getMoviesFavoriteArrayList() {
        return moviesFavoriteArrayList;
    }

    public void setMoviesFavoriteArrayList(ArrayList<MoviesFavorite> items) {
        if (moviesFavoriteArrayList.size() > 0) {
            moviesFavoriteArrayList.clear();
        }
        moviesFavoriteArrayList.addAll(items);
        notifyDataSetChanged();
    }

    public void insertData(MoviesFavorite favorite) {
        moviesFavoriteArrayList.add(favorite);
        notifyItemInserted(moviesFavoriteArrayList.size() - 1);
    }

    public void deleteData(int position) {
        moviesFavoriteArrayList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, moviesFavoriteArrayList.size());
    }

    @NonNull
    @Override
    public MoviesFavoriteAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.movies_item_favorite, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MoviesFavoriteAdapter.ViewHolder holder, final int position) {
        holder.tvTitle.setText(moviesFavoriteArrayList.get(position).getTitle());
        holder.chipRating.setText(moviesFavoriteArrayList.get(position).getVoteAverage());
        holder.tvReleaseDate.setText(moviesFavoriteArrayList.get(position).getReleaseDate());
        float ratingFavorite = Float.parseFloat(moviesFavoriteArrayList.get(position).getVoteAverage());
        float nilaiRating = ratingFavorite / 2;
        holder.ratingBar.setRating(nilaiRating);
        Glide.with(context)
                .load(AppUtilities.POSTER_FILM_DETAIL + moviesFavoriteArrayList.get(position).getPosterPath())
                .into(holder.imageMoviesFavorite);
        holder.deleteMoviesFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MoviesHelper helper = MoviesHelper.getInstance(v.getContext());
                helper.open();
                helper.delete(moviesFavoriteArrayList.get(position).getId());
                helper.close();
                deleteData(holder.getAdapterPosition());
                Toast.makeText(context, R.string.removeFromfavorite, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return moviesFavoriteArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvTitle;
        public Chip chipRating;
        public TextView tvReleaseDate;
        public RatingBar ratingBar;
        public ImageView imageMoviesFavorite;
        public ImageButton deleteMoviesFavorite;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvMovieTitleFavorite);
            chipRating = itemView.findViewById(R.id.chipMovieRatingFavorite);
            tvReleaseDate = itemView.findViewById(R.id.tvMovieReleaseFavorite);
            ratingBar = itemView.findViewById(R.id.movieRatingBarFavorite);
            imageMoviesFavorite = itemView.findViewById(R.id.movieImageFavorite);
            deleteMoviesFavorite = itemView.findViewById(R.id.deleteMoviesFavorite);
        }
    }
}
