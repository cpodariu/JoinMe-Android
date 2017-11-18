package com.example.alexandru.joinme_android.Helpers;

import android.net.Uri;

/**
 * Created by cpodariu on 11/18/17.
 */

//myUrl="http://192.168.43.253:8080/rest/getEventsByInterests?email="+email+"&password="+passwd;

public class NetworkUtils {
    private static final String PROTOCOL = "http";
    private static final String BASE_URL = "192.168.43.253:8080";

    private static final String LOG_IN_PATH = "rest/login";
    private static final String GET_EVENTS_BY_INTERESTS_PATH ="rest/getEventsByInterests";

    private static final String EMAIL_KEY = "email";
    public static final String PASSWORD_KEY = "password";

    public static String buildLogInUrl(String email, String password)
    {
        Uri.Builder builder = new Uri.Builder();
        builder.scheme(PROTOCOL)
                .encodedAuthority(BASE_URL)
                .appendEncodedPath(LOG_IN_PATH)
                .appendQueryParameter(EMAIL_KEY, email)
                .appendQueryParameter(PASSWORD_KEY, password);
        return builder.build().toString();
    }

    public static String buildGetEventsByInterestsUrl(String email, String password)
    {
        Uri.Builder builder = new Uri.Builder();
        builder.scheme(PROTOCOL)
                .encodedAuthority(BASE_URL)
                .appendEncodedPath(GET_EVENTS_BY_INTERESTS_PATH)
                .appendQueryParameter(EMAIL_KEY, email)
                .appendQueryParameter(PASSWORD_KEY, password);
        return builder.build().toString();
    }
}
