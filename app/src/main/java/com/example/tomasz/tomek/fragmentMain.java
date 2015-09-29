package com.example.tomasz.tomek;

import android.app.Activity;
import android.app.Fragment;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothProfile;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.AudioManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.BatteryManager;
import android.os.Bundle;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.github.rahatarmanahmed.cpv.CircularProgressView;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link fragmentMain.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link fragmentMain#newInstance} factory method to
 * create an instance of this fragment.
 */
public class fragmentMain extends Fragment  {
    private boolean bolBroacastRegistred;
    Activity mActivity;
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        mActivity = activity;
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }

    }

    //DEKLARACJE ZMIENNYCH
    private TextView batteryInfo;
    private CircularProgressView batteryLevel;
    private TextView batteryCharge;
    private TextView batteryTemp;
    private TextView batteryHealth;
    private Button button;
    private WifiManager wifienable = null;
    private int brightness;
    private ContentResolver cResolver;
    private Window window;
    private ImageButton wifi;
    private ImageButton bluetooth;
    private ImageButton MobileData;
    private ConnectivityManager cm;
    private AudioManager audioManager;
    private ImageButton audio;
    private Vibrator vibrator;
    private ToggleButton auto;
    private TextView textcolor;
    private Button BatteryUsage;
    private Switch Fahrenheit;
    private SharedPreferences prefs;
    private static final String PREFERENCES_NAME = "myPreferences";
    //private static final String PREFERENCES_TEXT_FIELD = "textField";
    boolean temp;




    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment fragmentMain.
     */
    // TODO: Rename and change types and number of parameters
    public static fragmentMain newInstance(String param1, String param2) {
        fragmentMain fragment = new fragmentMain();

            Bundle args = new Bundle();
            args.putString(ARG_PARAM1, param1);
            args.putString(ARG_PARAM2, param2);
            fragment.setArguments(args);
        return fragment;
    }

    public fragmentMain() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    private BroadcastReceiver batteryInfoReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            int  health= intent.getIntExtra(BatteryManager.EXTRA_HEALTH,0);
            int  icon_small= intent.getIntExtra(BatteryManager.EXTRA_ICON_SMALL,0);
            int  level= intent.getIntExtra(BatteryManager.EXTRA_LEVEL,0);
            int  plugged= intent.getIntExtra(BatteryManager.EXTRA_PLUGGED,0);
            boolean  present= intent.getExtras().getBoolean(BatteryManager.EXTRA_PRESENT);
            int  scale= intent.getIntExtra(BatteryManager.EXTRA_SCALE, 0);
            int  status= intent.getIntExtra(BatteryManager.EXTRA_STATUS,0);
            String  technology= intent.getExtras().getString(BatteryManager.EXTRA_TECHNOLOGY);
            int  temperature= intent.getIntExtra(BatteryManager.EXTRA_TEMPERATURE, 0);
            int  voltage= intent.getIntExtra(BatteryManager.EXTRA_VOLTAGE,0);
            boolean isCharging = status == BatteryManager.BATTERY_STATUS_CHARGING ||
                    status == BatteryManager.BATTERY_STATUS_FULL;
            float tempTwo = ((float) temperature) / 10;
            float tempfahrenheit = 32 + (float)9/5*(float)temperature / 10;
            if (isCharging)
            {
                batteryCharge.setCompoundDrawablesWithIntrinsicBounds(0,0,0,0);
                batteryCharge.setText("CHARGING...");
            }
            else
            {
                batteryCharge.setCompoundDrawablesWithIntrinsicBounds(0,0,0,0);
                batteryCharge.setText("IN USE");
            }
            switch (health) {
                case 1: batteryHealth.setText("UNKNOWN");
                    break;
                case 2: batteryHealth.setText("GOOD");
                    break;
                case 3: batteryHealth.setText("OVERHEAT");
                    break;
                case 4: batteryHealth.setText("DEAD");
                    break;
                case 5: batteryHealth.setText("OVER VOLTAGE");
                    break;
                case 6: batteryHealth.setText("FAILURE");
                    break;
                case 7: batteryHealth.setText("COLD");
                    break;

            }


