package com.example.alexandru.joinme_android.Helpers;

import android.net.Uri;

import com.example.alexandru.joinme_android.domain.Event;
import com.example.alexandru.joinme_android.domain.User;

public class NetworkUtils {
    private static final String PROTOCOL = "http";
    private static final String BASE_URL = "192.168.137.103:8080";

    private static final String LOG_IN_PATH = "rest/login";
    private static final String GET_EVENTS_BY_INTERESTS_PATH = "rest/getEventsByInterests";

    private static final String CREATE_EVENT_PATH = "/rest/createEvent";

    private static final String EMAIL_KEY = "email";
    public static final String PASSWORD_KEY = "password";
    public static final String EVENT_NAME_KEY = "eventname";
    public static final String EVENT_DESCRIPTION_KEY = "eventdescription";
    public static final String EVENT_CATEGORY_KEY = "eventcategory";
    public static final String EVENT_DATE_KEY = "eventdate";
    public static final String EVENT_TIME_KEY = "eventtime";
    public static final String EVENT_LOCATION_KEY = "eventlocation";
    public static final String EVENT_OPEN_KEY = "eventopen";





    public static String buildLogInUrl(String email, String password) {
        Uri.Builder builder = new Uri.Builder();
        builder.scheme(PROTOCOL)
                .encodedAuthority(BASE_URL)
                .appendEncodedPath(LOG_IN_PATH)
                .appendQueryParameter(EMAIL_KEY, email)
                .appendQueryParameter(PASSWORD_KEY, password);
        return builder.build().toString();
    }

    public static String buildCreateEventUrl(String email, String password, Event event) {
        Uri.Builder builder = new Uri.Builder();
        builder.scheme(PROTOCOL)
                .encodedAuthority(BASE_URL)
                .appendEncodedPath(CREATE_EVENT_PATH)
                .appendQueryParameter(EMAIL_KEY, email)
                .appendQueryParameter(PASSWORD_KEY, password)
                .appendQueryParameter(EVENT_NAME_KEY, event.getName())
                .appendQueryParameter(EVENT_DESCRIPTION_KEY, event.getDescription())
                .appendQueryParameter(EVENT_CATEGORY_KEY, event.getCategory())
                .appendQueryParameter(EVENT_DATE_KEY,event.getDate())
                .appendQueryParameter(EVENT_TIME_KEY,event.getTime())
                .appendQueryParameter(EVENT_LOCATION_KEY,event.getLocation())
                .appendQueryParameter(EVENT_OPEN_KEY, event.getOpen().toString());
        return builder.build().toString();

    }

    public static String buildGetEventsByInterestsUrl(String email, String password) {
        Uri.Builder builder = new Uri.Builder();
        builder.scheme(PROTOCOL)
                .encodedAuthority(BASE_URL)
                .appendEncodedPath(GET_EVENTS_BY_INTERESTS_PATH)
                .appendQueryParameter(EMAIL_KEY, email)
                .appendQueryParameter(PASSWORD_KEY, password);
        return builder.build().toString();
    }
}
