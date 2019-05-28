package com.headquarter.com.headquarter.activity.activity;

import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.headquarter.R;

import org.w3c.dom.Text;

import java.io.InputStream;
import java.sql.Blob;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class AdapterRecyclerRegistered extends RecyclerView.Adapter<AdapterRecyclerRegistered.ViewHolderRecycler> {

    ArrayList<ArrayList> listDatos;

    public AdapterRecyclerRegistered(ArrayList<ArrayList> listDatos) {
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
        TextView fecha;
        TextView tipo;
        TextView campo;

        public ViewHolderRecycler(@NonNull View itemView) {
            super(itemView);
            dato = itemView.findViewById(R.id.txtTituloEvento);
            fecha = itemView.findViewById(R.id.txtFecha);
            tipo = itemView.findViewById(R.id.txtTipo);
            campo = itemView.findViewById(R.id.txtCampo);
        }

        public void asignarDatos(ArrayList datos) {
            dato.setText(datos.get(1).toString());
            fecha.setText("Fecha: "+datos.get(3).toString());
            tipo.setText("Tipo: "+datos.get(6).toString());
            campo.setText("Campo: "+datos.get(7).toString());
        }
    }
}
