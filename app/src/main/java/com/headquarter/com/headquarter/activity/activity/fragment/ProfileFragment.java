package com.headquarter.com.headquarter.activity.activity.fragment;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
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
import com.headquarter.com.headquarter.activity.activity.BottomNavigationViewActivity;
import com.headquarter.com.headquarter.activity.activity.ConnectionDB;
import com.headquarter.com.headquarter.activity.activity.LogInActivity;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    //Variables usuario firebase
    private FirebaseAuth firebaseAuth;
    private FirebaseUser user;
    //Variables datos del usuario
    private TextView userEmail;
    private TextView userName;
    private TextView userDNI;
    private TextView userBirthDate;
    private TextView userPhone;
    private TextView userTeam;
    private TextView userFAANumber;
    private ImageView userImage;


    private Button buttonLogOut;
    private View view;
    private ResultSet resultSet;
    private ProgressBar progressBar;

    //Variable consulta sql
    private String sql;


    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        sql = "SELECT * FROM `jugador` WHERE idGoogle = '" + user.getUid() + "'";


        new ProfileTask().execute();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_profile, container, false);

        buttonLogOut = view.findViewById(R.id.buttonLogOut);
        buttonLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Cerrar sesion y volver a la pagina principal
                try {
                    firebaseAuth.signOut();
                    Intent intent = new Intent(getActivity(), LogInActivity.class);
                    startActivity(intent);
                } catch (Exception e) {
                    System.out.println(e);
                }

            }
        });

        return view;
    }

    public void showUserData() {

        try {
            userImage = view.findViewById(R.id.userImage);
            Picasso.get().load(user.getPhotoUrl()).resize(500, 500).transform(new CircleTransform()).into(userImage);

            userEmail = view.findViewById(R.id.userEmail);
            userEmail.setVisibility(View.VISIBLE);
            userEmail.setText(user.getEmail());

            userName = view.findViewById(R.id.userName);
            userName.setVisibility(View.VISIBLE);
            userName.setText(user.getDisplayName());

            userDNI = view.findViewById(R.id.userDNI);
            userDNI.setVisibility(View.VISIBLE);
            userDNI.setText(resultSet.getString("DNI"));

            userBirthDate = view.findViewById(R.id.userDate);
            userBirthDate.setVisibility(View.VISIBLE);
            userBirthDate.setText(resultSet.getString("fecha_nacimiento"));

            userPhone = view.findViewById(R.id.userPhone);
            userPhone.setVisibility(View.VISIBLE);
            userPhone.setText(resultSet.getString("telefono"));

            userTeam = view.findViewById(R.id.userTeam);
            userTeam.setVisibility(View.VISIBLE);
            userTeam.setText(resultSet.getString("id_equipo_fk"));

            userFAANumber = view.findViewById(R.id.userFAANumber);
            userFAANumber.setVisibility(View.VISIBLE);
            userFAANumber.setText(resultSet.getString("numero_FAA"));


        } catch (Exception e) {

        }
    }

    @Override
    public void onRefresh() {

    }


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

    private class ProfileTask extends AsyncTask {

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected Object doInBackground(Object[] objects) {

            progressBar = view.findViewById(R.id.progressBar);
            progressBar.setVisibility(View.VISIBLE);
            try {
                Statement statement = BottomNavigationViewActivity.connection.createStatement();
                resultSet = statement.executeQuery(sql);
                resultSet.next();


            } catch (SQLException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Object o) {


            try {
                progressBar.setVisibility(View.GONE);
                showUserData();
                BottomNavigationViewActivity.connection.close();

            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
    }

}
