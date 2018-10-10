package com.appclass.appclassassist;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

public class AppClassClases extends AppCompatActivity {

    private Button bRegresar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_app_class_clases);

        bRegresar = findViewById(R.id.bRegresar);




        bRegresar.setOnClickListener(e -> {
            regresarInicio();
        } );
    }

    private void regresarInicio() {
        Intent intent = getIntent();
        setResult(Activity.RESULT_OK, intent);
        finish();
    }
    @Override
    public void onBackPressed() {
        
        regresarInicio();
    }
}
