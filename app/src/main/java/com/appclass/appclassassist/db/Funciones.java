package com.appclass.appclassassist.db;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.os.Build;

import com.google.firebase.auth.FirebaseAuth;

public class Funciones {

    public static String getBluetoothMAC(Activity activity) {


        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            return null;
        }
        if(android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return  android.provider.Settings.Secure.getString(activity.getContentResolver(), "bluetooth_address");
        }
        if(android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return  BluetoothAdapter.getDefaultAdapter().getAddress();
        }

        return null;





    }

    public static String getCorreo() {
        return FirebaseAuth.getInstance().getCurrentUser().getEmail().toString();

    }
    public static String getCorreoFix(String correo) {
        return correo.replace(".", "+");
    }
    public static String getCorreoFix() {
        return getCorreo().replace(".", "+");
    }
}
