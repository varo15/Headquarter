
package com.headquarter.com.headquarter.activity.activity.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.headquarter.R;
import com.headquarter.com.headquarter.activity.activity.others.ConnectionDB;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class LogInActivity extends AppCompatActivity implements View.OnClickListener {

    //defining view objects
    private static final String TAG = "EmailPassword";
    private static final int RC_SIGN_IN = 9001;
    public static GoogleSignInClient mGoogleSignInClient;

    //Declaramos el progressbar
    private ProgressBar progressBar;

    //Declaramos un objeto firebaseAuth
    private FirebaseAuth firebaseAuth;
    public static FirebaseUser firebaseUser = SplashScreenActivity.firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        //inicializamos el objeto firebaseAuth
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        //Vinculamos el progressbar y lo hacemos invisible
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);

        //attaching listener to button
        findViewById(R.id.botonGoogle).setOnClickListener(this);

        if (firebaseUser != null) {
            //new CheckIfUserExist().execute();
            findViewById(R.id.botonGoogle).setVisibility(View.GONE);
            updateUI(firebaseUser);
        }

        // [START config_signin]
        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        // [END config_signin]

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }

    // [START onactivityresult]
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e);
                // [START_EXCLUDE]
                updateUI(null);
                // [END_EXCLUDE]
            }
        }
    }
    // [END onactivityresult]

    // [START auth_with_google]
    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());
        // [START_EXCLUDE silent]
        progressBar.setVisibility(View.VISIBLE);
        // [END_EXCLUDE]

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            firebaseUser = firebaseAuth.getCurrentUser();
                            updateUI(firebaseUser);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                        }

                        // [START_EXCLUDE]

                        //progressBar.setVisibility(View.GONE);
                        // [END_EXCLUDE]
                    }
                });
    }
    // [END auth_with_google]

    /*
        Meodo onStart
        Checkea si algun usuario se logeo alguna vez en la aplicacion y recupera sus datos
     */

    // [START signinGoogle]
    private void signInGoogle() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }
    // [END signinGoogle]


    private void updateUI(FirebaseUser user) {
        if (user != null) {
            progressBar.setVisibility(View.VISIBLE); //Se puede quitar??
            new CheckIfUserExist().execute();
        }
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.botonGoogle) {
            signInGoogle();
        }
    }

    public class CheckIfUserExist extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Void... voids) {

            Boolean checkUser;
            String sql = "SELECT `jugador`.`registrado`, `jugador`.`idGoogle`" +
                    "FROM `jugador`" +
                    "WHERE `jugador`.`idGoogle` = '" + firebaseUser.getUid() + "'";
            Connection connection = ConnectionDB.conn;
            try {
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(sql);
                resultSet.next();
                checkUser = resultSet.getBoolean("registrado");

            } catch (SQLException e) {
                checkUser = false;
                e.printStackTrace();
            }
            return checkUser;

        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {

            if (aBoolean) {

                Intent menuIntent = new Intent(LogInActivity.this, BottomNavigationViewActivity.class);
                progressBar.setVisibility(View.GONE);
                LogInActivity.this.startActivity(menuIntent);


            } else {
                Intent registerIntent = new Intent(LogInActivity.this, RegisterActivity.class);
                progressBar.setVisibility(View.GONE);
                LogInActivity.this.startActivity(registerIntent);
            }
            super.onPostExecute(aBoolean);
        }
    }
}
