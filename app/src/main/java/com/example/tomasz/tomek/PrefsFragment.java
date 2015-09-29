package com.example.tomasz.tomek;

/**
 * Created by Tomasz on 2015-09-23.
 */

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;

public class PrefsFragment extends PreferenceFragment {

    private Switch fahrenheit;
    private Switch charge;
    private DataBaseManager DBhelper;
    private BatteryDatabase Batterydatabase;
    private fragmentMain main;
    private static final String PREFERENCES_NAME = "myPreferences";
    private static final String PREFERENCES_TEXT_FIELD = "textField";
    private static final String SETTING_CHECK_BOX = "checkbox_setting";
    private SharedPreferences preferences;
    private OnFragmentInteractionListener mListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parentViewGroup,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.settings, parentViewGroup, false);
        preferences = this.getActivity().getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
        fahrenheit = (Switch) rootView.findViewById(R.id.fahrenheit);
        charge = (Switch) rootView.findViewById(R.id.START);
        fahrenheit.setChecked(isCheckedSettingEnabled(SETTING_CHECK_BOX));
        charge.setChecked(isCheckedSettingEnabled("checkbox1"));
        fahrenheit.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                saveData("CHECKBOX", fahrenheit.isChecked());

            }
        });

        if (mListener != null) {
            mListener.onFragmentInteraction("Custom Title");
        }
        return rootView;

    }
    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
    @Override
    public void onPause() {
        super.onPause();
        setCheckedSettingEnabled(SETTING_CHECK_BOX,fahrenheit.isChecked());
        setCheckedSettingEnabled("checkbox1",charge.isChecked());
    }


    private void saveData(String key, boolean value) {
        SharedPreferences.Editor preferencesEditor = preferences.edit();
        preferencesEditor.putBoolean(key, value);
        preferencesEditor.commit();
    }

    private void loadPrefs() {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getActivity());
        boolean cbValue = sp.getBoolean("ISCHECKED", false);
        if (cbValue) {
            fahrenheit.setChecked(true);
        } else {
            fahrenheit.setChecked(false);
        }
    }
    private void setCheckedSettingEnabled(String key, boolean enabled) {
        PreferenceManager.getDefaultSharedPreferences(this.getActivity())
                .edit()
                .putBoolean(key, enabled)
                .apply();
    }
    private boolean isCheckedSettingEnabled(String key) {
        return PreferenceManager.getDefaultSharedPreferences(this.getActivity())
                .getBoolean(key, false);
    }
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(String title);
    }
}
