package com.headquarter.com.headquarter.activity.activity.activity;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.headquarter.R;
import com.headquarter.com.headquarter.activity.activity.objects.Equipo;
import com.headquarter.com.headquarter.activity.activity.others.ConnectionDB;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class RegisterActivity extends AppCompatActivity {

    CheckBox checkbox;
    EditText numero;
    Spinner spinner;
    ArrayList<Equipo> teamsList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        new PopulateTeamsSpinner().execute();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        checkbox = findViewById(R.id.checkBox);
        numero = findViewById(R.id.editNumero);
        spinner = findViewById(R.id.spinner);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                getSelectedTeam(view);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(checkbox.isChecked()){
                    numero.setVisibility(View.VISIBLE);
                }
                else{
                    numero.setVisibility(View.GONE);
                }
            }
        });


    }

    public void getSelectedTeam(View v){
        Equipo equipo = (Equipo) spinner.getSelectedItem();
        Toast.makeText(this, equipo.getIdEquipo(), Toast.LENGTH_SHORT).show();
    }


    private class PopulateTeamsSpinner extends AsyncTask{

    String sql = "SELECT * FROM equipo";
    @Override
    protected Object doInBackground(Object[] objects) {
        try {
            Statement statement = ConnectionDB.conn.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            statement.close();
            resultSet.beforeFirst();
            while (resultSet.next()){
                Equipo equipo = new Equipo();
                equipo.setIdEquipo(resultSet.getString("idEquipo"));
                equipo.setNombreEquipo(resultSet.getString("nombreEquipo"));
                equipo.setEscudoEquipo(resultSet.getBlob("escudoEquipo"));
                teamsList.add(equipo);
            }

            resultSet.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

        @Override
        protected void onPostExecute(Object o) {
            ArrayAdapter<Equipo> spinnerAdapter = new ArrayAdapter<>(RegisterActivity.this,android.R.layout.simple_spinner_item, teamsList);

            spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(spinnerAdapter);
        }
    }

    private class RegisterUserInDatabase extends AsyncTask{


        @Override
        protected Object doInBackground(Object[] objects) {
            try {
                Statement statement = ConnectionDB.conn.createStatement();
                ResultSet resultSet = statement.executeUpdate(sql);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}

