package com.appclass.appclassassist;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
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
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == Activity.RESULT_OK && requestCode == 1) {
            finish();

        }
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


        bRegistrar.setOnClickListener( e -> {

            if(verificarCorreoClave()) {

                AlertDialog.Builder builderClave = new AlertDialog.Builder(this);
                builderClave.setTitle( getString(R.string.loginVerificarClave) );
                final EditText etClaveVerificar = new EditText(this);
                etClaveVerificar.setInputType(InputType.TYPE_CLASS_TEXT |
                        InputType.TYPE_TEXT_VARIATION_PASSWORD);
                builderClave.setView(etClaveVerificar);
                builderClave.setPositiveButton(getString(R.string.aceptar), (dialog, which) -> {
                    if(etClave.getText().toString().equals(etClaveVerificar.getText().toString())) {
                        registrar(etCorreo.getText().toString(), etClave.getText().toString());
                    }
                    else {
                        Toast.makeText(this, getString(R.string.loginClaveError), Toast.LENGTH_SHORT).show();
                    }
                });
                builderClave.setNegativeButton(getString(R.string.cancelar), (dialog, which) -> dialog.cancel());
                builderClave.show();


            }
            else
                Toast.makeText(this, getString(R.string.loginCamposVacios), Toast.LENGTH_SHORT).show();


        });

        bAcceder.setOnClickListener( e -> {
            if(verificarCorreoClave())
                iniciarSesion(etCorreo.getText().toString(), etClave.getText().toString());
            else
                Toast.makeText(this, getString(R.string.loginCamposVacios), Toast.LENGTH_SHORT).show();

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



    private boolean verificarCorreoClave() {
        if(etCorreo.getText().toString().isEmpty())
            return false;
        if(etClave.getText().toString().isEmpty())
            return false;
        return true;
    }

    private void iniciarSesion(String email, String password) {
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful()) {
                    iniciarAppClass();
                }
                else {
                    Toast.makeText(AppClassLogin.this, getString(R.string.loginErrorIniciar), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }



    private void registrar(String email, String password) {
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if( task.isSuccessful()) {
                    Toast.makeText(AppClassLogin.this, getString(R.string.loginRegistrarCorrecto), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(AppClassLogin.this, getString(R.string.loginErrorRegistrar), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private void iniciarAppClass() {

        Intent intent = new Intent(this, AppClassClases.class);
        startActivityForResult(intent, 1);


    }
}
