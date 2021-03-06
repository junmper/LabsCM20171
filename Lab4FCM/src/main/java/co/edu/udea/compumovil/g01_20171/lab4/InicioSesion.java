package co.edu.udea.compumovil.g01_20171.lab4;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class InicioSesion extends AppCompatActivity{

    private EditText inputEmail, inputPassword;
    private FirebaseAuth auth;
    private ProgressBar progressBar;
    private Button btnSignup, btnLogin, btnReset;

            @Override
            protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);

                //Get Firebase auth instance
                auth = FirebaseAuth.getInstance();

                if (auth.getCurrentUser() != null) {
                    startActivity(new Intent(InicioSesion.this, Main.class));
                    finish();
                }

                setContentView(R.layout.activity_inicio);

                //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
                //setSupportActionBar(toolbar);

                inputEmail = (EditText) findViewById(R.id.email);
                inputPassword = (EditText) findViewById(R.id.password);
                progressBar = (ProgressBar) findViewById(R.id.progressBar);
                btnSignup = (Button) findViewById(R.id.btn_signup);
                btnLogin = (Button) findViewById(R.id.btn_login);
                btnReset = (Button) findViewById(R.id.btn_reset_password);

                //Get Firebase auth instance
                auth = FirebaseAuth.getInstance();

                btnSignup.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(InicioSesion.this, RegistroUsuario.class));
                    }
                });

                btnReset.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(InicioSesion.this, ResetPasswordActivity.class));
                    }
                });

                btnLogin.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String email = inputEmail.getText().toString();
                        final String password = inputPassword.getText().toString();

                        if (TextUtils.isEmpty(email)) {
                            Toast.makeText(getApplicationContext(), "Entre un correo por favor!", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        if (TextUtils.isEmpty(password)) {
                            Toast.makeText(getApplicationContext(), "Entre una contraseña por favor!", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        progressBar.setVisibility(View.VISIBLE);

                        //authenticate user
                        auth.signInWithEmailAndPassword(email, password)
                                .addOnCompleteListener(InicioSesion.this, new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        // If sign in fails, display a message to the user. If sign in succeeds
                                        // the auth state listener will be notified and logic to handle the
                                        // signed in user can be handled in the listener.
                                        progressBar.setVisibility(View.GONE);
                                        if (!task.isSuccessful()) {
                                            // there was an error
                                            if (password.length() < 6) {
                                                inputPassword.setError(getString(R.string.minimum_password));
                                            } else {
                                                Toast.makeText(InicioSesion.this, getString(R.string.auth_failed), Toast.LENGTH_LONG).show();
                                            }
                                        } else {
                                            Intent intent = new Intent(InicioSesion.this, Main.class);
                                            startActivity(intent);
                                            finish();
                                        }
                                    }
                                });
                    }
                });
            }


        }

