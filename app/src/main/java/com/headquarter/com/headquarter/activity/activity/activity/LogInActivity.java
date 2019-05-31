
package com.headquarter.com.headquarter.activity.activity.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

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
    private GoogleSignInClient mGoogleSignInClient;
    private EditText TextEmail;
    private EditText TextPassword;
    private TextView TextUser;
    private ProgressBar progressBar;


    //Declaramos un objeto firebaseAuth
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //inicializamos el objeto firebaseAuth
        firebaseAuth = FirebaseAuth.getInstance();

        //Referenciamos los views
        TextEmail = findViewById(R.id.textEmail2);
        TextPassword = findViewById(R.id.textPassword);
        TextUser = findViewById(R.id.textEmail2);
        progressBar = findViewById(R.id.progressBar);


        //attaching listener to button
        findViewById(R.id.botonRegistrar).setOnClickListener(this);
        findViewById(R.id.botonLogin).setOnClickListener(this);
        findViewById(R.id.botonGoogle).setOnClickListener(this);

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
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            updateUI(null);
                        }

                        // [START_EXCLUDE]

                        progressBar.setVisibility(View.GONE);
                        // [END_EXCLUDE]
                    }
                });
    }
    // [END auth_with_google]

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

        progressBar.setVisibility(View.VISIBLE);

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
                            Toast.makeText(LogInActivity.this, "Error en el registro",
                                    Toast.LENGTH_SHORT).show();
                        }


                        progressBar.setVisibility(View.GONE);

                    }
                });
        // [END create_user_with_email]
    }

    // [START signinGoogle]
    private void signInGoogle() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }
    // [END signinGoogle]

    /*
        Metodo signIn Email & Pass
        Inicia sesion en la aplicacion con un usuario y una contraseña ya existentes en Firebase
     */
    private void signIn(String email, String password) {
        Log.d(TAG, "signIn:" + email);
        if (!validateForm()) {
            return;
        }
        progressBar.setVisibility(View.VISIBLE);

        // [START sign_in_with_email]
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            Toast.makeText(LogInActivity.this, "Sesion iniciada", Toast.LENGTH_SHORT).show();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(LogInActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                        }

                        progressBar.setVisibility(View.GONE);

                    }
                });
        // [END sign_in_with_email]
    }


    /*
        Metodo signOut
        Email
     */
    public void signOut() {
        // Firebase sign out
        firebaseAuth.signOut();

        // Google sign out
        mGoogleSignInClient.signOut().addOnCompleteListener(this,
                new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        updateUI(null);
                    }
                });
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
        progressBar.setVisibility(View.GONE);



        if (user != null) {
            firebaseUser = user;
            new CheckIfUserExist().execute();
            /*Intent menuIntent = new Intent(this, BottomNavigationViewActivity.class);
            LogInActivity.this.startActivity(menuIntent);
*/
        } else {

            /*findViewById(R.id.botonRegistrar).setVisibility(View.VISIBLE);
            findViewById(R.id.botonLogin).setVisibility(View.VISIBLE);*/
        }
    }

    @Override
    public void onClick(View v) {

        int i = v.getId();
        if (i == R.id.botonRegistrar) {
            createAccount(TextEmail.getText().toString(), TextPassword.getText().toString());
        } else if (i == R.id.botonLogin) {
            signIn(TextEmail.getText().toString(), TextPassword.getText().toString());
        } else if (i == R.id.botonGoogle) {
            signInGoogle();

        }
    }

    public class CheckIfUserExist extends AsyncTask<Void, Void, Boolean> {

        private Boolean checkUser = false;



        @Override
        protected void onPreExecute() {
            new ConnectionDB().execute();
        }

        @Override
        protected Boolean doInBackground(Void... voids) {

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
            progressBar.setVisibility(View.GONE);
            if (aBoolean) {
                Intent menuIntent = new Intent(LogInActivity.this, BottomNavigationViewActivity.class);
                LogInActivity.this.startActivity(menuIntent);

            } else {
                Intent registerIntent = new Intent(LogInActivity.this, RegisterActivity.class);
                LogInActivity.this.startActivity(registerIntent);
            }
            super.onPostExecute(aBoolean);
        }
    }
}
