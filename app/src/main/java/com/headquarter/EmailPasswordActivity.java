package com.headquarter;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class EmailPasswordActivity extends AppCompatActivity implements View.OnClickListener {

    //defining view objects
    private static final String TAG = "EmailPassword";
    private EditText TextEmail;
    private EditText TextPassword;
    private ProgressDialog progressDialog;


    //Declaramos un objeto firebaseAuth
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //inicializamos el objeto firebaseAuth
        firebaseAuth = FirebaseAuth.getInstance();

        //Referenciamos los views
        TextEmail = (EditText) findViewById(R.id.TxtEmail);
        TextPassword = (EditText) findViewById(R.id.TxtPassword);

        Button btnRegistrar = (Button) findViewById(R.id.botonRegistrar);
        Button btnLogin = findViewById(R.id.botonLogin);

        progressDialog = new ProgressDialog(this);

        //attaching listener to button
        btnRegistrar.setOnClickListener(this);
        btnLogin.setOnClickListener(this);


        //Obtenemos el email y la contraseña desde las cajas de texto
        String email = TextEmail.getText().toString().trim();
        String password = TextPassword.getText().toString().trim();

    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();

    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.botonRegistrar) {

            createAccount(TextEmail.getText().toString(), TextPassword.getText().toString());
        } else if (i == R.id.botonLogin) {
            signIn(TextEmail.getText().toString(), TextPassword.getText().toString());
        }
    }

    /*
        Metodos de registro, login, validacion
     */

    //Metodo de Registro de nuevo usuario
    private void createAccount(String email, String password) {
        Log.d(TAG, "createAccount:" + email);
        if (!validateForm()) {
            return;
        }

        progressDialog.show();

        // [START create_user_with_email]
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            Toast.makeText(EmailPasswordActivity.this, "Usuario registrado", Toast.LENGTH_SHORT).show();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(EmailPasswordActivity.this, "Error en el registro",
                                    Toast.LENGTH_SHORT).show();
                        }

                        // [START_EXCLUDE]
                        progressDialog.dismiss();
                        // [END_EXCLUDE]
                    }
                });
        // [END create_user_with_email]
    }

    //Metodo de Sign In
    private void signIn(String email, String password) {
        Log.d(TAG, "signIn:" + email);
        if (!validateForm()) {
            return;
        }

        progressDialog.show();

        // [START sign_in_with_email]
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            Toast.makeText(EmailPasswordActivity.this, "Usuario Logeado", Toast.LENGTH_SHORT).show();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(EmailPasswordActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                        }

                        // [START_EXCLUDE]
                        if (!task.isSuccessful()) {
                            Toast.makeText(EmailPasswordActivity.this, "Usuario Logeado", Toast.LENGTH_SHORT).show();
                        }
                        progressDialog.dismiss();
                        // [END_EXCLUDE]
                    }
                });
        // [END sign_in_with_email]
    }


    //Metodo de validacion de campos
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


}
