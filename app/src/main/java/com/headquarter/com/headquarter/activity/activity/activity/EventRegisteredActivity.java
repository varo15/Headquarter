package com.headquarter.com.headquarter.activity.activity.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.headquarter.R;
import com.headquarter.com.headquarter.activity.activity.fragment.EventsRegisteredFragment;
import com.headquarter.com.headquarter.activity.activity.objects.Partida;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

import java.sql.Blob;
import java.sql.SQLException;
import java.sql.Statement;

public class EventRegisteredActivity extends AppCompatActivity {

    //Variables usuario firebase
    private FirebaseAuth firebaseAuth;
    private FirebaseUser user;

    public static Partida partida;

    public View activityView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_registered);

        activityView = findViewById(R.id.eventRegisteredActivityLayout);
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();



        /*
         *Llamada al metodo que carga los datos
         */
        mostrarDatosPartida();

        /*
         *Creamos un nuevo AlertDialog gracias al metodo creado mas abajo
         */
        final AlertDialog alertDialog = getAlertDialog();
        /*
         * FloatingActionButton
         */
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

    private void mostrarDatosPartida() {
        TextView titulo = findViewById(R.id.textView2);
        titulo.setText(partida.getNombrePartida());
        /*
         *Llamada al metodo que pinta la imagen en el ImageView corespondiente
         */
        getEventImage(partida);
    }

    private void getEventImage(Partida partida) {
        Blob blob = partida.getFotoPartida();
        int blobLength = 0;
        try {
            blobLength = (int) blob.length();
            byte[] blobAsBytes = blob.getBytes(1, blobLength);
            blob.free();
            Bitmap bitmap = BitmapFactory.decodeByteArray(blobAsBytes, 0, blobAsBytes.length);
            //imagenPartida.setImageBitmap(bitmap);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private class BorrarPartidaTask extends AsyncTask<Void, Void, Boolean> {

        private boolean success;
        @Override
        protected Boolean doInBackground(Void... voids) {

            String sql = "DELETE FROM `participa` WHERE `participa`.`idGoogle_fk` ='" + user.getUid() + "' AND `participa`.`idPartida_fk` = " + partida.getIdPartida();
            Statement statement = BottomNavigationViewActivity.statement;
            try {
                statement.executeUpdate(sql);
                success = true;
            } catch (MySQLIntegrityConstraintViolationException DuplicateEntry){
                success = false;
            } catch (SQLException e) {

                e.printStackTrace();
            }

            return success;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            if (aBoolean == true){
                Snackbar.make(activityView, "Te has borrado de " + partida.getNombrePartida(), Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();


            }else if (aBoolean==false) {
                Snackbar.make(activityView, "Servidor caido, intentalo de nuevo mas tarde: ", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
            super.onPostExecute(aBoolean);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