if (temp)
{
    batteryTemp.setText(tempfahrenheit + "\u00b0"+"F");
}
            else {
    batteryTemp.setText(tempTwo + "\u00b0" + "C");

}

    batteryLevel.setProgress(level);
    batteryInfo.setText(level + "%");

        }
    };

    private BroadcastReceiver MobileDataReceiver = new  BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            //boolean MobileDataState = intent.getBooleanExtra(ConnectivityManager.EXTRA_NO_CONNECTIVITY, false);
            NetworkInfo myNetworkInfo = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            if(myNetworkInfo.isConnected())
            {
                MobileData.setColorFilter(Color.WHITE);
            }else
            {
                MobileData.setColorFilter(Color.rgb(93, 96, 189));
            }

        }
    };
    private BroadcastReceiver AudioModeReceiver = new  BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            int mod=audioManager.getRingerMode();

            if(mod==AudioManager.RINGER_MODE_VIBRATE){
                audio.setColorFilter(Color.GRAY);
                audio.setImageResource(R.drawable.wibracje);
            }

            else if(mod==AudioManager.RINGER_MODE_NORMAL){
                audio.setColorFilter(Color.WHITE);
                audio.setImageResource(R.drawable.soundon);

            }

            else
            {
                audio.setColorFilter(Color.rgb(93, 96, 189));
                audio.setImageResource(R.drawable.soundoff);
            }
        }


    };

    private BroadcastReceiver BluetoothReceiver = new BroadcastReceiver() {
        private static final int FAIL = -1;
        @Override
        public void onReceive(Context context, Intent intent) {
// TODO: This method is called when the BroadcastReceiver is receiving
            // an Intent broadcast.
            String action = intent.getAction();            // Get intent's action string
            Bundle extras = intent.getExtras();            // Get all the Intent's extras

            if (extras == null) return;                    // All intents of interest have extras.

            switch (action) {
                case "android.bluetooth.adapter.action.STATE_CHANGED": {
                    bluetoothStateChanged(extras.getInt("android.bluetooth.adapter.extra.STATE", FAIL));
                    break;
                }
                case "android.bluetooth.a2dp.profile.action.CONNECTION_STATE_CHANGED": {
                    a2dpStateChanged(
                            extras.getInt("android.bluetooth.profile.extra.STATE", FAIL),
                            (BluetoothDevice) extras.get("android.bluetooth.device.extra.DEVICE"));
                    break;
                }
                case "android.bluetooth.device.action.BOND_STATE_CHANGED": {
                    bondStateChanged(
                            extras.getInt("android.bluetooth.device.extra.BOND_STATE", FAIL),
                            (BluetoothDevice) extras.get("android.bluetooth.device.extra.DEVICE"));
                    break;
                }
            }
        }
    };
    private void bluetoothStateChanged(int state) {
        switch (state) {
            case BluetoothAdapter.STATE_OFF:
                bluetooth.setColorFilter(Color.rgb(93, 96, 189));
                break;
            case BluetoothAdapter.STATE_TURNING_ON:
                bluetooth.setColorFilter(Color.GRAY);
                break;
            case BluetoothAdapter.STATE_ON:
                bluetooth.setColorFilter(Color.WHITE);
                break;
            case BluetoothAdapter.STATE_TURNING_OFF:
                break;
            // TODO: trigger changes in how widget handles clicks and displays devices
            //Log.d(TAG, "Bluetooth adapter state changed to: " + state);
        }
    }

    /**
     * Handles changes in widget operation when a device's A2DP connection state changes
     * @param device the BluetoothDevice whose A2DP connection state has changed
     * @param state the new state, one of the BluetoothProfile connection and transitioning states
     */
    private void a2dpStateChanged(int state, BluetoothDevice device) {
        switch (state) {
            case BluetoothProfile.STATE_DISCONNECTED:
                bluetooth.setColorFilter(Color.rgb(93, 96, 189));
                break;
            case BluetoothProfile.STATE_CONNECTING:
                bluetooth.setColorFilter(Color.GRAY);
                break;
            case BluetoothProfile.STATE_CONNECTED:
                bluetooth.setColorFilter(Color.WHITE);
                break;
            case BluetoothProfile.STATE_DISCONNECTING:
                break;
            // TODO: trigger changes in how widget handles clicks and displays this device
            //Log.d(TAG, "Bluetooth A2DP state changed to " + state + " for " + device.getName());
        }
    }

    /**
     * Handles changes in widget operation when a device's bond state changes
     * @param device the BluetoothDevice whose bond state has changed
     * @param state the new state, one of the BluetoothDevice BOND and transitioning states
     */
    private void bondStateChanged(int state, BluetoothDevice device) {
        switch (state) {
            case BluetoothDevice.BOND_NONE:
                break;
            case BluetoothDevice.BOND_BONDING:
                break;
            case BluetoothDevice.BOND_BONDED:
                break;
            // TODO: trigger changes in how widget handles clicks and displays this device
            //Log.d(TAG, "Bluetooth bond state changed to " + state + " for " + device.getName());
        }
    }




    private BroadcastReceiver WifiReceiver = new  BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            int wifiState = intent.getIntExtra(WifiManager.EXTRA_WIFI_STATE, WifiManager.WIFI_STATE_UNKNOWN);

            switch (wifiState) {
                case WifiManager.WIFI_STATE_DISABLING:

                    break;
                case WifiManager.WIFI_STATE_DISABLED:

                    wifi.setColorFilter(Color.rgb(93, 96, 189));
                    break;
                case WifiManager.WIFI_STATE_ENABLING:

                    wifi.setColorFilter(Color.GRAY);
                    break;
                case WifiManager.WIFI_STATE_ENABLED:

                    wifi.setColorFilter(Color.WHITE);
                    break;
                case WifiManager.WIFI_STATE_UNKNOWN:

                    break;
                default:
                    break;
            }

        }
    };
    private void setMobileDataEnabled(Context context, boolean enabled) {
        final ConnectivityManager conman =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        try {
            final Class conmanClass = Class.forName(conman.getClass().getName());
            final Field iConnectivityManagerField = conmanClass.getDeclaredField("mService");
            iConnectivityManagerField.setAccessible(true);
            final Object iConnectivityManager = iConnectivityManagerField.get(conman);
            final Class iConnectivityManagerClass = Class.forName(
                    iConnectivityManager.getClass().getName());
            final Method setMobileDataEnabledMethod = iConnectivityManagerClass
                    .getDeclaredMethod("setMobileDataEnabled", Boolean.TYPE);
            setMobileDataEnabledMethod.setAccessible(true);

            setMobileDataEnabledMethod.invoke(iConnectivityManager, enabled);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        View v = inflater.inflate(R.layout.fragment_main, container, false);
        View options = inflater.inflate(R.layout.settings,container,false);
        //*BATERIA*PRZYPISANIE ZMIENNYCH DO KOMPONENTOW
        batteryInfo = (TextView) v.findViewById(R.id.textView);
        prefs= this.getActivity().getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
        batteryLevel = (CircularProgressView) v.findViewById(R.id.progressbar);
        batteryTemp = (TextView) v.findViewById(R.id.textView5);
        batteryCharge = (TextView) v.findViewById(R.id.textView7);
        batteryHealth = (TextView) v.findViewById(R.id.textView8);
        button = (Button) v.findViewById(R.id.button2);
        BatteryUsage = (Button) v.findViewById(R.id.button);
        batteryCharge.setCompoundDrawablesWithIntrinsicBounds(R.drawable.electricity3, 0, 0, 0);
//*WIFI*PRZYPISANIE ZMIENNYCH DO KOMPONENTOW
        wifi = (ImageButton) v.findViewById(R.id.button1);
        bluetooth = (ImageButton) v.findViewById(R.id.button22);
        MobileData = (ImageButton) v.findViewById(R.id.button3);
        audio = (ImageButton) v.findViewById(R.id.button5);
        auto = (ToggleButton) v.findViewById(R.id.toggleButton);
        textcolor = (TextView) v.findViewById(R.id.textView9);
        Fahrenheit = (Switch) options.findViewById(R.id.fahrenheit);



        final Context tomek = mActivity.getApplicationContext();
        final WifiManager wifiManager = (WifiManager) mActivity.getSystemService(Context.WIFI_SERVICE);
        final BluetoothAdapter bluetoothAdapter = (BluetoothAdapter) mActivity.getSystemService(Context.BLUETOOTH_SERVICE);
        cm = (ConnectivityManager) mActivity.getSystemService(Context.CONNECTIVITY_SERVICE);
        audioManager = (AudioManager) mActivity.getSystemService(Context.AUDIO_SERVICE);
        vibrator = (Vibrator) mActivity.getSystemService(Context.VIBRATOR_SERVICE);
        cResolver =  mActivity.getContentResolver();

        final SeekBar mSeekBar = (SeekBar) v.findViewById(R.id.SeekBar);
        mSeekBar.setProgressDrawable(getResources()
                .getDrawable(R.drawable.seekbar));

        audio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int mod= audioManager.getRingerMode();
                if(mod == AudioManager.RINGER_MODE_NORMAL)
                {audio.setImageResource(R.drawable.wibracje);
                    audioManager.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);

                }
                else if(mod == AudioManager.RINGER_MODE_VIBRATE)
                {audio.setImageResource(R.drawable.soundoff);

                    audioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);
                }
                else if(mod == AudioManager.RINGER_MODE_SILENT)
                {audio.setImageResource(R.drawable.soundon);

                    audioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
                }
            }
        });
        auto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int checking = 0;
                float BrightnessValue = 0;
                try {
                    checking = Settings.System.getInt(cResolver, Settings.System.SCREEN_BRIGHTNESS_MODE);
                } catch (Settings.SettingNotFoundException e) {
                    e.printStackTrace();
                }
                if (checking == 1) {
                    Settings.System.putInt(cResolver, Settings.System.SCREEN_BRIGHTNESS_MODE, Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL);
                    auto.setChecked(false);
                } else {
                    Settings.System.putInt(cResolver, Settings.System.SCREEN_BRIGHTNESS_MODE, Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC);
                    auto.setChecked(true);
                    try {
                        BrightnessValue = Settings.System.getInt(cResolver, Settings.System.SCREEN_BRIGHTNESS);
                    } catch (Settings.SettingNotFoundException e) {
                        e.printStackTrace();

                    }
                    float lol = BrightnessValue;
                    mSeekBar.setProgress((int) BrightnessValue);
                }

            }
        });
        bluetooth.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if (!bluetoothAdapter.isEnabled()) {
                    bluetoothAdapter.enable();
                    bluetooth.setColorFilter(Color.WHITE);
                } else {
                    bluetoothAdapter.disable();
                    bluetooth.setColorFilter(Color.rgb(93, 96, 189));
                }
            }
        });


        BatteryUsage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_POWER_USAGE_SUMMARY);
                startActivity(intent);
            }
        });


        //WIFI*FUNCKJA ONCLICK ON/OFF WIFI
        wifi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!wifiManager.isWifiEnabled()) {
                    wifiManager.setWifiEnabled(true);
                    wifi.setColorFilter(Color.WHITE);
                } else {
                    wifiManager.setWifiEnabled(false);
                    wifi.setColorFilter(Color.rgb(93, 96, 189));
                }

            }
        });
        MobileData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NetworkInfo myNetworkInfo = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
                if (!myNetworkInfo.isConnected()) {
                    setMobileDataEnabled(tomek, true);
                    MobileData.setColorFilter(Color.WHITE);
                } else {
                    setMobileDataEnabled(tomek, false);
                    MobileData.setColorFilter(Color.rgb(93, 96, 189));
                }

            }
        });
        window = mActivity.getWindow();
        mSeekBar.setMax(255);
        mSeekBar.setKeyProgressIncrement(1);
        float curBrightnessValue = 0;
        try
        {
            //Get the current system brightness
            curBrightnessValue = android.provider.Settings.System.getInt(cResolver, Settings.System.SCREEN_BRIGHTNESS);
        }
        catch (Settings.SettingNotFoundException e)
        {
            //Throw an error case it couldn't be retrieved
            Log.e("Error", "Cannot access system brightness");
            e.printStackTrace();
        }
        int screen_brightness = (int) curBrightnessValue;
        mSeekBar.setProgress(screen_brightness);
        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progress = 0;

            @Override
            public void onProgressChanged(SeekBar seekBar, int progressValue, boolean fromUser) {
                //Set the minimal brightness level
                progress = progressValue;


                android.provider.Settings.System.putInt(cResolver,
                        android.provider.Settings.System.SCREEN_BRIGHTNESS,
                        progress);
            }


            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                android.provider.Settings.System.putInt(cResolver,
                        Settings.System.SCREEN_BRIGHTNESS_MODE, 0);
                auto.setChecked(false);
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {


            }
        });

        loadPrefs();

        return v;
    }
    private void loadPrefs() {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this.getActivity());
        boolean cbValue = sp.getBoolean("checkbox_setting", false);

        if(cbValue){
            temp=true;
        }else{
            temp=false;
        }


    }



    @Override
    public void onResume() {
        super.onResume();
        bolBroacastRegistred = true;
        mActivity.registerReceiver(batteryInfoReceiver, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
        mActivity.registerReceiver(WifiReceiver, new IntentFilter(WifiManager.WIFI_STATE_CHANGED_ACTION));
        mActivity.registerReceiver(BluetoothReceiver, new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED));
        mActivity.registerReceiver(MobileDataReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
        mActivity.registerReceiver(AudioModeReceiver, new IntentFilter(AudioManager.RINGER_MODE_CHANGED_ACTION));
    }
    @Override
    public void onPause() {
        super.onPause();


    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(String title);
    }


}
