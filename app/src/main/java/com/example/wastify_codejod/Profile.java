package com.example.wastify_codejod;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.Objects;

public class Profile extends AppCompatActivity {
    FirebaseAuth firebaseAuth;
    FirebaseFirestore fstore;
    TextView driver_prof_name,boss_name;
    Button logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        View decorView = getWindow().getDecorView();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
            );
        }
        driver_prof_name=findViewById(R.id.drivername);
        logout=findViewById(R.id.logoutInProf);
        boss_name=findViewById(R.id.bossname);
        fstore=FirebaseFirestore.getInstance();
        firebaseAuth=FirebaseAuth.getInstance();

        String userId= Objects.requireNonNull(firebaseAuth.getCurrentUser()).getUid();
        DocumentReference dr=fstore.collection("users").document(userId);

        dr.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                assert documentSnapshot != null;
                String name=documentSnapshot.getString("fName");
                Intent intent = new Intent(getApplicationContext(), DriverDutyActivity.class);
                intent.putExtra("driver_name",name);
                driver_prof_name.setText("Name: "+name);
                boss_name.setText("Authority name: Mr. RK Singh,Director-Solid Waste Managment cell)");
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),SignupActivity.class));
                firebaseAuth.signOut();
            }
        });

    }
}