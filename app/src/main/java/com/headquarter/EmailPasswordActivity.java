package com.headquarter;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class EmailPasswordActivity extends BaseActivity implements View.OnClickListener {

    //defining view objects
    private static final String TAG = "EmailPassword";
    private EditText TextEmail;
    private EditText TextPassword;
    private TextView TextUser;


    //Declaramos un objeto firebaseAuth
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //inicializamos el objeto firebaseAuth
        firebaseAuth = FirebaseAuth.getInstance();

        //Referenciamos los views
        TextEmail = findViewById(R.id.textEmail);
        TextPassword = findViewById(R.id.textPassword);
        TextUser = findViewById(R.id.textUser);


        //attaching listener to button
        findViewById(R.id.botonRegistrar).setOnClickListener(this);
        findViewById(R.id.botonLogin).setOnClickListener(this);
        findViewById(R.id.botonLogOut).setOnClickListener(this);


    }

    /*
        Meodo onStart
        Checkea si algun usuario se logeo alguna vez en la aplicacion y recupera sus datos
     */
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        updateUI(currentUser);

    }



    /*
        Metodo crateAccount Email & Pass
        Crea una cuenta en Firebase con un usuario y contraseña
     */
    private void createAccount(String email, String password) {
        Log.d(TAG, "createAccount:" + email);
        if (!validateForm()) {
            return;
        }

        showProgressDialog();

        // [START create_user_with_email]
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(EmailPasswordActivity.this, "Error en el registro",
                                    Toast.LENGTH_SHORT).show();
                        }

                        hideProgressDialog();

                    }
                });
        // [END create_user_with_email]
    }

    /*
        Metodo signIn Email & Pass
        Inicia sesion en la aplicacion con un usuario y una contraseña ya existentes en Firebase
     */
    private void signIn(String email, String password) {
        Log.d(TAG, "signIn:" + email);
        if (!validateForm()) {
            return;
        }
        showProgressDialog();

        // [START sign_in_with_email]
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            Toast.makeText(EmailPasswordActivity.this, "Sesion iniciada", Toast.LENGTH_SHORT).show();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(EmailPasswordActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                        }

                        hideProgressDialog();

                    }
                });
        // [END sign_in_with_email]
    }


    /*
        Metodo signOut
        Email
     */
    private void signOut() {
        firebaseAuth.signOut();
        updateUI(null);
    }

    private boolean validateForm() {
        boolean valid = true;

        String email = TextEmail.getText().toString();
        if (TextUtils.isEmpty(email)) {
            TextEmail.setError("Required.");
            valid = false;
        } else {
            TextEmail.setError(null);
        }

        String password = TextPassword.getText().toString();
        if (TextUtils.isEmpty(password)) {
            TextPassword.setError("Required.");
            valid = false;
        } else {
            TextPassword.setError(null);
        }

        return valid;
    }

    private void updateUI(FirebaseUser user) {
        hideProgressDialog();
        if (user != null) {
            TextUser.setText(user.getEmail());

            findViewById(R.id.botonRegistrar).setVisibility(View.GONE);
            findViewById(R.id.botonLogin).setVisibility(View.GONE);
            findViewById(R.id.botonLogOut).setVisibility(View.VISIBLE);

        } else {
            TextUser.setText("Problemas");

            findViewById(R.id.botonRegistrar).setVisibility(View.VISIBLE);
            findViewById(R.id.botonLogin).setVisibility(View.VISIBLE);
            findViewById(R.id.botonLogOut).setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View v) {

        int i = v.getId();
        if (i == R.id.botonRegistrar) {
            createAccount(TextEmail.getText().toString(), TextPassword.getText().toString());
        } else if (i == R.id.botonLogin) {
            signIn(TextEmail.getText().toString(), TextPassword.getText().toString());
        } else if (i == R.id.botonLogOut) {
            signOut();
        }else if (i == R.id.botonGoogle){


        }
    }
}
