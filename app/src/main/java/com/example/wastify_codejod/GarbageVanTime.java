package com.example.wastify_codejod;

import androidx.appcompat.app.AppCompatActivity;
import android.widget.SearchView;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class GarbageVanTime extends AppCompatActivity {
    ListView listView;
    String[] name={ "Azad Society\t11:00\tGujarat\tAhmedabad","Ashram Road\t11:05\tGujarat\tAhmedabad","Bapunagar\t11:10\tGujarat\tAhmedabad","Chandranagar\t09:00\tGujarat\tAhmedabad",
            "Ctm Char rasta\t09:30\tGujarat\tAhmedabad","Gita Mandir road\t10:30\tGujarat\tAhmedabad","Gujarat High court\t09:15\tGujarat\tAhmedabad","Manek Chowk\t09:50\tGujarat\tAhmedabad",
            "Maninagar\t09:55\tGujarat\tAhmedabad"};
    ArrayAdapter<String> arrayAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_garbage_van_time);
        listView=findViewById(R.id.listview);
        arrayAdapter=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,name);
        listView.setAdapter(arrayAdapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu,menu);

        MenuItem menuItem=menu.findItem(R.id.action_search);
        SearchView searchView=(SearchView) menuItem.getActionView();
        searchView.setQueryHint("Type area name here to search");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                arrayAdapter.getFilter().filter(newText);

                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }
}