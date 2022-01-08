package com.example.wastify_codejod;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class DriverLeaveActivity extends AppCompatActivity {
    FirebaseFirestore fStore;
    EditText leaveName,leaveDates,leaveReason;
    Button submitLeave;
    ImageView backButton2;
    FirebaseAuth fAuth;
    ProgressBar pb_leave;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_leave);

        View decorView = getWindow().getDecorView();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
            );
        }

        leaveName=findViewById(R.id.nameLeaveApplication);
        leaveDates=findViewById(R.id.leaveDates);
        leaveReason=findViewById(R.id.leaveText);
        submitLeave=findViewById(R.id.submitLeave);
        backButton2=findViewById(R.id.backButtonImg);
        pb_leave =findViewById(R.id.pbInDl);

        fAuth= FirebaseAuth.getInstance();
        fStore=FirebaseFirestore.getInstance();

        submitLeave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String leave_name=leaveName.getText().toString().trim();
                String leave_date=leaveDates.getText().toString().trim();
                String leave_reason=leaveReason.getText().toString().trim();

                if(TextUtils.isEmpty(leave_name)){
                    leaveName.setError("Enter your name!");
                    return;
                }
                if(TextUtils.isEmpty(leave_date)){
                    leaveDates.setError("Enter the dates");
                    return;
                }

                if(TextUtils.isEmpty(leave_reason)){
                    leaveReason.setError("Write your reason!");
                    return;
                }

                FirebaseUser fuser = fAuth.getCurrentUser();
                String userID = fAuth.getCurrentUser().getUid();
                DocumentReference documentReference = fStore.collection("leaves").document(userID);
                Map<String,Object> leave_map = new HashMap<>();
                leave_map.put("name_leave",leave_name);
                leave_map.put("date_leave",leave_date);
                leave_map.put("leave_reason",leave_reason);

                pb_leave.setVisibility(View.VISIBLE);

                documentReference.set(leave_map).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(DriverLeaveActivity.this, "Your leave application has been registered successfully! Your designated authority will contact you shortly.", Toast.LENGTH_LONG).show();
                        startActivity(new Intent(getApplicationContext(), DriverActivity.class));
                        Log.d(TAG, "onSuccess: leave registered for "+ userID);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(DriverLeaveActivity.this, "Failed to apply your leave! Please try after sometime", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), DriverActivity.class));
                        Log.d(TAG, "onFailure: " + e.toString());
                    }
                });

            }
        });

        backButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder
                        = new AlertDialog
                        .Builder(DriverLeaveActivity.this);
                builder.setMessage("All your changes will be discared! Are you sure you want to exit?");
                // Set Alert Title
                builder.setTitle("Alert !");

                builder
                        .setPositiveButton(
                                "Yes",
                                new DialogInterface
                                        .OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog,
                                                        int which)
                                    {
                                        startActivity(new Intent(getApplicationContext(), DriverActivity.class));
                                    }
                                });


                builder
                        .setNegativeButton(
                                "No",
                                new DialogInterface
                                        .OnClickListener() {

                                    @Override
                                    public void onClick(DialogInterface dialog,
                                                        int which)
                                    {
                                        dialog.cancel();
                                    }
                                });

                AlertDialog alertDialog = builder.create();

                alertDialog.show();
            }
        });


    }
}