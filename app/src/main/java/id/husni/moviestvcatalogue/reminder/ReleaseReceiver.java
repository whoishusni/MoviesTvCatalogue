package id.husni.moviestvcatalogue.reminder;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.lifecycle.MutableLiveData;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import cz.msebera.android.httpclient.Header;
import id.husni.moviestvcatalogue.BuildConfig;
import id.husni.moviestvcatalogue.R;
import id.husni.moviestvcatalogue.activity.MainActivity;
import id.husni.moviestvcatalogue.activity.MovieReleaseActivity;
import id.husni.moviestvcatalogue.model.release.MovieRelease;
import id.husni.moviestvcatalogue.utilities.AppUtilities;

public class ReleaseReceiver extends BroadcastReceiver {

    private static final String TYPE_RELEASE = "typeRelease" ;
    private static final String CHANNEL_ID =  "channelIdRelease";
    private static final String MOVIE_GROUP = "movieGroup" ;
    private static final String CHANNEL_NAME = "channelName" ;

    private int notifId = 0;
    private MovieRelease movieRelease;
    private ArrayList<MovieRelease> movieReleaseArrayList = new ArrayList<>();


    @Override
    public void onReceive(Context context, Intent intent) {
        setDataRelease(context);
    }

    public void setDataRelease(final Context context) {
        final MutableLiveData<ArrayList<MovieRelease>> mutableLiveData = new MutableLiveData<>();
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String dateFormatted = String.valueOf(simpleDateFormat.format(date.getTime()));

        String URL = "https://api.themoviedb.org/3/discover/movie?api_key=" + BuildConfig.API_TMDB + "&primary_release_date.gte=" + dateFormatted + "&primary_release_date.lte=" + dateFormatted;
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(URL, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String result = new String(responseBody);
                    JSONObject object = new JSONObject(result);
                    JSONArray array = object.getJSONArray("results");

                    for (int i = 0; i < array.length(); i++) {
                        JSONObject releaseObject = array.getJSONObject(i);
                        movieRelease = new MovieRelease(releaseObject);
                        movieReleaseArrayList.add(movieRelease);
                        notifId++;
                    }
                    showNotif(context);
                    mutableLiveData.postValue(movieReleaseArrayList);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });

    }

    private void showNotif(Context context) {
        String message = null;

        NotificationCompat.Builder builder;
        Intent intent = new Intent(context, MovieReleaseActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);

        PendingIntent contentPending = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Uri notifSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        if (notifId < 2) {
             builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                    .setSound(notifSound)
                    .setSmallIcon(R.drawable.ic_new_releases)
                    .setAutoCancel(true)
                    .setContentTitle(context.getString(R.string.release_reminder))
                    .setGroup(MOVIE_GROUP)
                    .setContentIntent(contentPending)
                    .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000});
            for (int i = 0; i < movieReleaseArrayList.size(); i++) {
                builder.setContentText(context.getString(R.string.title) + " : " + movieReleaseArrayList.get(i).getTitle());
            }
        } else {
            NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle()
                    .setBigContentTitle(context.getString(R.string.release_reminder))
                    .setSummaryText(notifId + " New Movies Release");
            for (int i = 0; i < movieReleaseArrayList.size() ; i++) {
                inboxStyle.addLine(context.getString(R.string.title) + " : " + movieReleaseArrayList.get(i).getTitle());
            }

            builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                    .setSound(notifSound)
                    .setSmallIcon(R.drawable.ic_new_releases)
                    .setAutoCancel(true)
                    .setContentTitle(context.getString(R.string.release_reminder))
                    .setGroup(MOVIE_GROUP)
                    .setStyle(inboxStyle)
                    .setContentIntent(contentPending)
                    .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000});
            for (int i = 0; i < movieReleaseArrayList.size(); i++) {
                builder.setContentText(context.getString(R.string.title) + " : " + movieReleaseArrayList.get(i).getTitle());
            }

        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
            notificationChannel.setVibrationPattern(new long[]{1000, 1000, 1000, 1000, 1000});
            notificationChannel.enableVibration(true);
            builder.setChannelId(CHANNEL_ID);

            if (notificationManager != null) {
                notificationManager.createNotificationChannel(notificationChannel);
            }
        }
        Notification notification = builder.build();
        if (notificationManager != null) {
            notificationManager.notify(notifId,notification);
        }

    }

    public void setReleaseReminder(Context context) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        Calendar calendar = Calendar.getInstance();

        calendar.set(Calendar.HOUR_OF_DAY, 8);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        Intent intent = new Intent(context, ReleaseReceiver.class);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, AppUtilities.RELEASE_REQUEST_CODE, intent, 0);
        if (alarmManager != null) {
            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
        }
        Toast.makeText(context, id.husni.moviestvcatalogue.R.string.toast_setup_reminder, Toast.LENGTH_SHORT).show();
    }

    public void setCancelReleaseReminder(Context context) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, ReleaseReceiver.class);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, AppUtilities.RELEASE_REQUEST_CODE, intent, 0);
        pendingIntent.cancel();

        if (alarmManager != null) {
            alarmManager.cancel(pendingIntent);
        }
        Toast.makeText(context, id.husni.moviestvcatalogue.R.string.toast_nosetup_reminder, Toast.LENGTH_SHORT).show();
    }
}
