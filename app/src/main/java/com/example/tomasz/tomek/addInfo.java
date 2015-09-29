package com.example.tomasz.tomek;

import android.app.Activity;
import android.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link addInfo.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link addInfo#newInstance} factory method to
 * create an instance of this fragment.
 */
public class addInfo extends Fragment {
    private TextView Btech;
    private TextView Bvolt;
    private TextView Benergy;
    private TextView Bhealth;
    Activity mActivity;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
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
     * @return A new instance of fragment add_info.
     */
    // TODO: Rename and change types and number of parameters
    public static addInfo newInstance(String param1, String param2) {
        addInfo fragment = new addInfo();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public addInfo() {
        // Required empty public constructor
    }
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity=activity;
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        View view = inflater.inflate(R.layout.add_info, container, false);
        Btech = (TextView) view.findViewById(R.id.textView14);
        Bvolt = (TextView) view.findViewById(R.id.textView15);
        Benergy = (TextView) view.findViewById(R.id.textView17);
        Bhealth = (TextView) view.findViewById(R.id.textView16);
        return view;

    }


    @Override
    public void onResume() {
        super.onResume();

        mActivity.registerReceiver(batteryReceiver, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));

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


    private BroadcastReceiver batteryReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            int health = intent.getIntExtra(BatteryManager.EXTRA_HEALTH, 0);
            int icon_small = intent.getIntExtra(BatteryManager.EXTRA_ICON_SMALL, 0);
            int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0);
            int plugged = intent.getIntExtra(BatteryManager.EXTRA_PLUGGED, 0);
            boolean present = intent.getExtras().getBoolean(BatteryManager.EXTRA_PRESENT);
            int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, 0);
            int status = intent.getIntExtra(BatteryManager.EXTRA_STATUS, 0);
            String technology = intent.getExtras().getString(BatteryManager.EXTRA_TECHNOLOGY);
            int temperature = intent.getIntExtra(BatteryManager.EXTRA_TEMPERATURE, 0);
            int voltage = intent.getIntExtra(BatteryManager.EXTRA_VOLTAGE, 0);


            float tempTwo = ((float) temperature) / 10;

            switch (health) {
                case 1:
                    Bhealth.setText("UNKNOWN");
                    break;
                case 2:
                    Bhealth.setText("GOOD");
                    break;
                case 3:
                    Bhealth.setText("OVERHEAT");
                    break;
                case 4:
                    Bhealth.setText("DEAD");
                    break;
                case 5:
                    Bhealth.setText("OVER VOLTAGE");
                    break;
                case 6:
                    Bhealth.setText("FAILURE");
                    break;
                case 7:
                    Bhealth.setText("COLD");
                    break;

            }
            Bvolt.setText(voltage + "mV");
            Btech.setText(technology);


        }
    };


}
