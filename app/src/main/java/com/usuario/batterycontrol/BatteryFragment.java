package com.usuario.batterycontrol;

import android.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * Created by usuario on 13/02/17.
 */

public class BatteryFragment extends Fragment {
    private ProgressBar pbLevel;
    private TextView txvLevel;
    private ImageView imgStatus;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_layout, null);

        pbLevel = (ProgressBar) rootView.findViewById(R.id.pb_level);
        txvLevel = (TextView) rootView.findViewById(R.id.txv_level);
        imgStatus = (ImageView) rootView.findViewById(R.id.img_status);

        return rootView;
    }


    @Override
    public void onResume() {
        super.onResume();

        //Crear un intent filter para la acción Intent.ACTION_BATTERY_CHANGED.
        IntentFilter intentFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);

        //Registrar BroadcastReciever.
        getActivity().registerReceiver(broadcastReceiver, intentFilter);
    }


    @Override
    public void onPause() {
        super.onPause();
        getActivity().unregisterReceiver(broadcastReceiver);
    }


    /**
     * BroadCast que depende del ciclo de vida del Fragment.
     */
    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            //Leer la información que llega del intent: level y status.
            int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0);
            pbLevel.setProgress(level);
            txvLevel.setText(level + "%");

            //El estado en el que está la batería.
            int status = intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1);

            switch (status) {
                case BatteryManager.BATTERY_STATUS_CHARGING:
                    imgStatus.setImageResource(R.drawable.charging);
                    break;

                case BatteryManager.BATTERY_STATUS_FULL:
                    imgStatus.setImageResource(R.drawable.full);
                    break;

                case BatteryManager.BATTERY_STATUS_DISCHARGING:
                    imgStatus.setImageResource(R.drawable.unplugged);
                    break;
            }
        }
    };
}
