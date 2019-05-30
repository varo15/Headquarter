package com.headquarter.com.headquarter.activity.activity.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.headquarter.R;
import com.headquarter.com.headquarter.activity.activity.objects.Partida;

import java.sql.Blob;
import java.sql.SQLException;

public class EventRegisteredActivity extends AppCompatActivity {

    public static Partida partida;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_registered);

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
                Snackbar.make(view, "Borrao de la partida. Guarda el fusil", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
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

}
