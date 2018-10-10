package com.appclass.appclassassist;

import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class AppClassAssist extends AppCompatActivity {
    ImageView ivAsistir;
    private int tiempoVisible=60;
    private static int btRequestCode = 10;

    private Button bRegistrar;
    private EditText etCorreo;
    private EditText etCodigo;

    private  boolean isVisibleBT;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_app_class_assist);

        BluetoothAdapter BTAdapter = BluetoothAdapter.getDefaultAdapter();
        if(BTAdapter == null && false) {
            new AlertDialog.Builder(this)
                    .setTitle(getString(R.string.noCompatible))
                    .setMessage(getString(R.string.noBluetooth))
                    .setPositiveButton(getString(R.string.salir), (dialog, which) -> System.exit(0))
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        }
        else {


            ivAsistir = findViewById(R.id.ivAsistir);
            bRegistrar = findViewById(R.id.bAdministrarClases);

            ivAsistir.setOnClickListener(e -> {
                if(!isVisibleBT) {
                    Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
                    discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, tiempoVisible);
                    startActivityForResult(discoverableIntent, btRequestCode);
                }

            });

            bRegistrar.setOnClickListener(e -> {
                Intent intent = new Intent(this, AppClassLogin.class);
                startActivity(intent);

            });
        }

        isVisibleBT = false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode==btRequestCode) {
            if(resultCode==tiempoVisible) {

                ivAsistir.setBackgroundResource(R.drawable.aqui_si);
                isVisibleBT = true;
                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        ivAsistir.setBackgroundResource(R.drawable.aqui_no);
                        isVisibleBT=false;
                    }
                }, tiempoVisible*1000);
            }
        }
    }
}
