package com.appclass.appclassassist;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AppClassLogin extends AppCompatActivity {

    private Button bRegresar;
    private Button bRegistrar;
    private Button bOlvideClave;
    private Button bAcceder;

    private EditText etCorreo;
    private EditText etClave;

    private FirebaseAuth.AuthStateListener authStateListener;

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseAuth.getInstance().addAuthStateListener(authStateListener);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();

        setContentView(R.layout.activity_app_class_login);

        bRegistrar = findViewById(R.id.bRegistrar);
        bRegresar = findViewById(R.id.bRegresar);
        bOlvideClave = findViewById(R.id.bOlvideClave);
        bAcceder = findViewById(R.id.bAcceder);

        etCorreo = findViewById(R.id.etCorreo);
        etClave = findViewById(R.id.etClave);



        bRegresar.setOnClickListener( e -> finish() );

        bOlvideClave.setOnClickListener( e-> {
            String correo="";
            if(etCorreo.getText()!= null)
                 correo = etCorreo.getText().toString();

            if(!correo.isEmpty()) {
                FirebaseAuth.getInstance().sendPasswordResetEmail(correo);
                Toast.makeText(this, getText(R.string.loginResetClave), Toast.LENGTH_SHORT).show();

            }
        });



        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if( user != null ) {
                    iniciarAppClass();
                }
            }
        };
    }
    private void iniciarAppClass() {

    }
}
