package com.media.app.Utils;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.telephony.gsm.GsmCellLocation;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import static android.content.Context.LOCATION_SERVICE;
import static com.media.app.Utils.logger.logg;


/**
 * Created by prabeer.kochar on 03-08-2017.
 */

public final class utility {
    public  static String DeviceDetails(){
        String manufacturer = Build.MANUFACTURER;
        String model = Build.MODEL;
        String product =  Build.PRODUCT;
        return product+'|'+model+'|'+manufacturer;
    }

    public static String ShellExecuter(String command) {

        StringBuffer output = new StringBuffer();

        Process p;
        try {
            p = Runtime.getRuntime().exec(command);

            // DataOutputStream os = new DataOutputStream(p.getOutputStream());

            //os.writeBytes("mount -o remount,rw /system\n");
            p.waitFor();
            BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));

            String line = "";

            while ((line = reader.readLine())!= null) {
                output.append(line + "\n");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        String response = output.toString();
        return response;

    }

    public static String operator(Context con) {
        int mccSlot1 = -1;
        int mccSlot2 = -1;
        int mncSlot1 = -1;
        int mncSlot2 = -1;

        SubscriptionManager subManager = (SubscriptionManager) con.getSystemService(Context.TELEPHONY_SUBSCRIPTION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
            if (subManager.getActiveSubscriptionInfoCount() >= 1) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
                    try {
                        mccSlot1 = subManager.getActiveSubscriptionInfoForSimSlotIndex(0).getMcc();
                    }catch(Exception e){
                        mccSlot1 = -1;
                    }
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
                    try {
                        mncSlot1 = subManager.getActiveSubscriptionInfoForSimSlotIndex(0).getMnc();
                    }catch(Exception e){
                        mncSlot1 = -1;
                    }
                }
            }
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
            if (subManager.getActiveSubscriptionInfoCount() >= 2) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
                    try {
                        mccSlot2 = subManager.getActiveSubscriptionInfoForSimSlotIndex(1).getMcc();
                    }catch (Exception e)
                    {
                        mccSlot2 = -1;
                    }
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
                    try {
                        mncSlot2 = subManager.getActiveSubscriptionInfoForSimSlotIndex(1).getMnc();
                    }catch(Exception e){
                        mncSlot2 = -1;
                    }

                }
            }
        }
        return Integer.toString(mncSlot1)+"|"+ Integer.toString(mccSlot1)+"|"+ Integer.toString(mccSlot2)+"|"+ Integer.toString(mncSlot2);
    }

    private static double lat = -1.0;

    private static double lon = -1.0;
    // The minimum distance to change Updates in meters
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10; // 10 meters

    // The minimum time between updates in milliseconds
    private static final long MIN_TIME_BW_UPDATES = 1000 * 60 * 1; // 1 minute

    public static String Gloc(Context paramContext) throws Exception {
        try {
            LocationManager locationManager = (LocationManager)
                    paramContext.getSystemService(LOCATION_SERVICE);
            // Criteria criteria = new Criteria();
            // String bestProvider = locationManager.getBestProvider(criteria, true);
            String locationProvider;
            Location location = null;
            LocationListener locationListener = new LocationListener() {
                public void onLocationChanged(Location location) {
                    // Called when a new location is found by the network location provider
                    lat = location.getLatitude();
                    lon = location.getLongitude();
                }

                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {

                }

                @Override
                public void onProviderEnabled(String provider) {

                }

                @Override
                public void onProviderDisabled(String provider) {

                }
            };
            if (locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
                locationProvider = LocationManager.NETWORK_PROVIDER;
                if (location == null) {
                    locationManager.requestLocationUpdates(locationProvider, MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, locationListener);
                    if (locationManager != null) {
                        location = locationManager.getLastKnownLocation(locationProvider);
                        if (location != null) {
                            lat = location.getLatitude();
                            lon = location.getLongitude();
                        }
                    }
                }
                logg("GPS:From Network");
            } else if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                locationProvider = LocationManager.GPS_PROVIDER;
                if (location == null) {
                    locationManager.requestLocationUpdates(locationProvider, MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, locationListener);
                    if (locationManager != null) {
                        location = locationManager.getLastKnownLocation(locationProvider);
                        if (location != null) {
                            lat = location.getLatitude();
                            lon = location.getLongitude();
                        }
                    }
                }
                logg("GPS:From GPS");
            } else if (locationManager.isProviderEnabled(LocationManager.PASSIVE_PROVIDER)) {
                locationProvider = LocationManager.PASSIVE_PROVIDER;
                if (location == null) {
                    locationManager.requestLocationUpdates(locationProvider, MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, locationListener);
                    if (locationManager != null) {
                        location = locationManager.getLastKnownLocation(locationProvider);
                        if (location != null) {
                            lat = location.getLatitude();
                            lon = location.getLongitude();
                        }
                    }
                }
                logg("GPS:From Passive");
            } else {
                location = null;
                logg("GPS:null");
            }
            try {
                lat = location.getLatitude();
                lon = location.getLongitude();
            } catch (NullPointerException e) {
                lat = -1.0;
                lon = -1.0;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return Double.toString(lat) + "|" + Double.toString(lon);
    }


        public static String imi(Context context) {
            try {
                TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
                return String.valueOf(tm.getDeviceId());
            }catch(Exception e){
                return "99999999";
            }
        }
    public static int GetCellid(Context paramContext) throws Exception {
        {
            GsmCellLocation localGsmCellLocation = (GsmCellLocation) ((TelephonyManager) paramContext.getSystemService(Context.TELEPHONY_SERVICE)).getCellLocation();
            if (isUMTS(paramContext)) {
                if (localGsmCellLocation != null) {
                    return localGsmCellLocation.getLac() & 0xFFFF;
                }
            } else if (localGsmCellLocation != null) {
                int i = localGsmCellLocation.getLac();
                return i;
            }
            return -1;
        }
    }

    public static boolean isUMTS(Context paramContext) {
        switch (((TelephonyManager) paramContext.getSystemService(Context.TELEPHONY_SERVICE)).getNetworkType()) {
            default:
                return false;
            case 8:
                return true;
            case 10:
                return true;
            case 9:
                return true;
            case 3:
                return true;
        }

    }

}
