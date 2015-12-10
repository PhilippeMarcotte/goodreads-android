package com.example.philippe.goodreads;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import oauth.signpost.OAuth;
import oauth.signpost.OAuthConsumer;
import oauth.signpost.OAuthProvider;
import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;
import oauth.signpost.commonshttp.CommonsHttpOAuthProvider;
import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;
import oauth.signpost.exception.OAuthNotAuthorizedException;

/**
 * Created by Philippe on 2015-12-09.
 */
public class OAuthHelper {
    private OAuthConsumer mConsumer;
    private OAuthProvider mProvider;
    private String mCallbackUrl;
    public OAuthHelper(String consumerKey, String consumerSecret,
                       String scope, String callbackUrl)
            throws UnsupportedEncodingException {
        mConsumer = new CommonsHttpOAuthConsumer(consumerKey, consumerSecret);
        mProvider = new CommonsHttpOAuthProvider(
                "https://www.google.com/accounts/OAuthGetRequestToken?scope="
                        + URLEncoder.encode(scope, "utf-8"),
                "https://www.google.com/accounts/OAuthGetAccessToken",
                "https://www.google.com/accounts/OAuthAuthorizeToken?hd=default");
        mProvider.setOAuth10a(true);
        mCallbackUrl = (callbackUrl == null ? OAuth.OUT_OF_BAND : callbackUrl);
    }

    public String getRequestToken()
            throws OAuthMessageSignerException, OAuthNotAuthorizedException,
            OAuthExpectationFailedException, OAuthCommunicationException {
        String authUrl = mProvider.retrieveRequestToken(mConsumer,
                mCallbackUrl);
        return authUrl;
    }
}
