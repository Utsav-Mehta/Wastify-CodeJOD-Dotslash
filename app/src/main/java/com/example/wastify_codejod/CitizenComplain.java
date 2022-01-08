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

public class CitizenComplain extends AppCompatActivity {
    FirebaseFirestore fStore;
    EditText complainName,complainPhNumber,complainMain;
    Button submitComplain;
    ImageView backButton;
    FirebaseAuth fAuth;
    ProgressBar pb_complain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_citizen_complain);

        View decorView = getWindow().getDecorView();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
            );
        }


        fStore=FirebaseFirestore.getInstance();
        complainName=findViewById(R.id.nameComplain);
        complainPhNumber=findViewById(R.id.phoneNumComplain);
        complainMain=findViewById(R.id.complainText);
        submitComplain=findViewById(R.id.submitComplainBtn);
        backButton=findViewById(R.id.backButtonImg);
        pb_complain =findViewById(R.id.progressBar4);

        fAuth= FirebaseAuth.getInstance();
        fStore=FirebaseFirestore.getInstance();

        submitComplain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nameComplain=complainName.getText().toString().trim();
                String phoneNum=complainPhNumber.getText().toString().trim();
                String compMain=complainMain.getText().toString().trim();

                if(TextUtils.isEmpty(nameComplain)){
                    complainName.setError("Enter your name!");
                    return;
                }
                if(TextUtils.isEmpty(phoneNum)){
                    complainPhNumber.setError("Enter your phone number!");
                    return;
                }
                if(phoneNum.length()<10){
                    complainPhNumber.setError("Enter a valid phone number!");
                    return;
                }
                if(TextUtils.isEmpty(compMain)){
                    complainMain.setError("Write your complain!");
                    return;
                }

                FirebaseUser fuser = fAuth.getCurrentUser();
                String userID = fAuth.getCurrentUser().getUid();
                DocumentReference documentReference = fStore.collection("complains").document(userID);
                Map<String,Object> complain_map = new HashMap<>();
                complain_map.put("name_complain",nameComplain);
                complain_map.put("phone_complain",phoneNum);
                complain_map.put("complain",compMain);

                pb_complain.setVisibility(View.VISIBLE);

                documentReference.set(complain_map).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(CitizenComplain.this, "Your complain has been registered successfully! Our officer will contact you shortly.", Toast.LENGTH_LONG).show();
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        Log.d(TAG, "onSuccess: complain registered for "+ userID);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(CitizenComplain.this, "Failed to file your complain! Please try after sometime", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        Log.d(TAG, "onFailure: " + e.toString());
                    }
                });

            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder
                        = new AlertDialog
                        .Builder(CitizenComplain.this);
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
                                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
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