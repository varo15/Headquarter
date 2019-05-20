package com.headquarter.com.headquarter.activity.activity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.headquarter.R;
import com.squareup.picasso.Picasso;

public class Welcome extends BaseActivity{


    private TextView TxtEmail;
    private TextView TxtName;
    private ImageView ImgUser;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        TxtEmail = findViewById(R.id.textEmail2);
        TxtName = findViewById(R.id.textName);
        ImgUser = findViewById(R.id.imageUser);

        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();

        TxtEmail.setText(user.getEmail().toString());
        TxtName.setText(user.getDisplayName().toString());
        Picasso.get().load(user.getPhotoUrl()).resize(500,500).into(ImgUser);



    }
}


