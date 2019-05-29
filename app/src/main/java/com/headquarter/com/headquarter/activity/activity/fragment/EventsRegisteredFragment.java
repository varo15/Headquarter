package com.headquarter.com.headquarter.activity.activity.fragment;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.headquarter.R;
import com.headquarter.com.headquarter.activity.activity.BottomNavigationViewActivity;
import com.headquarter.com.headquarter.activity.activity.adapter.EventsRegisteredFragmentAdapter;
import com.headquarter.com.headquarter.activity.activity.others.Partida;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class EventsRegisteredFragment extends Fragment {

    //Variables usuario firebase
    private FirebaseAuth firebaseAuth;
    private FirebaseUser user;

    RecyclerView recycler;
    private ResultSet resultSet;
    private String sql;
    private ProgressBar progressBar;
    private SwipeRefreshLayout swipeRefreshLayout;

    //ArrayList necesarios para la carga de datos
    static ArrayList<Partida> getListOfEventsRegistered = new ArrayList<>();

    public EventsRegisteredFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        //Lamamos al emtodo para obtener el usuario y preparar la consulta
        getUser();
        //Preparamos la consulta con el uui de nuestro usuario logeado
        sql = "SELECT `partida`.*, `campo`.`nombreCampo`, `participa`.*, `jugador`.`idGoogle` FROM `partida`" +
                "LEFT JOIN `campo` ON `partida`.`id_campo_fk` = `campo`.`idCampo`" +
                "LEFT JOIN `participa` ON `participa`.`idPartida_fk` = `partida`.`idPartida`" +
                "LEFT JOIN `jugador` ON `participa`.`idGoogle_fk` = `jugador`.`idGoogle`" +
                "WHERE `jugador`.`idGoogle` = '" + user.getUid() + "'";

        //Ejecutar la tarea que devulve la consulta
        new EventsRegisteredTask().execute();
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_events_registered, container, false);
        recycler = view.findViewById(R.id.recycler);
        recycler.setLayoutManager(new LinearLayoutManager(getContext()));


        //ProgressBar
        progressBar = view.findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);

        // Refresh
        swipeRefreshLayout = view.findViewById(R.id.pullToRefresh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new EventsRegisteredTask().execute();
            }
        });
        return view;
    }

    private class EventsRegisteredTask extends AsyncTask {


        @Override
        protected Object doInBackground(Object[] objects) {


            try {

                Statement statement = BottomNavigationViewActivity.statement;

                resultSet = statement.executeQuery(sql);
                resultSet.beforeFirst();
                getListOfEventsRegistered.clear();

                while (resultSet.next()) {
                    Partida partida = new Partida();

                    partida.setIdPartida(resultSet.getInt("idPartida"));
                    partida.setNombrePartida(resultSet.getString("nombrePartida"));
                    partida.setGuionPartida(resultSet.getBlob("guionPartida"));
                    partida.setFechaPartida(resultSet.getDate("fechaPartida"));
                    partida.setFotoPartida(resultSet.getBlob("fotoPartida"));
                    partida.setAforoPartida(resultSet.getString("aforoPartida"));
                    partida.setTipoPartida(resultSet.getString("tipoPartida"));
                    partida.setCampoPartida(resultSet.getString("nombreCampo"));

                    getListOfEventsRegistered.add(partida);
                }


            } catch (SQLException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            progressBar.setVisibility(View.GONE);
            loadEventsRegisteredCards();
            swipeRefreshLayout.setRefreshing(false);


        }
    }

    private void loadEventsRegisteredCards() {
        //En este metodo ira todo el codigo necesario para que se carguen los datos y se dibujen los cardviews, antes de que se dibujen se mostrara el fragment en blanco con el progresbar dando vueltas
        //Una vez que carguen, el progressbar se desactiva y se pintan las tarjetas

        EventsRegisteredFragmentAdapter adapter = new EventsRegisteredFragmentAdapter(getListOfEventsRegistered);
        recycler.setAdapter(adapter);
    }

    /*
        Metodo que nos devuelve el usuario de firebase
     */
    private void getUser() {
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
    }

}