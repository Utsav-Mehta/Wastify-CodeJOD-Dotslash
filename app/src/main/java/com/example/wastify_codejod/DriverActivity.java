package com.example.wastify_codejod;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class DriverActivity extends AppCompatActivity {
    TextView time, date,drivername;
    FirebaseAuth fbaseAuth;
    FirebaseFirestore fstore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drivers);

        time = findViewById(R.id.Time_time);
        date = findViewById(R.id.Date_date);
        drivername=findViewById(R.id.driverName);

        String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        String currentTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());

        date.setText(currentDate);
        time.setText(currentTime);

        fstore=FirebaseFirestore.getInstance();
        fbaseAuth=FirebaseAuth.getInstance();

        String userId= Objects.requireNonNull(fbaseAuth.getCurrentUser()).getUid();
        DocumentReference dr=fstore.collection("users").document(userId);

        dr.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                assert documentSnapshot != null;
                String name=documentSnapshot.getString("fName");
                Intent intent = new Intent(getApplicationContext(), DriverDutyActivity.class);
                intent.putExtra("driver_name",name);
                drivername.setText("Dear"+name);
            }
        });

    }

    public void openimageinput(View v) {
        Intent intent = new Intent(this, DriverDutyActivity.class);
        startActivity(intent);
    }

//    public void openprofile(View v) {
//        Intent intent = new Intent(this, profile.class);
//        startActivity(intent);
//    }

}