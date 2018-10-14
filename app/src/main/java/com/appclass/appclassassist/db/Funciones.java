package com.appclass.appclassassist.db;

import android.app.Activity;

import com.google.firebase.auth.FirebaseAuth;

public class Funciones {

    public static String getBluetoothMAC(Activity activity) {
        return  android.provider.Settings.Secure.getString(activity.getContentResolver(), "bluetooth_address");
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
