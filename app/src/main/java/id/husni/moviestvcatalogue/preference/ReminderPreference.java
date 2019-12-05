package id.husni.moviestvcatalogue.preference;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.SwitchPreference;

import id.husni.moviestvcatalogue.R;
import id.husni.moviestvcatalogue.reminder.DailyReceiver;
import id.husni.moviestvcatalogue.reminder.ReleaseReceiver;

public class ReminderPreference extends PreferenceFragmentCompat implements SharedPreferences.OnSharedPreferenceChangeListener {

    private String DAILY;
    private String RELEASE;

    private SwitchPreference dailySwitchPref;
    private SwitchPreference releaseSwitchPref;
    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.reminder_preference);

        init();
        summary();
    }

    private void init() {
        DAILY = getResources().getString(R.string.key_reminder_daily);
        RELEASE = getResources().getString(R.string.key_reminder_release);
        dailySwitchPref = findPreference(DAILY);
        releaseSwitchPref = findPreference(RELEASE);
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
        releaseSwitchPref.setChecked(sharedPreferences.getBoolean(RELEASE, false));
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        DailyReceiver dailyReceiver = new DailyReceiver();
        ReleaseReceiver releaseReceiver = new ReleaseReceiver();
        if (key.equals(DAILY)) {
            if (dailySwitchPref.isChecked()) {
                dailyReceiver.setDailyReminder(getContext());
            } else {
                dailyReceiver.setCancelDailyReminder(getContext());
            }
        }
        if (key.equals(RELEASE)) {
            if (releaseSwitchPref.isChecked()) {
                releaseReceiver.setReleaseReminder(getContext());
            } else {
                releaseReceiver.setCancelReleaseReminder(getContext());
            }
        }
    }
}
