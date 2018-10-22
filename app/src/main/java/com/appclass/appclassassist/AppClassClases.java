package com.appclass.appclassassist;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.appclass.appclassassist.db.Clase;
import com.appclass.appclassassist.db.Funciones;
import com.appclass.appclassassist.db.Refs;
import com.appclass.appclassassist.db.Usuario;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AppClassClases extends AppCompatActivity {

    private Button bRegresar;
    private Button bAgregarClase;
    private Button bCerrarSesion;

    private EditText etCodigoClase;

    private ListView lvClases;

    private FirebaseDatabase firebaseDatabase ;
    private DatabaseReference databaseReference;

    private Usuario usuario;
    private String codigoClase;



    private List<Clase> misClases;
    private ArrayAdapter<Clase> arrayAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_app_class_clases);

        bRegresar = findViewById(R.id.bRegresar);
        bAgregarClase = findViewById(R.id.bAgregarClase);
        bCerrarSesion = findViewById(R.id.bCerrarSesion);
        etCodigoClase = findViewById(R.id.etCodigoClase);
        lvClases = findViewById(R.id.lvClases);





        misClases = new ArrayList<>();
        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, misClases);

        lvClases.setAdapter(arrayAdapter);



        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference(Refs.AppClass);


        obtenerAlumno(Funciones.getCorreoFix());







        bCerrarSesion.setOnClickListener( e -> {
            FirebaseAuth.getInstance().signOut();
            regresarInicio();
        });

        bRegresar.setOnClickListener(e -> {
            regresarInicio();
        } );


        bAgregarClase.setOnClickListener( e-> {
            if(etCodigoClase.getText().toString().isEmpty()) {
                Toast.makeText(this, getText(R.string.clasesAgregarFaltaCodigo), Toast.LENGTH_SHORT).show();
                return;
            }
            codigoClase = etCodigoClase.getText().toString();
            obtenerAlumno(Funciones.getCorreoFix());
            databaseReference.child(Refs.clases).child(codigoClase).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if(dataSnapshot.exists()) {

                        databaseReference.child(Refs.clases).child(codigoClase).child(Refs.alumnos).child(Funciones.getCorreoFix(usuario.getCorreo())).setValue(usuario);
                        Clase clase = dataSnapshot.getValue(Clase.class);
                        databaseReference.child(Refs.usuarios).child(Funciones.getCorreoFix(usuario.getCorreo())).child(Refs.clases).child(clase.getCodigo()).setValue(clase);

                        etCodigoClase.setText("");



                        Toast.makeText(AppClassClases.this, getString(R.string.clasesAgregadaSatisfactoriamente), Toast.LENGTH_SHORT).show();

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



        databaseReference.child(Refs.usuarios).child(Funciones.getCorreoFix()).child(Refs.clases).orderByChild(Clase.ordenarPorCampo).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                misClases.clear();
                for(DataSnapshot data : dataSnapshot.getChildren()) {
                    misClases.add(data.getValue(Clase.class)) ;
                }
                arrayAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });




        lvClases.setOnItemClickListener((parent, view, position, id) -> {
            Clase c = misClases.get(position);

            Intent intent = new Intent(this, AlumnoDetalle.class);
            intent.putExtra(Refs.claseCodigo, c.getCodigo());
            intent.putExtra(Refs.claseNombre, c.getNombre());
            String nombreCompleto = usuario.getApellidos()+ ", "+usuario.getNombre();
            intent.putExtra(Refs.usuarioNombre, nombreCompleto);
            intent.putExtra(Refs.usuarioCodigo, usuario.getIdControl());
            startActivity(intent);

        });


    }


    private void obtenerAlumno (String correoFix) {

        firebaseDatabase.getReference(Refs.AppClass).child(Refs.usuarios).child(correoFix).addListenerForSingleValueEvent(new ValueEventListener() {
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
                usuario = null;
            }
        });






    }

    private void regresarInicio() {
        setResult(Activity.RESULT_OK, getIntent());
        finish();
    }
    @Override
    public void onBackPressed() {

        regresarInicio();
    }
}
