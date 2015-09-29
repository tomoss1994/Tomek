package com.example.tomasz.tomek;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class LaunchOnPlugReceiver extends BroadcastReceiver {

    public LaunchOnPlugReceiver() {

    }

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        boolean start = sp.getBoolean("checkbox1",false);
        if(start) {
            Intent startActivity = new Intent(context, MainActivity.class);
            startActivity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(startActivity);
        }


    }
}
