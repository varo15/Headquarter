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
import com.headquarter.com.headquarter.activity.activity.AdapterRecycler;
import com.headquarter.com.headquarter.activity.activity.AdapterRecyclerRegistered;
import com.headquarter.com.headquarter.activity.activity.BottomNavigationViewActivity;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
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

    ArrayList<String> listDatos;
    RecyclerView recycler;
    private ResultSet resultSet;
    private String sql;
    private ProgressBar progressBar;
    private SwipeRefreshLayout swipeRefreshLayout;

    //ArrayList necesarios para la carga de datos
    static ArrayList<ArrayList> getListOfEventsRegistered = new ArrayList<ArrayList>();

    public EventsRegisteredFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        //Lamamos al emtodo para obtener el usuario y preparar la consulta
        getUser();
        //Preparamos la consulta con el uui de nuestro usuario logeado
        sql = "SELECT `partida`.*, `participa`.*, `jugador`.`DNI` FROM `partida`" +
                "LEFT JOIN `participa` ON `participa`.`idPartida_fk` = `partida`.`idPartida`" +
                "LEFT JOIN `jugador` ON `participa`.`idGoogle_fk` = `jugador`.`idGoogle`" +
                "WHERE `jugador`.`idGoogle` = '" + user.getUid() + "'" ;

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

        listDatos = new ArrayList<String>();

        for (int i = 0; i < 10; i++) {
            listDatos.add("Dato " + i);
        }

        AdapterRecyclerRegistered adapter = new AdapterRecyclerRegistered(getListOfEventsRegistered);
        recycler.setAdapter(adapter);

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

                Statement statement = BottomNavigationViewActivity.connection.createStatement();
                resultSet = statement.executeQuery(sql);
                ResultSetMetaData rsm = resultSet.getMetaData();
                resultSet.beforeFirst();

                int columsNumber = rsm.getColumnCount();
                int i;

                while (resultSet.next()) {
                    //Cremos un array nuevo por cada fila de la consulta y lo guardamos en listOfEvents
                    ArrayList<String> event = new ArrayList<String>();
                    for (i = 1; i <= columsNumber; i++) {
                        event.add(resultSet.getString(i));
                    }
                    getListOfEventsRegistered.add(event);
                }


            } catch (SQLException e) {
                System.out.println("CAGADA");
                e.printStackTrace();
            }finally {
                System.out.println("----------------------Esto pertenece al EventsRegisteredFragment----------------------");
                int i;
                for (i = 0; i < getListOfEventsRegistered.size(); i++) {
                    System.out.println("\n");
                    System.out.println(getListOfEventsRegistered.get(i) + "\n");
                }
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
    }

    /*
        Metodo que nos devuelve el usuario de firebase
     */
    private void getUser() {
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
    }

}
