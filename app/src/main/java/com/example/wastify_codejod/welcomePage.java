package com.example.wastify_codejod;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.Objects;

public class welcomePage extends AppCompatActivity {
    Button contBtn;
    FirebaseAuth fbaseAuth;
    String userId;
    FirebaseFirestore fstore;
    ProgressBar pbar3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_page);
        contBtn=findViewById(R.id.contBtn);
        pbar3 =findViewById(R.id.progressBar3);

        View decorView = getWindow().getDecorView();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
            );
        }

        fstore=FirebaseFirestore.getInstance();
        fbaseAuth=FirebaseAuth.getInstance();

        userId= Objects.requireNonNull(fbaseAuth.getCurrentUser()).getUid();
        DocumentReference dr=fstore.collection("users").document(userId);

        dr.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                assert documentSnapshot != null;
                String type=documentSnapshot.getString("type");
                String name=documentSnapshot.getString("fName");
                String admin = "Admin";
                String citizen ="Citizen";
                String driver="Driver";

                contBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        assert type != null;
                        if(type.equals(citizen)){
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                            Toast.makeText(welcomePage.this, "Welcome! "+name, Toast.LENGTH_SHORT).show();
                            pbar3.setVisibility(View.VISIBLE);
                        }

                        else if (type.equals(driver)){
                            startActivity(new Intent(getApplicationContext(), DriverActivity.class));
                            Toast.makeText(welcomePage.this, "Welcome! "+name, Toast.LENGTH_SHORT).show();
                            pbar3.setVisibility(View.VISIBLE);
                        }

                        else if (type.equals(admin)){
                            startActivity(new Intent(getApplicationContext(), SuperAdminActivity.class));
                            Toast.makeText(welcomePage.this, "Welcome! "+name, Toast.LENGTH_SHORT).show();
                            pbar3.setVisibility(View.VISIBLE);
                        }

                    }
                });

            }
        });
    }
}