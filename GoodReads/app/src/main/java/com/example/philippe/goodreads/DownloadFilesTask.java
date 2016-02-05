package com.example.philippe.goodreads;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.example.philippe.goodreadsapi.GoodreadsService;
import com.example.philippe.goodreadsapi.Reviews;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.net.URL;


/**
 * Created by Philippe_Travail on 2015-12-09.
 */
class DownloadFilesTask extends AsyncTask<URL, Integer, Long> {
    final String CONSUMER_KEY = "OjyBECynlWliftzJdwmqA";
    final String CONSUMER_SECRET = "F1YLHobIuTKGNEyTvwxwaYv2QW7252T2KXd6IWOnWE";

    final String ACCESS_TOKEN = "Y2yWPj3IZivAbQ7CAcXi0A";
    final String TOKEN_SECRET = "s5QOq80VQ8oIPtZdPcfephehEuGAGPGvHr7lwBiCTE";
    private Context context;

    public DownloadFilesTask(Context context){
        this.context = context;
    }

    protected Long doInBackground(URL... urls) {
        GoodreadsService.init(CONSUMER_KEY, CONSUMER_SECRET);
        GoodreadsService.setAccessToken(ACCESS_TOKEN, TOKEN_SECRET);
        FileOutputStream outputStream;

        try {
            outputStream = context.openFileOutput("toReadShelf",Context.MODE_PRIVATE);
            Reviews mimouShelves = GoodreadsService.getAllBooksOnShelf("to-read", GoodreadsService.getAuthorizedUser().getId());
            Log.d("json", mimouShelves.jsonify().toString());
            outputStream.write(mimouShelves.jsonify().toString().getBytes());
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0l;
    }

}