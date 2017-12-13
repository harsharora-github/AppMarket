package com.media.app.BroadCastReceivers;

import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.media.app.DataBases.databaseHandler;


public class bluetoothStart extends BroadcastReceiver {
    public bluetoothStart() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        databaseHandler BDB = new databaseHandler(context);
        if (BluetoothAdapter.ACTION_STATE_CHANGED.equals(action)) {
            Log.d("btt", String.valueOf((intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, -1))));
            if ((intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, -1)) == BluetoothAdapter.STATE_OFF){
                if(BDB.insertBTdata("STATE_OFF")) {
                    Log.d("btt", "BL STATE_OFF");
                }else{
                    Log.d("btt", "BL STATE_OFF Fail");
                }
                // Bluetooth is disconnected, do handling here
            }else if((intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, -1)) == BluetoothAdapter.STATE_ON){
                if(BDB.insertBTdata("STATE_ON")){
                    Log.d("btt","BL STATE_ON");
                }else{
                    Log.d("btt","BL STATE_ON fail");
                }
            }else if((intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, -1)) == BluetoothAdapter.STATE_CONNECTED){
                BDB.insertBTdata("STATE_CONNECTED");
                Log.d("btt","BL STATE_CONNECTED");
            }else if ((intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, -1)) == BluetoothAdapter.STATE_DISCONNECTED){
                BDB.insertBTdata("STATE_DISCONNECTED");
                Log.d("btt","BL STATE_DISCONNECTED");
            }
        }
    }
}
