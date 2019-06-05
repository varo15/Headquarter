package com.headquarter.com.headquarter.activity.activity.activity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.headquarter.R;
import com.headquarter.com.headquarter.activity.activity.objects.Partida;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

import java.sql.SQLException;
import java.sql.Statement;

public class EventActivity extends AppCompatActivity {


    //Variables usuario firebase
    private FirebaseAuth firebaseAuth;
    private FirebaseUser user;

    public static Partida partida;
    public View activityView;

    public ImageView imagenPartida;

    Button btnAtras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        this.getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

        btnAtras = findViewById(R.id.btnAtrasReg);

        btnAtras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        activityView = findViewById(R.id.eventActivityLayout);
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();

        /*
         *Llamada al metodo que carga los datos
         */
        mostrarDatosPartida();

        /*
         * FloatingActionButton
         */
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new ApuntarPartidaTask().execute();
            }

        });
    }

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

    }

    private class ApuntarPartidaTask extends AsyncTask<Void, Void, Boolean> {

        private boolean success;

        @Override
        protected Boolean doInBackground(Void... voids) {
            String sql = "INSERT INTO `participa` (`idGoogle_fk`, `idPartida_fk`) VALUES ('" + user.getUid() + "', '" + partida.getIdPartida() + "')";
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
                Snackbar.make(activityView, "Alistado en " + partida.getNombrePartida() + ". ¡Limpia tu fusil!", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

            } else if (aBoolean == false) {
                Snackbar.make(activityView, "Ya estas alistado en: " + partida.getNombrePartida(), Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
            super.onPostExecute(aBoolean);
        }
    }

}
