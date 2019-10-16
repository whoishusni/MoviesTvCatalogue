package id.husni.moviestvcatalogue.broadcast;

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

import java.util.Calendar;

import id.husni.moviestvcatalogue.R;
import id.husni.moviestvcatalogue.utilities.AppUtilities;

public class DailyReceiver extends BroadcastReceiver {

    private static final String CHANNEL_ID ="daily_channel_id" ;
    private static final String CHANNEL_NAME = "daily_channel_name" ;
    public static final String TYPE_DAILY ="type_daily" ;


    @Override
    public void onReceive(Context context, Intent intent) {
        showDailyNotification(context);
    }

    private void showDailyNotification(Context context) {
        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSound(alarmSound)
                .setContentTitle(context.getString(R.string.daily_reminder))
                .setContentText(context.getString(R.string.daily_reminder_message))
                .setSmallIcon(R.drawable.ic_reminder)
                .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000});

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
            channel.enableVibration(true);
            channel.setVibrationPattern(new long[]{1000,1000,1000,1000,1000});
            builder.setChannelId(CHANNEL_ID);

            if (manager != null) {
                manager.createNotificationChannel(channel);
            }

        }

        int notifId = 10;
        Notification notification = builder.build();
        if (manager != null) {
            manager.notify(notifId,notification);
        }
    }

    public void setDailyReminder(Context context, String type) {

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 7);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.SECOND, 0);

        Intent intent = new Intent(context, DailyReceiver.class);
        intent.putExtra(TYPE_DAILY, type);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, AppUtilities.DAILY_REQUEST_CODE, intent, 0);

        if (alarmManager != null) {
            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),AlarmManager.INTERVAL_DAY,pendingIntent);
        }
        Toast.makeText(context, R.string.toast_setup_daily, Toast.LENGTH_SHORT).show();
    }

    public void setCancelDailyReminder(Context context, String type) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, DailyReceiver.class);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, AppUtilities.DAILY_REQUEST_CODE, intent, 0);
        pendingIntent.cancel();

        if (alarmManager != null) {
            alarmManager.cancel(pendingIntent);
        }
        Toast.makeText(context, R.string.toast_notsetup_daily, Toast.LENGTH_SHORT).show();
    }
}
