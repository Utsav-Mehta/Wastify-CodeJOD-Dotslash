package com.example.wastify_codejod;

import static android.content.ContentValues.TAG;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class DriverDutyActivity extends AppCompatActivity {
    Button updateBtn;
    FusedLocationProviderClient fusedLocationProviderClient;
    Button uploadBtn,enDutyBtn;
    FirebaseAuth fAuth;
    ImageView driverImage;
    FirebaseFirestore fStore;
    FirebaseStorage firebaseStorage;
    StorageReference storageReference;
    Uri imageUri;
    TextView date,time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_duty);
        updateBtn =findViewById(R.id.updateBtn);
        uploadBtn=findViewById(R.id.uploadBtn);
        driverImage=findViewById(R.id.image);
        enDutyBtn=findViewById(R.id.endDutyBtn);
        date=findViewById(R.id.driverDate);
        time=findViewById(R.id.driverTime);


        View decorView = getWindow().getDecorView();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
            );
        }


            //firebase
        fAuth= FirebaseAuth.getInstance();
        fStore=FirebaseFirestore.getInstance();
        firebaseStorage=FirebaseStorage.getInstance();
        storageReference=firebaseStorage.getReference();

        Intent intent = getIntent();
        String name = intent.getStringExtra("driver_name");

        String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        String currentTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());

        date.setText(currentDate);
        time.setText(currentTime);

        fusedLocationProviderClient= LocationServices.getFusedLocationProviderClient(this);

        enDutyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder
                        = new AlertDialog
                        .Builder(DriverDutyActivity.this);
                builder.setMessage("Are you sure you want end your duty?");
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

        uploadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                galleryActivityResultLauncher.launch(intent);
            }
        });


        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StorageReference riversRef = storageReference.child("images/"+currentDate+""+currentTime+".jpg");
                riversRef.putFile(imageUri)
                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull  Exception e) {
                                Log.e(TAG, "onFailure: "+e);
                            }
                        });
                if(ActivityCompat.checkSelfPermission(DriverDutyActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED){
                    getLocation();
                }
                else{
                    ActivityCompat.requestPermissions(DriverDutyActivity.this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},44);
                }
            }
        });

    }
    private ActivityResultLauncher<Intent> galleryActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        imageUri = data.getData();

                        driverImage.setImageURI(imageUri);
                    }
                    else {
                        Toast.makeText(DriverDutyActivity.this, "Cancelled...", Toast.LENGTH_SHORT).show();
                    }
                }
            }
    );

    @SuppressLint("MissingPermission")
    private void getLocation() {
        fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {
                Location location=task.getResult();
                if(location!=null){
                    try{
                        Geocoder geocoder= new Geocoder(DriverDutyActivity.this, Locale.getDefault());
                        List<Address> addresses = geocoder.getFromLocation(
                                location.getLatitude(),location.getLongitude(),1
                        );
                        double latitude=addresses.get(0).getLatitude();
                        double longitude=addresses.get(0).getLongitude();
                        String countryName=addresses.get(0).getCountryName();
                        String locality=addresses.get(0).getLocality();
                        String AddressLine=addresses.get(0).getAddressLine(0);
                        String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
                        String currentTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());

                        FirebaseUser fuser = fAuth.getCurrentUser();
                         String userID = fAuth.getCurrentUser().getUid();
                        DocumentReference documentReference = fStore.collection("driver-locations").document(userID);
                        Map<String,Object> locs = new HashMap<>();
                        locs.put("driver_name","Krishnakumar Yadav");
                        locs.put("latitude",latitude);
                        locs.put("longitude",longitude);
                        locs.put("locality",locality);
                        locs.put("address_line",AddressLine);
                        locs.put("date",currentDate);
                        locs.put("time",currentTime);

                        documentReference.set(locs).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.d(TAG, "onSuccess: user Profile is created for "+ userID);
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.d(TAG, "onFailure: " + e.toString());
                            }
                        });
                    }
                    catch (IOException e){
                        e.printStackTrace();
                    }
                }
            }
        });
    }
}