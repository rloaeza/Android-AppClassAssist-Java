package com.appclass.appclassassist;

import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

public class AppClassAssist extends AppCompatActivity {
    ImageView ivAsistir;
    private int tiempoVisible=60;
    private static int btRequestCode = 10;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_class_assist);

        ivAsistir = findViewById(R.id.ivAsistir);


        ivAsistir.setOnClickListener(e->{
            Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
            discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, tiempoVisible);
            startActivityForResult(discoverableIntent, btRequestCode);

        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode==btRequestCode) {
            if(resultCode==tiempoVisible) {

                ivAsistir.setBackgroundResource(R.drawable.asistir_si);
                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        ivAsistir.setBackgroundResource(R.drawable.asistir_no);
                    }
                }, tiempoVisible*1000);
            }
        }
    }
}
