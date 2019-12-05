package id.husni.moviestvcatalogue.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.RemoteViews;

import id.husni.moviestvcatalogue.R;
import id.husni.moviestvcatalogue.activity.MainActivity;

/**
 * Implementation of App Widget functionality.
 */
public class MoviesWidget extends AppWidgetProvider {

    private static final String MOVIES_WIDGET_ACTION = "moviesWidgetAction" ;
    public static final String MOVIES_WIDGET_EXTRA_ITEM = "moviesWidgetExtraItem" ;

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        Intent intent = new Intent(context,MoviesWidgetService.class);
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));

        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.movies_widget);
        views.setRemoteAdapter(R.id.moviesStackImage, intent);
        views.setEmptyView(R.id.moviesStackImage, R.id.tvEmptyWidgetData);

        Intent newIntent = new Intent(context, MoviesWidget.class);
        newIntent.setAction(MOVIES_WIDGET_ACTION);
        newIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        newIntent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, newIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        views.setPendingIntentTemplate(R.id.moviesStackImage, pendingIntent);

        //update widget
       // context.startService(intent);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        if (intent.getAction() != null) {
            if (intent.getAction().equals(MOVIES_WIDGET_ACTION)) {
                Intent intentIntoApp = new Intent(context, MainActivity.class);
                context.startActivity(intentIntoApp);
            }
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

