package com.example.philippe.goodreads;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Window;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import oauth.signpost.OAuthConsumer;
import oauth.signpost.basic.DefaultOAuthConsumer;
import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;

import java.lang.String;


public class MainActivity extends AppCompatActivity {
    final String CONSUMER_KEY = "OjyBECynlWliftzJdwmqA";
    final String CONSUMER_SECRET = "F1YLHobIuTKGNEyTvwxwaYv2QW7252T2KXd6IWOnWE";

    final String ACCESS_TOKEN = "Y2yWPj3IZivAbQ7CAcXi0A";
    final String TOKEN_SECRET = "s5QOq80VQ8oIPtZdPcfephehEuGAGPGvHr7lwBiCTE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar appBar = (Toolbar) findViewById(R.id.appBar);
        setSupportActionBar(appBar);
        // create a consumer object and configure it with the access
        // token and token secret obtained from the service provider
        OAuthConsumer consumer = new DefaultOAuthConsumer(CONSUMER_KEY,
                CONSUMER_SECRET);
        consumer.setTokenWithSecret(ACCESS_TOKEN, TOKEN_SECRET);

        // create an HTTP request to a protected resource
        URL url = null;
        try {
            url = new URL("http://www.goodreads.com/review/list.xml?user_id=30067343&shelf=to-read&v=2");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        HttpURLConnection request = null;
        try {
            request = (HttpURLConnection) url.openConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // sign the request
        try {
            consumer.sign(request);
        } catch (OAuthMessageSignerException e) {
            e.printStackTrace();
        } catch (OAuthExpectationFailedException e) {
            e.printStackTrace();
        } catch (OAuthCommunicationException e) {
            e.printStackTrace();
        }

        // send the request
        try {
            request.connect();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
