package com.example.wastify_codejod;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class LoginActivity extends AppCompatActivity {
    EditText mail,
            passcode;
    Button loginBtn;
    TextView registerTxt;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore fstore;
    ProgressBar pbar2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        View decorView = getWindow().getDecorView();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
            );
        }

        firebaseAuth=FirebaseAuth.getInstance();
        mail=findViewById(R.id.logInMailID);
        passcode=findViewById(R.id.userpassword);
        registerTxt=findViewById(R.id.registertxt);
        loginBtn=findViewById(R.id.logInBtn);
        pbar2=findViewById(R.id.progressBar2);


        registerTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),SignupActivity.class));
            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String LogMailID=mail.getText().toString().trim();
                String passCode=passcode.getText().toString().trim();

                if(TextUtils.isEmpty(LogMailID)){
                    mail.setError("Email ID is required");
                    return;
                }

                if(TextUtils.isEmpty(passCode)){
                    passcode.setError("Password is required");
                    return;
                }

                if(passCode.length()<6){
                    passcode.setError("Password should contain minimum 6 characters");
                    return;
                }


                pbar2.setVisibility(View.VISIBLE);

                firebaseAuth.signInWithEmailAndPassword(LogMailID,passCode).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){

                            Toast.makeText(LoginActivity.this, "Welcome to Wastify", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),welcomePage.class));


                        }
                        else{
                            Toast.makeText(LoginActivity.this, ""+task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    }
                });
            }
        });

    }
}