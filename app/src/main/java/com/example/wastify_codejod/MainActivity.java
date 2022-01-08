package com.example.wastify_codejod;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ImageView menuIcon;
    LinearLayout contentView;
    RecyclerView recyclerView;
    FirebaseAuth fAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        View decorView = getWindow().getDecorView();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
            );
        }

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);
        menuIcon = findViewById(R.id.menuIcon);
        contentView = findViewById(R.id.content);

        navigationDrawer();

    }
    private void navigationDrawer() {

        navigationView.bringToFront();
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_home);

        menuIcon.setOnClickListener(view -> {
            if (drawerLayout.isDrawerVisible(GravityCompat.START))
                drawerLayout.closeDrawer(GravityCompat.START);
            else drawerLayout.openDrawer(GravityCompat.START);
        });

        animateNavigationDrawer();
    }

    private void animateNavigationDrawer() {

        drawerLayout.addDrawerListener(new DrawerLayout.SimpleDrawerListener() {
            static final float END_SCALE = 0.7f;
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {


                final float diffScaledOffset = slideOffset * (1 - END_SCALE);
                final float offsetScale = 1 - diffScaledOffset;
                contentView.setScaleX(offsetScale);
                contentView.setScaleY(offsetScale);
                fAuth=FirebaseAuth.getInstance();


                final float xOffset = drawerView.getWidth() * slideOffset;
                final float xOffsetDiff = contentView.getWidth() * diffScaledOffset / 2;
                final float xTranslation = xOffset - xOffsetDiff;
                contentView.setTranslationX(xTranslation);
            }
        });
    }
    public void onBackPressed() {
        if (drawerLayout.isDrawerVisible(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else
            super.onBackPressed();
    }
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){

            case R.id.developer:
                startActivity(new Intent(getApplicationContext(),Developers.class));
                return true;
            case R.id.nav_home:
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                return true;
            case R.id.garbage_van_collection_time:
                startActivity(new Intent(getApplicationContext(),GarbageVanTime.class));
                return true;
            case R.id.garbage_near_me:
                startActivity(new Intent(getApplicationContext(),DustbinNearbyActivity.class));
                return true;
            case R.id.complain:
                startActivity(new Intent(getApplicationContext(),CitizenComplain.class));
                return true;
            case R.id.logout:
                startActivity(new Intent(getApplicationContext(),LoginActivity.class));
                fAuth.signOut();
                return true;
        }
        return false;
    }

    public void read2(View v){
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.circlewaste.co.uk/2020/09/16/what-are-the-5-rs-of-waste-management/"));
        startActivity(browserIntent);
    }

    public void read1(View v){
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://greenrevolutionbins.wordpress.com/2013/08/06/color-coded-dustbins-for-waste-segregation/"));
        startActivity(browserIntent);
    }

    public void read3(View v){
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://safetymanagement.eku.edu/blog/the-seven-wastes-of-lean-manufacturing/"));
        startActivity(browserIntent);
    }

    public void read4(View v){
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.britannica.com/technology/waste-disposal-system"));
        startActivity(browserIntent);
    }

    public void read5(View v){
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.indiatoday.in/information/story/waste-disposal-and-management-all-you-need-to-know-1718288-2020-09-04"));
        startActivity(browserIntent);
    }
}