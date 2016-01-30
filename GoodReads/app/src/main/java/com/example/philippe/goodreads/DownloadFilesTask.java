package com.example.philippe.goodreads;

import android.media.session.MediaSession;
import android.os.AsyncTask;
import android.util.Log;

import com.google.api.client.auth.oauth2.AuthorizationCodeTokenRequest;
import com.google.api.client.auth.oauth2.TokenResponse;
import com.google.api.client.auth.oauth2.TokenResponseException;
import com.google.api.client.http.BasicAuthentication;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson.JacksonFactory;


import org.scribe.model.Token;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import oauth.signpost.OAuth;
import oauth.signpost.OAuthConsumer;
import oauth.signpost.OAuthProvider;
import oauth.signpost.basic.DefaultOAuthConsumer;
import oauth.signpost.basic.DefaultOAuthProvider;
import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;
import oauth.signpost.exception.OAuthNotAuthorizedException;
import oauth.signpost.signature.SignatureMethod;


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
        //OAuthConsumer consumer = new CommonsHttpOAuthConsumer(CONSUMER_KEY,
        //        CONSUMER_SECRET);
        //consumer.setTokenWithSecret(ACCESS_TOKEN, TOKEN_SECRET);

        // create an HTTP request to a protected resource
        GoodreadsService.init(CONSUMER_KEY, CONSUMER_SECRET);
        GoodreadsService.setAccessToken(ACCESS_TOKEN, TOKEN_SECRET);
        try {
            Reviews mimouShelves = GoodreadsService.getAllBooksOnShelf("to-read", "30067343");
            boolean isInToRead = false;
            int i = 0;
            for (Review review: mimouShelves.getReviews()) {
                isInToRead = false;
                for(String shelfName:review.getShelves()){
                    if(shelfName.equals("to-read"))
                        isInToRead = true;
                }
                if(!isInToRead)
                    Log.d("test","prout");
                Log.d("book",Integer.toString(++i));
            }
            System.out.println();
        } catch (Exception e) {
            e.printStackTrace();
        }
        OAuthConsumer consumer = new DefaultOAuthConsumer("iIlNngv1KdV6XzNYkoLA","exQ94pBpLXFcyttvLoxU2nrktThrlsj580zjYzmoM", SignatureMethod.PLAINTEXT);
        return 0l;
    }

}