package com.headquarter.com.headquarter.activity.activity.adapter;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.headquarter.R;
import com.headquarter.com.headquarter.activity.activity.EventActivity;
import com.headquarter.com.headquarter.activity.activity.others.Partida;

import java.util.ArrayList;

/*
 *
 * [idPartida, nombrePartida, guion, fecha, foto, aforo, tipo, campo, nombreCampo]
 * [1, Operacion Cascanueces, 0, 2019-05-09, ����������������, 40, Guionizada, 1, La Barganiza]
 *
 */


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

        int partidaId;
        TextView txtPartidaTitulo;
        TextView txtPartidaFecha;
        TextView txtPartidaTipo;
        TextView txtPartidaCampo;



        public ViewHolderRecycler(@NonNull View itemView) {
            super(itemView);
            txtPartidaTitulo = itemView.findViewById(R.id.txtTituloEvento);
            txtPartidaFecha = itemView.findViewById(R.id.txtFecha);
            txtPartidaTipo = itemView.findViewById(R.id.txtTipo);
            txtPartidaCampo = itemView.findViewById(R.id.txtCampo);



            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent eventIntent = new Intent(v.getContext(), EventActivity.class);
                    eventIntent.putExtra("eventId", partidaId);
                    v.getContext().startActivity(eventIntent);
                }
            });
        }

        public void asignarDatos(Partida partida) {
            partidaId = partida.getIdPartida();
            txtPartidaTitulo.setText(partida.getNombrePartida());
            txtPartidaFecha.setText(partida.getFechaPartida().toString());
            txtPartidaTipo.setText("Tipo: " + partida.getTipoPartida());
            txtPartidaCampo.setText("Campo: " + partida.getCampoPartida());

        }
    }
}
