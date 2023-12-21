package com.example.is_poi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {

    FirebaseAuth mAuth;

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            Intent intent=new Intent(getApplicationContext(), DashboardActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        EditText formEmail, formPassword;
        Button buttonLogin;
        ProgressBar progressBar;
        TextView textView;

        mAuth=FirebaseAuth.getInstance();
        formEmail=findViewById(R.id.email);
        formPassword=findViewById(R.id.password);
        buttonLogin=findViewById(R.id.login);
        progressBar=findViewById(R.id.progressBar);
        textView=findViewById(R.id.RegistratiNow);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(getApplicationContext(), Registrazione.class);
                startActivity(intent);
                finish();
            }
        });

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                String email, password;
                email=String.valueOf(formEmail.getText());
                password=String.valueOf(formPassword.getText());

                if(TextUtils.isEmpty(email)){
                    Toast.makeText(Login.this,"non é stata inserita l'email", Toast.LENGTH_SHORT);
                    return;
                }

                if(TextUtils.isEmpty(password)){
                    Toast.makeText(Login.this,"non é stata inserita la password", Toast.LENGTH_SHORT);
                    return;
                }

                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                progressBar.setVisibility(View.GONE);
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Toast.makeText(getApplicationContext(),"login successful", Toast.LENGTH_SHORT).show();
                                    Intent intent=new Intent(getApplicationContext(), DashboardActivity.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Toast.makeText(getApplicationContext(),"login failed", Toast.LENGTH_SHORT).show();

                                }
                            }
                        });
            }
        });

    }
}