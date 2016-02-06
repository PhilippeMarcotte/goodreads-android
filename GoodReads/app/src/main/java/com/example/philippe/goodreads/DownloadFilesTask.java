package com.example.philippe.goodreads;

import android.app.Activity;
import android.app.Application;
import android.content.ClipData;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.support.v7.widget.Toolbar;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.example.philippe.goodreadsapi.GoodreadsService;
import com.example.philippe.goodreadsapi.Reviews;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.ObjectOutputStream;
import java.net.URL;


/**
 * Created by Philippe_Travail on 2015-12-09.
 */
class DownloadFilesTask extends AsyncTask<Void, Void, Void> {
    final String CONSUMER_KEY = "OjyBECynlWliftzJdwmqA";
    final String CONSUMER_SECRET = "F1YLHobIuTKGNEyTvwxwaYv2QW7252T2KXd6IWOnWE";

    final String ACCESS_TOKEN = "Y2yWPj3IZivAbQ7CAcXi0A";
    final String TOKEN_SECRET = "s5QOq80VQ8oIPtZdPcfephehEuGAGPGvHr7lwBiCTE";
    private Context context;
    private MainActivity mainActivity;

    public DownloadFilesTask(MainActivity activity){
        this.mainActivity = activity;
        this.context = activity.getApplicationContext();
    }

    protected Void doInBackground(Void... params) {
        GoodreadsService.init(CONSUMER_KEY, CONSUMER_SECRET);
        GoodreadsService.setAccessToken(ACCESS_TOKEN, TOKEN_SECRET);
        FileOutputStream outputStream;
        ObjectOutputStream outputObject;
        try {
            outputStream = context.openFileOutput("toReadShelf",Context.MODE_PRIVATE);
            Reviews mimouShelves = GoodreadsService.getAllBooksOnShelf("to-read", GoodreadsService.getAuthorizedUser().getId());
            Log.d("json", mimouShelves.jsonify().toString());
            outputStream.write(mimouShelves.jsonify().toString().getBytes());
            outputStream = context.openFileOutput("toReadMap", Context.MODE_PRIVATE);
            outputObject = new ObjectOutputStream(outputStream);
            outputObject.writeObject(mimouShelves.getBookMap());
            outputObject.close();
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        LayoutInflater inflater = (LayoutInflater) mainActivity.getApplication()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        ImageView iv = (ImageView) inflater.inflate(R.layout.loading_view,
                null);

        Animation rotation = AnimationUtils.loadAnimation(mainActivity.getApplication(),
                R.anim.loading_rotate);
        rotation.setRepeatCount(Animation.INFINITE);
        iv.startAnimation(rotation);
        mainActivity.mLoading.setActionView(iv);
    }

    @Override
    protected void onPostExecute(Void result) {
        super.onPostExecute(result);
        mainActivity.createList();
        mainActivity.mLoading.getActionView().clearAnimation();
        mainActivity.mLoading.collapseActionView();
        mainActivity.mLoading.setActionView(null);
        mainActivity.invalidateOptionsMenu();
    }
}