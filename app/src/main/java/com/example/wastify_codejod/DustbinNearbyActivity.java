package com.example.wastify_codejod;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DustbinNearbyActivity extends AppCompatActivity {
    Spinner spinner;
    Button find;
    SupportMapFragment supportMapFragment;
    GoogleMap Map;
    FusedLocationProviderClient fusedLocationProviderClient;
    Double currentLat = (double) 0;
    Double currentLong = (double) 0;
    ArrayList<LatLng> arrayList = new ArrayList<>();
    LatLng ad1 = new LatLng(22.997436, 72.603809);
    LatLng ad2 = new LatLng(23.039579, 72.630937);
    LatLng ad3 = new LatLng(23.049906, 72.670508);
    LatLng ad4 = new LatLng(23.046166, 72.530731);
    LatLng vd1 = new LatLng(22.294151, 73.194624);
    LatLng vd2 = new LatLng(22.283371, 73.232127);
    LatLng vd3 = new LatLng(22.309233, 73.187942);
    LatLng vd4 = new LatLng(22.340704, 73.201061);
    LatLng gd1 = new LatLng(23.196180, 72.642463);
    LatLng gd2 = new LatLng(23.158980, 72.663693);
    LatLng gd3 = new LatLng(23.258233, 72.652633);
    LatLng gd4 = new LatLng(23.188537, 72.629945);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dustbin_nearby);

        View decorView = getWindow().getDecorView();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
            );
        }

        spinner = findViewById(R.id.sp_type);
        find = findViewById(R.id.find);
        supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.google_map);

        arrayList.add(ad1);arrayList.add(ad2);arrayList.add(ad3);arrayList.add(ad4);
        arrayList.add(vd1);arrayList.add(vd2);arrayList.add(vd3);arrayList.add(vd4);
        arrayList.add(gd1);arrayList.add(gd2);arrayList.add(gd3);arrayList.add(gd4);

        String[] place= {"Ahmedabad","Vadodara","Gandhinagar"};
        String[] spPlace = {"Ahmedabad","Vadodara","Gandhinagar"};

        spinner.setAdapter(new ArrayAdapter<>(DustbinNearbyActivity.this, android.R.layout.simple_spinner_dropdown_item, spPlace));

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        if (ActivityCompat.checkSelfPermission(DustbinNearbyActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            getCurrentLocation();
        } else {
            ActivityCompat.requestPermissions(DustbinNearbyActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
        }

        find.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int i = spinner.getSelectedItemPosition();
                String url = "https://maps.googleapis.com/maps/api/place/nearbysearch/json" +
                        "?location=" + currentLat + "," + currentLong + "&radius=5000" + "&types=" + place[i] +
                        "&sensor=true" + "&key=" + getResources().getString(R.string.google_map_key);

                new PlaceTask().execute(url);

                DisplayTrack(place[i]);
            }
        });

    }

    private void DisplayTrack(String ar) {
        try {
            Uri uri=null;
            if(ar.equals("Ahmedabad")) {
                uri = Uri.parse("https://www.google.co.in/maps/dir/HimaliyaMall/Maninagar/Bapunagar/RaspanCrossRoad");
            }else if(ar.equals("Vadodara")) {
                uri = Uri.parse("https://www.google.co.in/maps/dir/KubereshwarMarg/LukshmiVilasPalace/KalaGhodaCircle/SamaLake");
            }else{
                uri = Uri.parse("https://www.google.co.in/maps/dir/PDEU/DAIICT/Ch0/Samarpancircle");
            }
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            intent.setPackage("com.google.android.apps.maps");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }catch(ActivityNotFoundException e){
            Uri uri = Uri.parse("https://play.google.com/store/apps/detail?id=com.google.android.apps.maps");
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    }

    private void getCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        Task<Location> task = fusedLocationProviderClient.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(@NonNull Location location) {
                if (location != null) {
                    currentLat = location.getLatitude();
                    currentLong = location.getLongitude();
                    supportMapFragment.getMapAsync(new OnMapReadyCallback() {
                        @Override
                        public void onMapReady(@NonNull GoogleMap googleMap) {
                            Map = googleMap;
                            for (int i = 0; i < arrayList.size(); i++) {
                                if(i%2==0) {
                                    Map.addMarker(new MarkerOptions().position(arrayList.get(i)).title("Dry Dustbin"));
                                }else{
                                    Map.addMarker(new MarkerOptions().position(arrayList.get(i)).title("Wet Dustbin"));
                                }
                                Map.animateCamera(CameraUpdateFactory.zoomTo(15.0f));
                                Map.moveCamera(CameraUpdateFactory.newLatLng(arrayList.get(i)));
                            }
                        }
                    });
                }
            }
        });
    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 44) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getCurrentLocation();
            }
        }
    }

    private class PlaceTask extends AsyncTask<String, Integer, String> {
        @Override
        protected String doInBackground(String... strings) {
            String data = null;
            try {
                data = downloadurl(strings[0]);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return data;
        }

        @Override
        protected void onPostExecute(String s) {
            new ParserTask().execute(s);
        }
    }

    private String downloadurl(String string) throws IOException {
        URL url = new URL(string);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.connect();
        InputStream stream = connection.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        StringBuilder builder = new StringBuilder();
        String line="";
        while ((line = reader.readLine()) != null) {
            builder.append(line);
        }
        String data = builder.toString();
        reader.close();
        return data;
    }
    private class ParserTask extends AsyncTask<String, Integer, List<HashMap<String, String>>> {
        @Override
        protected List<HashMap<String, String>> doInBackground(String... strings) {
            JsonParser jsonParser = new JsonParser();
            List<HashMap<String, String>> mapList = null;
            JSONObject object = null;
            try {
                object = new JSONObject(strings[0]);
                mapList = jsonParser.parseResult(object);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return mapList;
        }

        @Override
        protected void onPostExecute(List<HashMap<String, String>> hashMaps) {
//            Map.clear();
            for (int i = 0; i < hashMaps.size(); i++) {
                HashMap<String, String> hashMapList = hashMaps.get(i);
                double lat = Double.parseDouble(hashMapList.get("lat"));
                double lng = Double.parseDouble(hashMapList.get("lng"));
                String name = hashMapList.get("name");
                LatLng latLng = new LatLng(lat, lng);
                MarkerOptions options = new MarkerOptions();
                options.position(latLng);
                options.title(name);
                Map.addMarker(options);
            }
        }
    }
}