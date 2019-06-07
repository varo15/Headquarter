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
import com.headquarter.com.headquarter.activity.activity.activity.BottomNavigationViewActivity;
import com.headquarter.com.headquarter.activity.activity.adapter.EventsFragmentAdapter;
import com.headquarter.com.headquarter.activity.activity.objects.Partida;

import java.sql.ResultSet;
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

    private RecyclerView recycler;
    private ResultSet resultSet;
    private String sql;
    private ProgressBar progressBar;
    private SwipeRefreshLayout swipeRefreshLayout;

    //Array donde se guardan los datos
    private ArrayList<Partida> listOfEvents = new ArrayList<>();

    private EventsFragmentAdapter adapter;


    public EventsFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        //Lamamos al emtodo para obtener el usuario y preparar la consulta
        getUser();

        adapter = new EventsFragmentAdapter(listOfEvents);
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_events, container, false);
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
                new EventsTask().execute();
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        new EventsTask().execute();
    }

    public class EventsTask extends AsyncTask {

        @Override
        protected Object doInBackground(Object[] objects) {

            //Preparamos la consulta con el uui de nuestro usuario logeado
            sql = "SELECT `partida`.*, `campo`.`nombreCampo` FROM partida " +
                    "LEFT JOIN `campo` ON `partida`.`id_campo_fk` = `campo`.`idCampo` " +
                    "WHERE partida.idPartida " +
                    "NOT IN( SELECT idPartida_fk FROM participa WHERE participa.idGoogle_fk = '" + user.getUid() + "' ) ORDER BY `partida`.`idPartida` DESC";

            try {
                Statement statement = BottomNavigationViewActivity.statement;
                resultSet = statement.executeQuery(sql);
                resultSet.beforeFirst();
                listOfEvents.clear();

                while (resultSet.next()) {
                    Partida partida = new Partida();

                    partida.setIdPartida(resultSet.getInt("idPartida"));
                    partida.setNombrePartida(resultSet.getString("nombrePartida"));
                    partida.setFechaPartida(resultSet.getDate("fechaPartida"));
                    partida.setFotoPartida(resultSet.getBlob("fotoPartida"));
                    partida.setAforoPartida(resultSet.getString("aforoPartida"));
                    partida.setTipoPartida(resultSet.getString("tipoPartida"));
                    partida.setCampoPartida(resultSet.getString("nombreCampo"));
                    partida.setMarcoAmbiental((resultSet.getString("marcoAmbiental")));

                    listOfEvents.add(partida);
                }

                System.out.println("Termina");
            } catch (SQLException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            loadEventsCards();
            progressBar.setVisibility(View.GONE);
            swipeRefreshLayout.setRefreshing(false);

        }
    }

    private void loadEventsCards() {
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
