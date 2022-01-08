package com.example.wastify_codejod;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.Objects;

public class SuperAdminActivity extends AppCompatActivity {
    CardView monitorDriver;
    CardView citizenComplain;
    CardView driverLeave;
    TextView logoutText;
    FirebaseAuth fbaseAuth;
    FirebaseFirestore fstore;
    TextView superadminname;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_super_admin);

        View decorView = getWindow().getDecorView();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
            );
        }

        monitorDriver=findViewById(R.id.cardMonitor);
        citizenComplain=findViewById(R.id.cardCitizen);
        driverLeave=findViewById(R.id.cardDriver);
        logoutText=findViewById(R.id.logoutTxt);
        superadminname=findViewById(R.id.superadminName);


        fstore=FirebaseFirestore.getInstance();
        fbaseAuth=FirebaseAuth.getInstance();

        String userId= Objects.requireNonNull(fbaseAuth.getCurrentUser()).getUid();
        DocumentReference dr=fstore.collection("users").document(userId);

        dr.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                assert documentSnapshot != null;
                String name=documentSnapshot.getString("fName");
                superadminname.setText(name);
            }
        });

        logoutText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),LoginActivity.class));
                fbaseAuth.signOut();
            }
        });

        monitorDriver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goMonitor=new Intent(SuperAdminActivity.this,AdminDriverMonitor.class);
                startActivity(goMonitor);
            }
        });


        citizenComplain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goComplain=new Intent(SuperAdminActivity.this, AdminComplainActivity.class);
                startActivity(goComplain);
            }
        });


        driverLeave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goLeave=new Intent(SuperAdminActivity.this,AdminDriverLeave.class);
                startActivity(goLeave);
            }
        });

    }
}