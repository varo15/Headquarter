package com.headquarter.com.headquarter.activity.activity;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.headquarter.R;

import java.util.ArrayList;

public class AdapterRecycler extends RecyclerView.Adapter<AdapterRecycler.ViewHolderRecycler> {

    ArrayList<ArrayList> listDatos;

    public AdapterRecycler(ArrayList<ArrayList> listDatos) {
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

        TextView dato;

        public ViewHolderRecycler(@NonNull View itemView) {
            super(itemView);
            dato = itemView.findViewById(R.id.txtTituloEvento);
        }

        public void asignarDatos(ArrayList datos) {
            dato.setText(datos.get(1).toString());
        }
    }
}
