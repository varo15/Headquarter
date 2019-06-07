package com.headquarter.com.headquarter.activity.activity.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.headquarter.R;
import com.headquarter.com.headquarter.activity.activity.objects.Partida;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

import java.sql.SQLException;
import java.sql.Statement;

public class EventRegisteredActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private FirebaseUser user;

    public static Partida partida;

    public View activityView;
    public ImageView imagenPartida;

    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReference();

    Button btnAtras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_registered);

        btnAtras = findViewById(R.id.btnAtrasReg);
        btnAtras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        activityView = findViewById(R.id.eventRegisteredActivityLayout);
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();

        mostrarDatosPartida();

        final AlertDialog alertDialog = getAlertDialog();

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.show();

            }
        });
    }

    private AlertDialog getAlertDialog() {
        final AlertDialog alertDialog = new AlertDialog.Builder(EventRegisteredActivity.this).create();
        alertDialog.setTitle("Desalistarse de la partida");
        alertDialog.setMessage("Estas seguro que deseas borrarte de la partida?");
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                new BorrarPartidaTask().execute();
            }
        });
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                alertDialog.dismiss();
            }
        });
        return alertDialog;
    }


    /**
     * mostrarDatosPartida
     * Metodo que pobla los campos de la activity sacando la informacion del objeto pertinente
     */
    private void mostrarDatosPartida() {
        //------------------------Aqui se define el imageview y se le asigna el contendio que esta guardado en la clase partida
        imagenPartida = findViewById(R.id.imageEvent);
        imagenPartida.setImageBitmap(partida.getFotoPartidaBitmap());

        TextView titulo = findViewById(R.id.title);
        titulo.setText(partida.getNombrePartida());
        TextView tipo = findViewById(R.id.tipo);
        tipo.setText(partida.getTipoPartida());
        TextView aforo = findViewById(R.id.aforo);
        aforo.setText(partida.getAforoPartida());
        TextView campo = findViewById(R.id.campo);
        campo.setText(partida.getCampoPartida());
        TextView fecha = findViewById(R.id.fecha);
        fecha.setText(partida.getFechaPartida().toString());
        TextView marcoAmbiental = findViewById(R.id.macroambiental);
        marcoAmbiental.setText(partida.getMarcoAmbiental());
        Button btnDescargar = findViewById(R.id.btnDescargarReg);

        btnDescargar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                descargarGuion();
            }
        });


    }

    /**
     * descargarguion
     * Metodo que descarga el guion de la partida correspondiente alojado en Firebase Storage
     */
    public void descargarGuion() {
        storageRef.child("partidas/" + "partida" + partida.getIdPartida() + "/" + partida.getIdPartida() + ".pdf").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                System.out.println(uri);
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(String.valueOf(uri))));
            }
        });
    }

    /**
     * BorrarPartidaTask
     * Extiende de AsynkTask y eleminia a un usuario de una partida
     */
    private class BorrarPartidaTask extends AsyncTask<Void, Void, Boolean> {

        private boolean success;

        @Override
        protected Boolean doInBackground(Void... voids) {

            String sql = "DELETE FROM `participa` WHERE `participa`.`idGoogle_fk` ='" + user.getUid() + "' AND `participa`.`idPartida_fk` = " + partida.getIdPartida();
            Statement statement = BottomNavigationViewActivity.statement;
            try {
                statement.executeUpdate(sql);
                success = true;
            } catch (MySQLIntegrityConstraintViolationException DuplicateEntry) {
                success = false;
            } catch (SQLException e) {

                e.printStackTrace();
            }

            return success;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            if (aBoolean == true) {
                Snackbar.make(activityView, "Te has borrado de " + partida.getNombrePartida(), Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();


            } else if (aBoolean == false) {
                Snackbar.make(activityView, "Servidor caido, intentalo de nuevo mas tarde: ", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
            super.onPostExecute(aBoolean);
        }
    }

}
