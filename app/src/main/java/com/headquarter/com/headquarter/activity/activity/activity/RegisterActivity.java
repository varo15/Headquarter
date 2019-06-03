package com.headquarter.com.headquarter.activity.activity.activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.headquarter.R;
import com.headquarter.com.headquarter.activity.activity.objects.Equipo;
import com.headquarter.com.headquarter.activity.activity.objects.Jugador;
import com.headquarter.com.headquarter.activity.activity.others.ConnectionDB;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class RegisterActivity extends AppCompatActivity {

    CheckBox checkbox;
    EditText numeroFAA;
    EditText DNI;
    EditText cumpleanios;
    Spinner spinner;
    Button button;
    FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
    ArrayList<Equipo> teamsList = new ArrayList<>();
    final Calendar myCalendar = Calendar.getInstance();
    public Equipo equipo = new Equipo();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        new PopulateTeamsSpinner().execute();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        checkbox = findViewById(R.id.checkBox);
        numeroFAA = findViewById(R.id.editNumero);
        DNI = findViewById(R.id.editDNI);
        cumpleanios = findViewById(R.id.birthday);
        spinner = findViewById(R.id.spinner);
        button = findViewById(R.id.button2);


        //Spinner listener
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                getSelectedTeam(view);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //Checkbox listener
        checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (checkbox.isChecked()) {
                    numeroFAA.setVisibility(View.VISIBLE);
                } else {
                    numeroFAA.setVisibility(View.GONE);
                }
            }
        });

        //Button listener
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new RegisterUserInDatabase().execute();
            }
        });


        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };

        cumpleanios.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(RegisterActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });


    }


    private void updateLabel() {
        String myFormat = "yyyy-MM-dd "; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        cumpleanios.setText(sdf.format(myCalendar.getTime()));
    }


    public void getSelectedTeam(View v) {
        equipo = (Equipo) spinner.getSelectedItem();
        String idE = String.valueOf(equipo.getIdEquipo());
        Toast.makeText(this, idE, Toast.LENGTH_SHORT).show();
    }


    private class PopulateTeamsSpinner extends AsyncTask {

        String sql = "SELECT * FROM equipo";

        @Override
        protected Object doInBackground(Object[] objects) {
            try {
                Statement statement = ConnectionDB.conn.createStatement();
                ResultSet resultSet = statement.executeQuery(sql);
                resultSet.beforeFirst();
                while (resultSet.next()) {
                    Equipo equipo = new Equipo();
                    equipo.setIdEquipo(resultSet.getInt("idEquipo"));
                    equipo.setNombreEquipo(resultSet.getString("nombreEquipo"));
                    equipo.setEscudoEquipo(resultSet.getBlob("escudoEquipo"));
                    teamsList.add(equipo);
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            ArrayAdapter<Equipo> spinnerAdapter = new ArrayAdapter<>(RegisterActivity.this, android.R.layout.simple_spinner_item, teamsList);

            spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(spinnerAdapter);
        }
    }

    private class RegisterUserInDatabase extends AsyncTask {

        private Jugador jugador = new Jugador();


        @Override
        protected Object doInBackground(Object[] objects) {

            jugador.setDNI(DNI.getText().toString());
            jugador.setIdGoogle(firebaseUser.getUid());
            jugador.setNombre(firebaseUser.getDisplayName());
            jugador.setFechaNacimiento(cumpleanios.getText().toString());
            jugador.setTelefono(firebaseUser.getPhoneNumber());
            jugador.setEmail(firebaseUser.getEmail());
            jugador.setId_equipo_fk(equipo.getIdEquipo());
            jugador.setNumeroFAA(numeroFAA.getText().toString());
            jugador.setRegistrado(1);

            String sql = "INSERT INTO `jugador` (`DNI`, `idGoogle`, `nombreJugador`, `fechaNacimiento`, `telefonoJugador`, `emailJugador`, `id_equipo_fk`, `numeroFAA`, `registrado`)" +
                    "VALUES ('" + jugador.getDNI() + "', '" + firebaseUser.getUid() + "', '" + jugador.getNombre() + "', '" + jugador.getFechaNacimiento() + "', '" + jugador.getTelefono() + "', '" + jugador.getEmail() + "" +
                    "'," + jugador.getId_equipo_fk() + ", '" + jugador.getNumeroFAA() + "', '" + jugador.getRegistrado() + "')";

            try {
                Statement statement = ConnectionDB.conn.createStatement();
                statement.executeUpdate(sql);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            Intent menuIntent = new Intent(RegisterActivity.this, BottomNavigationViewActivity.class);
            startActivity(menuIntent);
        }
    }
}

