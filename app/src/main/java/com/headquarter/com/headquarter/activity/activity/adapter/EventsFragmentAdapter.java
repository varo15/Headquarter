package com.headquarter.com.headquarter.activity.activity.adapter;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.headquarter.R;
import com.headquarter.com.headquarter.activity.activity.activity.EventActivity;
import com.headquarter.com.headquarter.activity.activity.objects.Partida;

import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;


public class EventsFragmentAdapter extends RecyclerView.Adapter<EventsFragmentAdapter.ViewHolderRecycler> {

    /**
     * Declaramos el ArrayList que se va a encargar de almacenar los eventos
     */
    ArrayList<Partida> listDatos;

    /**
     * Constructor del adaptador
     * @param listDatos
     */
    public EventsFragmentAdapter(ArrayList<Partida> listDatos) {
        this.listDatos = listDatos;
    }

    /**
     * Metodo que se encarga de asignar el layout al fragment de eventos
     * @param viewGroup
     * @param i
     * @return ViewHolderRecycler(view)
     */
    @NonNull
    @Override
    public ViewHolderRecycler onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_list, null, false);
        return new ViewHolderRecycler(view);
    }

    /**
     * Metodo que se encarga de llamar al metodo para asignar los datos al adapter con la longitud
     * de datos que le pasemos
     * @param viewHolderRecycler
     * @param i
     */
    @Override
    public void onBindViewHolder(@NonNull ViewHolderRecycler viewHolderRecycler, int i) {

        viewHolderRecycler.asignarDatos(listDatos.get(i));
    }

    /**
     * Metodo que se encarga de devolvernos la dimesion del array de datos
     * @return listDatos.size()
     */
    @Override
    public int getItemCount() {
        return listDatos.size();
    }

    /**
     * Clase intrinseca del ViewHolder
     */
    public class ViewHolderRecycler extends RecyclerView.ViewHolder {

        /**
         * Campos donde se va a mostrar la informacion
         */
        Partida partidaSelected;
        TextView txtPartidaTitulo;
        TextView txtPartidaFecha;
        TextView txtPartidaTipo;
        TextView txtPartidaCampo;
        ImageView imgPartidaFoto;


        /**
         * Metodo que se encarga de inicializar los campos donde se va a mostrar la informacion
         * @param itemView
         */
        public ViewHolderRecycler(@NonNull final View itemView) {

            super(itemView);
            txtPartidaTitulo = itemView.findViewById(R.id.txtTituloEvento);
            txtPartidaFecha = itemView.findViewById(R.id.txtFecha);
            txtPartidaTipo = itemView.findViewById(R.id.txtTipo);
            txtPartidaCampo = itemView.findViewById(R.id.txtCampo);
            imgPartidaFoto = itemView.findViewById(R.id.imagen);

            txtPartidaFecha.setTypeface(txtPartidaFecha.getTypeface(), Typeface.BOLD);
            txtPartidaTipo.setTypeface(txtPartidaFecha.getTypeface(), Typeface.BOLD);
            txtPartidaCampo.setTypeface(txtPartidaFecha.getTypeface(), Typeface.BOLD);


            itemView.setOnClickListener(new View.OnClickListener() {
                /**
                 * Listener que se encarga de entrar en la partida seleccionada cuando pinchemos
                 * en ella
                 * @param v
                 */
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(v.getContext(), EventActivity.class);
                    EventActivity.partida = partidaSelected;
                    v.getContext().startActivity(intent);


                }
            });
        }

        /**
         * Metodo que se encarga de asignar los datos a las miniaturas de los eventos
         * @param partida
         */
        public void asignarDatos(Partida partida) {

            partidaSelected = partida;
            txtPartidaTitulo.setText(partida.getNombrePartida());
            txtPartidaFecha.setText("Fecha: " + partida.getFechaPartida().toString());
            txtPartidaTipo.setText("Tipo: " + partida.getTipoPartida());
            txtPartidaCampo.setText("Campo: " + partida.getCampoPartida());
            getEventImage(partida);
            imgPartidaFoto.setImageBitmap(partida.getFotoPartidaBitmap());

        }

        /**
         * Metodo que se encarga de recoger la imagen de la partida y asignarsela al evento
         * @param partida
         */
        private void getEventImage(Partida partida) {
            Blob blob = partida.getFotoPartida();
            int blobLength = 0;
            try {
                blobLength = (int) blob.length();
                byte[] blobAsBytes = blob.getBytes(1, blobLength);
                blob.free();
                Bitmap bitmap = BitmapFactory.decodeByteArray(blobAsBytes, 0, blobAsBytes.length);
                partida.setFotoPartidaBitmap(bitmap);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
