package com.example.philippe.goodreads;

import android.os.AsyncTask;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import oauth.signpost.OAuthConsumer;
import oauth.signpost.basic.DefaultOAuthConsumer;
import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;
import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;

/**
 * Created by Philippe_Travail on 2015-12-09.
 */
class DownloadFilesTask extends AsyncTask<URL, Integer, Long> {
    final String CONSUMER_KEY = "OjyBECynlWliftzJdwmqA";
    final String CONSUMER_SECRET = "F1YLHobIuTKGNEyTvwxwaYv2QW7252T2KXd6IWOnWE";

    final String ACCESS_TOKEN = "Y2yWPj3IZivAbQ7CAcXi0A";
    final String TOKEN_SECRET = "s5QOq80VQ8oIPtZdPcfephehEuGAGPGvHr7lwBiCTE";
    protected Long doInBackground(URL... urls) {
        // create a consumer object and configure it with the access
        // token and token secret obtained from the service provider
        OAuthConsumer consumer = new CommonsHttpOAuthConsumer(CONSUMER_KEY,
                CONSUMER_SECRET);
        consumer.setTokenWithSecret(ACCESS_TOKEN, TOKEN_SECRET);

        // create an HTTP request to a protected resource
        URL url = null;
        try {
            url = new URL("https://www.goodreads.com/");//"http://www.goodreads.com/review/list.xml?key=OjyBECynlWliftzJdwmqA&user_id=30067343&shelf=to-read&v=2");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        HttpURLConnection request = null;
        try {
            request = (HttpURLConnection) url.openConnection();//(HttpURLConnection) url.openConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }

        /** sign the request
        try {
            consumer.sign(request);
        } catch (OAuthMessageSignerException e) {
            e.printStackTrace();
        } catch (OAuthExpectationFailedException e) {
            e.printStackTrace();
        } catch (OAuthCommunicationException e) {
            e.printStackTrace();
        }**/


        // send the request
        try {
            request.connect();
            System.out.println(request.getContent().toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0l;
    }

}

