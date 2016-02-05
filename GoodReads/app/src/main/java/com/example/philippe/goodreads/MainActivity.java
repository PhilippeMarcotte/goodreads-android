package com.example.philippe.goodreads;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.ListView;


import com.example.philippe.ListViewManager.BookListViewAdapter;
import com.example.philippe.ListViewManager.ExpandAnimation;
import com.example.philippe.goodreadsapi.Book;
import com.example.philippe.goodreadsapi.Review;

import org.json.JSONArray;

import java.io.File;
import java.lang.String;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;


public class MainActivity extends AppCompatActivity {
    private BookListViewAdapter adapter;
    private ListView list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //requestWindowFeature(Window.FEATURE_NO_TITLE);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar appBar = (Toolbar) findViewById(R.id.appBar);
        setSupportActionBar(appBar);
        list = (ListView) findViewById(R.id.listview);
        File shelfFile = new File(getFilesDir(),"toReadShelf");
        if(shelfFile.exists()){
            createList();
        }


    }

    private void createList(){
        ShelfLoader shelfLoader = new ShelfLoader("toRead",getApplicationContext());
        ArrayList<Review> reviewList = new ArrayList<>(shelfLoader.load());
        adapter = new BookListViewAdapter(getApplicationContext(), R.layout.book_item, reviewList);
        list.setAdapter(adapter);
        // Creating an item click listener, to open/close our toolbar for each item
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {

                View toolbar = view.findViewById(R.id.descriptionBar);

                // Creating the expand animation for the item
                ExpandAnimation expandAni = new ExpandAnimation(toolbar, 500);

                // Start the animation on the toolbar
                toolbar.startAnimation(expandAni);
            }
        });
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
                DownloadFilesTask task = new DownloadFilesTask(getApplicationContext());
                task.execute();
                try {
                    task.get();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
                ListView list = (ListView) findViewById(R.id.listview);
//                adapter.clear();
                createList();
            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

}
