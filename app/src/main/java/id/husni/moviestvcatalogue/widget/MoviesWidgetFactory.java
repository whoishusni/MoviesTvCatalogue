package id.husni.moviestvcatalogue.widget;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Binder;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.bumptech.glide.Glide;

import java.util.concurrent.ExecutionException;

import id.husni.moviestvcatalogue.model.favorite.MoviesFavorite;
import id.husni.moviestvcatalogue.utilities.AppUtilities;

import static id.husni.moviestvcatalogue.database.DatabaseContract.POSTER;
import static id.husni.moviestvcatalogue.database.DatabaseContract.URI_MOVIES;

public class MoviesWidgetFactory implements RemoteViewsService.RemoteViewsFactory {
    private Context context;
    private Cursor cursor;

    public MoviesWidgetFactory(Context context) {
        this.context = context;
    }

    @Override
    public void onCreate() {
        cursor = context.getContentResolver().query(URI_MOVIES,
                null,
                null,
                null,
                null);
    }

    @Override
    public void onDataSetChanged() {

        final long identifyToken = Binder.clearCallingIdentity();

        cursor = context.getContentResolver().query(URI_MOVIES,
                null,
                null,
                null,
                null);

        Binder.restoreCallingIdentity(identifyToken);
    }

    @Override
    public void onDestroy() {
        if (cursor != null) {
            cursor.close();
        }

    }

    @Override
    public int getCount() {
        if (cursor != null) {
            return cursor.getCount();
        } else {
            return 0;
        }
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews views = new RemoteViews(context.getPackageName(), id.husni.moviestvcatalogue.R.layout.movies_widget_item);
        MoviesFavorite moviesFavorite = new MoviesFavorite();
        cursor.moveToPosition(position);
        moviesFavorite.setPosterPath(cursor.getString(cursor.getColumnIndexOrThrow(POSTER)));
        try {
            Bitmap bitmap = Glide.with(context)
                    .asBitmap()
                    .load(AppUtilities.POSTER_FILM_DETAIL + moviesFavorite.getPosterPath())
                    .submit()
                    .get();
            views.setImageViewBitmap(id.husni.moviestvcatalogue.R.id.moviesImageWidget,bitmap);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        Bundle bundle = new Bundle();
        bundle.putInt(MoviesWidget.MOVIES_WIDGET_EXTRA_ITEM, position);

        Intent fillIntent = new Intent();
        fillIntent.putExtras(bundle);

        views.setOnClickFillInIntent(id.husni.moviestvcatalogue.R.id.moviesImageWidget, fillIntent);

        return views;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
}
