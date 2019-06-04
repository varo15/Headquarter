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

    ArrayList<Partida> listDatos;

    public EventsFragmentAdapter(ArrayList<Partida> listDatos) {
        this.listDatos = listDatos;
    }

    @NonNull
    @Override
    public ViewHolderRecycler onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_list, null, false);
        return new ViewHolderRecycler(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderRecycler viewHolderRecycler, int i) {

        viewHolderRecycler.asignarDatos(listDatos.get(i));
    }

    @Override
    public int getItemCount() {
        return listDatos.size();
    }

    public class ViewHolderRecycler extends RecyclerView.ViewHolder {

        Partida partidaSelected;
        TextView txtPartidaTitulo;
        TextView txtPartidaFecha;
        TextView txtPartidaTipo;
        TextView txtPartidaCampo;
        ImageView imgPartidaFoto;


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
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(v.getContext(), EventActivity.class);
                    EventActivity.partida = partidaSelected;
                    v.getContext().startActivity(intent);


                }
            });
        }

        public void asignarDatos(Partida partida) {

            partidaSelected = partida;
            txtPartidaTitulo.setText(partida.getNombrePartida());
            txtPartidaFecha.setText("Fecha: " + partida.getFechaPartida().toString());
            txtPartidaTipo.setText("Tipo: " + partida.getTipoPartida());
            txtPartidaCampo.setText("Campo: " + partida.getCampoPartida());
            getEventImage(partida);
            imgPartidaFoto.setImageBitmap(partida.getFotoPartidaBitmap());

        }

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
