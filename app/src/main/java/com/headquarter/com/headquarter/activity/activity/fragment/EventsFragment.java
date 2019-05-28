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
import com.headquarter.com.headquarter.activity.activity.BottomNavigationViewActivity;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class EventsFragment extends Fragment {

    //Variables usuario firebase
    private FirebaseAuth firebaseAuth;
    private FirebaseUser user;

    ArrayList<String> listDatos;
    RecyclerView recycler;
    private ResultSet resultSet;
    private String sql;
    private ProgressBar progressBar;
    private SwipeRefreshLayout swipeRefreshLayout;

    //Array donde se guardan los datos
    ArrayList<ArrayList> listOfEvents = new ArrayList<ArrayList>();


    public EventsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        //Lamamos al emtodo para obtener el usuario y preparar la consulta
        getUser();
        //Preparamos la consulta con el uui de nuestro usuario logeado
        sql = "SELECT * FROM partida";

        //Ejecutar la tarea que devulve la consulta
        new EventsTask().execute();
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_events, container, false);
        recycler = view.findViewById(R.id.recycler);
        recycler.setLayoutManager(new LinearLayoutManager(getContext()));

        listDatos = new ArrayList<String>();

        //ProgressBar
        progressBar = view.findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);

        // Refresh
        swipeRefreshLayout = view.findViewById(R.id.pullToRefresh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new EventsTask().execute();
            }
        });

        return view;
    }

    private class EventsTask extends AsyncTask {

        @Override
        protected Object doInBackground(Object[] objects) {


            try {
                listOfEvents.clear();
                listDatos.clear();
                Statement statement = BottomNavigationViewActivity.connection.createStatement();
                resultSet = statement.executeQuery(sql);
                ResultSetMetaData rsm = resultSet.getMetaData();

                int columnsNumber = rsm.getColumnCount();
                int i;

                resultSet.beforeFirst();

                while (resultSet.next()) {
                    //Cremos un array nuevo por cada fila de la consulta y lo guardamos en listOfEvents
                    ArrayList<String> event = new ArrayList<String>();
                    for (i = 1; i <= columnsNumber; i++) {
                        event.add(resultSet.getString(i));
                    }
                    listOfEvents.add(event);
                }

            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                int i;
                System.out.println("------------------Esto pertenece a EventsFragment-------------------");
                for (i = 0; i < listOfEvents.size(); i++) {
                    System.out.println("\n");
                    System.out.println(listOfEvents.get(i) + "\n");
                }
            }

            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            progressBar.setVisibility(View.GONE);
            loadEventsCards();
            swipeRefreshLayout.setRefreshing(false);


        }
    }

    private void loadEventsCards() {
        //En este metodo ira todo el codigo necesario para que se carguen los datos y se dibujen los cardviews, antes de que se dibujen se mostrara el fragment en blanco con el progresbar dando vueltas
        //Una vez que carguen, el progressbar se desactiva y se pintan las tarjetas

        System.out.println(listOfEvents.size());
        System.out.println(listDatos.size());
        for(int i=0;i<listOfEvents.size();i++){
            listDatos.add(listOfEvents.get(i).get(1).toString());
        }
        AdapterRecycler adapter = new AdapterRecycler(listDatos);
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
