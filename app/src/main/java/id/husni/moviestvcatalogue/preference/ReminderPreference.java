package id.husni.moviestvcatalogue.preference;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.SwitchPreference;

import id.husni.moviestvcatalogue.R;
import id.husni.moviestvcatalogue.broadcast.DailyReceiver;

public class ReminderPreference extends PreferenceFragmentCompat implements SharedPreferences.OnSharedPreferenceChangeListener {

    private String DAILY;

    private SwitchPreference dailySwitchPref;
    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.reminder_preference);

        init();
        summary();
    }

    private void init() {
        DAILY = getResources().getString(R.string.key_reminder_daily);
        dailySwitchPref = findPreference(DAILY);
    }

    @Override
    public void onResume() {
        super.onResume();
        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
    }

    private void summary() {
        SharedPreferences sharedPreferences = getPreferenceManager().getSharedPreferences();
        dailySwitchPref.setChecked(sharedPreferences.getBoolean(DAILY, false));
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        DailyReceiver dailyReceiver = new DailyReceiver();
        if (key.equals(DAILY)) {
            if (dailySwitchPref.isChecked()) {
                dailyReceiver.setDailyReminder(getContext(),dailyReceiver.TYPE_DAILY);
            } else {
                dailyReceiver.setCancelDailyReminder(getContext(),dailyReceiver.TYPE_DAILY);
            }
        }
    }
}
