package com.headquarter.com.headquarter.activity.activity.fragment;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.headquarter.R;
import com.headquarter.com.headquarter.activity.activity.activity.BottomNavigationViewActivity;
import com.headquarter.com.headquarter.activity.activity.activity.LogInActivity;
import com.headquarter.com.headquarter.activity.activity.objects.Jugador;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private FirebaseAuth firebaseAuth;
    private FirebaseUser user;
    private TextView userEmail;
    private TextView userName;
    private TextView userDNI;
    private TextView userBirthDate;
    private TextView userPhone;
    private TextView userTeam;
    private TextView userFAANumber;
    private ImageView userImage;
    private Jugador jugador = new Jugador();


    private Button buttonLogOut;
    private View view;
    private ResultSet resultSet;
    private ProgressBar progressBar;
    private SwipeRefreshLayout swipeRefreshLayout;

    private String sql;


    public ProfileFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        getUser();

        new ProfileTask().execute();
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_profile, container, false);

        initializeComponents();

        userImage = view.findViewById(R.id.userImage);
        Picasso.get().load(user.getPhotoUrl()).resize(600, 600).transform(new CircleTransform()).into(userImage);

        userEmail.setVisibility(View.VISIBLE);
        userEmail.setText(user.getEmail());

        userName.setVisibility(View.VISIBLE);
        userName.setText(user.getDisplayName());

        buttonLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    firebaseAuth.signOut();

                    Intent intent = new Intent(getActivity(), LogInActivity.class);
                    startActivity(intent);
                } catch (Exception e) {
                    System.out.println(e);
                }


            }
        });

        progressBar = view.findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);

        swipeRefreshLayout = view.findViewById(R.id.pullToRefresh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new ProfileTask().execute();
            }
        });

        return view;
    }

    public void initializeComponents() {
        userEmail = view.findViewById(R.id.userEmail);
        userEmail.setVisibility(View.GONE);
        userName = view.findViewById(R.id.userName);
        userName.setVisibility(View.GONE);
        userDNI = view.findViewById(R.id.userDNI);
        userDNI.setVisibility(View.GONE);
        userBirthDate = view.findViewById(R.id.userDate);
        userBirthDate.setVisibility(View.GONE);
        userPhone = view.findViewById(R.id.userPhone);
        userPhone.setVisibility(View.GONE);
        userTeam = view.findViewById(R.id.userTeam);
        userTeam.setVisibility(View.GONE);
        userFAANumber = view.findViewById(R.id.userFAANumber);
        userFAANumber.setVisibility(View.GONE);
        userEmail = view.findViewById(R.id.userEmail);
        userEmail.setVisibility(View.GONE);
        buttonLogOut = view.findViewById(R.id.buttonLogOut);
    }

    public void showUserData() {

        userImage = view.findViewById(R.id.userImage);

        Picasso.get().load(user.getPhotoUrl()).resize(650, 650).transform(new CircleTransform()).into(userImage);


        userEmail.setVisibility(View.VISIBLE);
        userEmail.setText(jugador.getEmail());

        userName.setVisibility(View.VISIBLE);
        userName.setText(jugador.getNombre());

        userDNI.setVisibility(View.VISIBLE);
        userDNI.setText(jugador.getDNI());

        userBirthDate.setVisibility(View.VISIBLE);
        userBirthDate.setText(jugador.getFechaNacimiento());

        userPhone.setVisibility(View.VISIBLE);
        userPhone.setText(jugador.telefono);

        userTeam.setVisibility(View.VISIBLE);
        userTeam.setText(jugador.getEquipo());

        userFAANumber.setVisibility(View.VISIBLE);
        userFAANumber.setText(jugador.getNumeroFAA());

    }

    @Override
    public void onRefresh() {
    }


    /**
     * CircleTransform
     *Metodo que permite dar una forma redonda a la imagen del usuario
     */
    public class CircleTransform implements Transformation {
        @Override
        public Bitmap transform(Bitmap source) {
            int size = Math.min(source.getWidth(), source.getHeight());

            int x = (source.getWidth() - size) / 2;
            int y = (source.getHeight() - size) / 2;

            Bitmap squaredBitmap = Bitmap.createBitmap(source, x, y, size, size);
            if (squaredBitmap != source) {
                source.recycle();
            }

            Bitmap bitmap = Bitmap.createBitmap(size, size, source.getConfig());

            Canvas canvas = new Canvas(bitmap);
            Paint paint = new Paint();
            BitmapShader shader = new BitmapShader(squaredBitmap,
                    BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP);
            paint.setShader(shader);
            paint.setAntiAlias(true);

            float r = size / 2f;
            canvas.drawCircle(r, r, r, paint);

            squaredBitmap.recycle();
            return bitmap;
        }

        @Override
        public String key() {
            return "circle";
        }

    }

    /**
     * ProfileTask
     *Clase Asincrona en la cual se realiza la consulta de los datos del jugador
     */
    private class ProfileTask extends AsyncTask {

        @Override
        protected Object doInBackground(Object[] objects) {

            sql = "SELECT `jugador`.*, `equipo`.`nombreEquipo` FROM `jugador`" +
                    "LEFT JOIN `equipo` ON `jugador`.`id_equipo_fk` = `equipo`.`idEquipo`" +
                    "WHERE jugador.idGoogle = '" + user.getUid() + "'";

            try {
                Statement statement = BottomNavigationViewActivity.statement;
                resultSet = statement.executeQuery(sql);
                resultSet.next();

                jugador.setDNI(resultSet.getString("DNI"));
                jugador.setNombre(user.getDisplayName());
                jugador.setFechaNacimiento(resultSet.getString("fechaNacimiento"));
                jugador.setEmail(resultSet.getString("emailJugador"));
                jugador.setTelefono(resultSet.getString("telefonoJugador"));
                jugador.setEquipo(resultSet.getString("nombreEquipo"));
                jugador.setNumeroFAA(resultSet.getString("numeroFAA"));


            } catch (SQLException e) {
                System.out.println("CAGADA");
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            progressBar.setVisibility(View.GONE);
            showUserData();
            swipeRefreshLayout.setRefreshing(false);
        }
    }

    /**
     * getUser
     * Obtiene el jugador
     */
    private void getUser() {
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
    }
}
