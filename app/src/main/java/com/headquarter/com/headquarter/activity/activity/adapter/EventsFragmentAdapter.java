package com.headquarter.com.headquarter.activity.activity.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.headquarter.R;

import java.util.ArrayList;

/*
 *
 * [idPartida, nombrePartida, guion, fecha, foto, aforo, tipo, campo, nombreCampo]
 * [1, Operacion Cascanueces, 0, 2019-05-09, ����������������, 40, Guionizada, 1, La Barganiza]
 *
 */


public class EventsFragmentAdapter extends RecyclerView.Adapter<EventsFragmentAdapter.ViewHolderRecycler> {

    ArrayList<ArrayList> listDatos;

    public EventsFragmentAdapter(ArrayList<ArrayList> listDatos) {
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
        String partidaId;
        TextView partidaTitulo;
        TextView partidaFecha;
        TextView partidaTipo;
        TextView partidaCampo;

        public ViewHolderRecycler(@NonNull View itemView) {
            super(itemView);
            partidaTitulo = itemView.findViewById(R.id.txtTituloEvento);
            partidaFecha = itemView.findViewById(R.id.txtFecha);
            partidaTipo = itemView.findViewById(R.id.txtTipo);
            partidaCampo = itemView.findViewById(R.id.txtCampo);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(v.getContext(), partidaTitulo.getText(), Toast.LENGTH_SHORT).show();
                }
            });
        }

        public void asignarDatos(ArrayList datos) {
            partidaId = datos.get(0).toString();
            partidaTitulo.setText(datos.get(1).toString());
            partidaFecha.setText("Fecha: "+datos.get(3).toString());
            partidaTipo.setText("Tipo: "+datos.get(6).toString());
            partidaCampo.setText("Campo: "+datos.get(8).toString());
        }
    }
}
