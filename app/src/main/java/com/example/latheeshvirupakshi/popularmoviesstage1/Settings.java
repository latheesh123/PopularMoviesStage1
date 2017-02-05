package com.example.latheeshvirupakshi.popularmoviesstage1;

import android.content.SharedPreferences;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.preference.PreferenceScreen;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Settings extends PreferenceActivity implements Preference.OnPreferenceChangeListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.pref_movie);

        PreferenceSummary(findPreference(getString(R.string.pref_key)));



    }

    private void PreferenceSummary(Preference preference)
    {
        //Preference Change Listener

        preference.setOnPreferenceChangeListener(this);
        Object value1=PreferenceManager.getDefaultSharedPreferences(preference.getContext()).getString(preference.getKey(),"");
        onPreferenceChange(preference,value1);
    }


    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {

        String value=newValue.toString();
        if (preference instanceof ListPreference) {
            ListPreference listPreference = (ListPreference) preference;
            int prefIndex = listPreference.findIndexOfValue(value);
            if (prefIndex >= 0) {

                listPreference.setSummary(listPreference.getEntries()[prefIndex]);
            }
            else
            {
                preference.setSummary(value);

            }
        }
        return true;
    }
}
