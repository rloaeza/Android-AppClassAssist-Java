package com.appclass.appclassassist;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.appclass.appclassassist.db.Clase;
import com.appclass.appclassassist.db.Refs;
import com.appclass.appclassassist.db.Usuario;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AppClassClases extends AppCompatActivity {

    private Button bRegresar;
    private Button bAgregarClase;

    private EditText etCodigoClase;

    private ListView lvClases;

    private FirebaseDatabase firebaseDatabase ;
    private DatabaseReference databaseReference;

    private Usuario usuario;
    private String codigoClase;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_app_class_clases);

        bRegresar = findViewById(R.id.bRegresar);
        bAgregarClase = findViewById(R.id.bAgregarClase);
        etCodigoClase = findViewById(R.id.etCodigoClase);
        lvClases = findViewById(R.id.lvClases);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference(Refs.AppClass).child(Refs.clases);


        obtenerAlumno( FirebaseAuth.getInstance().getCurrentUser().getEmail().toString() );

        bRegresar.setOnClickListener(e -> {
            regresarInicio();
        } );


        bAgregarClase.setOnClickListener( e-> {

            String correo = FirebaseAuth.getInstance().getCurrentUser().getEmail().toString();
            String correoFix=correo.replace(".", "+");
            String codigoClase = etCodigoClase.getText().toString();
            String btMac = "Aqui va la MAC de Bluetooth";

            databaseReference.child(codigoClase).addListenerForSingleValueEvent(new ValueEventListener() {

                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if(dataSnapshot.exists()) {

                        databaseReference.child(codigoClase).child(Refs.alumnos).child(correoFix).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if(!dataSnapshot.exists()) {

                                    databaseReference.child(codigoClase).child(Refs.alumnos).child(correoFix).setValue(
                                            new Usuario(usuario.getIdControl(), usuario.getNombre(), usuario.getaPaterno(), usuario.getaMaterno(), usuario.getCorreo(), usuario.getBtMac())
                                    );
                                    etCodigoClase.setText("");
                                    Toast.makeText(AppClassClases.this, getString(R.string.clasesAgregadaSatisfactoriamente), Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) { }
                        });
                    }
                    else {
                        Toast.makeText(AppClassClases.this, getString(R.string.clasesAgregadaError), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        });



        databaseReference.orderByChild("Alumnos").equalTo("robertoloaeza@tecuruapan+edu+mx").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.e("AppClass", dataSnapshot.toString());
                for(DataSnapshot dato : dataSnapshot.getChildren()) {

                    Clase c = dato.getValue(Clase.class);

                    Log.e("AppClass", c.getNombre());


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }

    private void obtenerAlumno (String correo) {

        String correoFix=correo.replace(".", "+");
        DatabaseReference databaseReferenceTemp;

        databaseReferenceTemp = firebaseDatabase.getReference(Refs.AppClass).child(Refs.usuarios).child(correoFix);
        databaseReferenceTemp.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()) {
                    usuario = dataSnapshot.getValue(Usuario.class);
                }
                else {
                    usuario = null;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });






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
