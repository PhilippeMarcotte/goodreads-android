package com.example.philippe.goodreads;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;


import com.example.philippe.ListViewManager.BookListViewAdapter;
import com.example.philippe.ListViewManager.ExpandAnimation;
import com.example.philippe.goodreadsapi.GoodreadsService;
import com.example.philippe.goodreadsapi.Review;
import com.example.philippe.goodreadsapi.Reviews;
import com.example.philippe.goodreadsapi.ShelfLoader;

import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;


public class MainActivity extends AppCompatActivity {
    private BookListViewAdapter adapter;
    private ListView list;
    private Reviews mimouReviews;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //requestWindowFeature(Window.FEATURE_NO_TITLE);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar appBar = (Toolbar) findViewById(R.id.appBar);
        setSupportActionBar(appBar);
        list = (ListView) findViewById(R.id.listview);
        File shelfFile = new File(getFilesDir(),"toReadShelf");
        //if(shelfFile.exists()){
        //    if(GoodreadsService.verifyIntegrity(shelfFile))
        //        createList();
        //}


    }

    public void createList(){
        mimouReviews = new Reviews();
        mimouReviews.loadShelf("toRead",getApplicationContext());

        adapter = new BookListViewAdapter(getApplicationContext(), R.layout.book_item, mimouReviews.getReviews());
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
                LayoutInflater inflater = (LayoutInflater) getApplication()
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                ImageView iv = (ImageView) inflater.inflate(R.layout.loading_view,
                        null);

                Animation rotation = AnimationUtils.loadAnimation(getApplication(),
                        R.anim.loading_rotate);
                rotation.setRepeatCount(Animation.INFINITE);
                iv.startAnimation(rotation);

                item.setActionView(iv);

                DownloadFilesTask task = new DownloadFilesTask(getApplicationContext(), this);

                task.execute();
                /*try {
                    task.get();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }*/
//                adapter.clear();
               // createList();
                item.collapseActionView();
                item.setActionView(null);
                invalidateOptionsMenu();
            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

}
