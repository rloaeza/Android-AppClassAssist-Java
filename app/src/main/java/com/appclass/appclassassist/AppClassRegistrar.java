package com.appclass.appclassassist;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AppClassRegistrar extends AppCompatActivity {

    private String btMacLocal;
    private Alumno alumno;
    private EditText etId;
    private EditText etNombre;
    private EditText etApaterno;
    private EditText etAmaterno;
    private EditText etCorreo;

    private EditText claseCorreo;
    private EditText claseCodigo;

    private Button bAceptar;

    private int error;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_class_registrar);
        etId = findViewById(R.id.etId);
        etNombre = findViewById(R.id.etNombre);
        etApaterno = findViewById(R.id.etApaterno);
        etAmaterno = findViewById(R.id.etAmaterno);
        etCorreo = findViewById(R.id.etCorreo);

        claseCodigo = findViewById(R.id.etCodigo);
        claseCorreo = findViewById(R.id.etCorreoClase);
        bAceptar = findViewById(R.id.bAceptar);

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference(AppClassReferencias.AppClass);

        btMacLocal = android.provider.Settings.Secure.getString(this.getContentResolver(), "bluetooth_address");


        bAceptar.setOnClickListener(e -> {
            alumno = new Alumno();
            error = 0;
            alumno.setNombre(VerificarCampo(etNombre.getText().toString()));
            alumno.setApaterno(VerificarCampo(etApaterno.getText().toString()));
            alumno.setAmaterno(VerificarCampo(etAmaterno.getText().toString()));
            alumno.setCorreo(VerificarCampo(etCorreo.getText().toString()));
            alumno.setId(VerificarCampo(etId.getText().toString()));
            alumno.setBtMAC(btMacLocal);
            alumno.setAsistio("0");
            if (error > 0) {
                Toast.makeText(this, getString(R.string.errorCampos), Toast.LENGTH_SHORT).show();
            } else {
                String correoFix = claseCorreo.getText().toString().replace(".", "+");


                databaseReference.child(AppClassReferencias.Personas).child(correoFix).child(AppClassReferencias.Alumnos).child(alumno.getId()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(!dataSnapshot.exists()) {


                            databaseReference.child(AppClassReferencias.Personas).child(correoFix).child(AppClassReferencias.Alumnos).child(alumno.getId()).setValue(
                                    alumno
                            );
                            Toast.makeText(getBaseContext(), getString(R.string.alumnoRegistroCorrecto), Toast.LENGTH_SHORT).show();


                        }
                        else {
                            Toast.makeText(getBaseContext(), getString(R.string.alumnoRegistroExistente), Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });








                databaseReference.child(AppClassReferencias.Personas).child(correoFix).child(AppClassReferencias.Clases).child(claseCodigo.getText().toString()).child(AppClassReferencias.bdCantidadAlumnos).child(alumno.getId()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(!dataSnapshot.exists()) {


                            databaseReference.child(AppClassReferencias.Personas).child(correoFix).child(AppClassReferencias.Clases).child(claseCodigo.getText().toString()).child(AppClassReferencias.bdCantidadAlumnos).child(alumno.getId()).setValue(
                                    alumno
                            );
                            Toast.makeText(getBaseContext(), getString(R.string.alumnoRegistroCorrecto), Toast.LENGTH_SHORT).show();

                        }
                        else {
                            Toast.makeText(getBaseContext(), getString(R.string.alumnoRegistroExistente), Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });




            }
        });
    }

    private String VerificarCampo(String campo) {
        if(campo==null) {
            error++;
            return "";
        }
        return campo;
    }


}
