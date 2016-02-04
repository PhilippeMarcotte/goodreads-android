package com.example.philippe.goodreads;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;


import com.example.philippe.ListViewManager.BookListViewAdapter;

import java.lang.String;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //requestWindowFeature(Window.FEATURE_NO_TITLE);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar appBar = (Toolbar) findViewById(R.id.appBar);
        setSupportActionBar(appBar);

        final ListView listview = (ListView) findViewById(R.id.listview);

        ArrayList<String> arrayList = new ArrayList<String>();
        arrayList.add("test1");
        arrayList.add("test2");
        ListView list = (ListView) findViewById(R.id.listview);
        list.setAdapter(new BookListViewAdapter(getApplicationContext(), R.layout.book_item, arrayList));
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.app_bar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                // User chose the "Settings" item, show the app settings UI...
                return true;

            case R.id.action_download_shelves:
                new DownloadFilesTask(getApplicationContext()).execute();

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

}
