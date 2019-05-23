package com.headquarter.com.headquarter.activity.activity.fragment;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.headquarter.R;
import com.headquarter.com.headquarter.activity.activity.ConnectionDB;
import com.headquarter.com.headquarter.activity.activity.LogInActivity;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.sql.ResultSet;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private FirebaseAuth firebaseAuth;
    private FirebaseUser user;
    private TextView userEmail;
    private TextView userName;
    private ImageView userImage;
    private Button buttonLogOut;
    private View view;
    private SwipeRefreshLayout swipeRefreshLayout;




    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_profile, container, false);

        swipeRefreshLayout = view.findViewById(R.id.swiperefresh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                showUserData();
            }
        });

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
        showUserData();

        return view;
    }

    public void showUserData() {

        userImage = view.findViewById(R.id.userImage);
        Picasso.get().load(user.getPhotoUrl()).resize(500, 500).transform(new CircleTransform()).into(userImage);

        userEmail = view.findViewById(R.id.userEmail);
        userEmail.setText(user.getEmail());

        userName = view.findViewById(R.id.userName);
        userName.setText(user.getDisplayName());
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

}
