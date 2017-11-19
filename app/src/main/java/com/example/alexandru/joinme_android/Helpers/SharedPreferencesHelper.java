package com.example.alexandru.joinme_android.Helpers;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesHelper {
    private static final String PREFERENCE_FILE = "joinmepreferencefile";
    public static final String USER_EMAIL_KEY = "joinmeemailkey";
    public static final String USER_PASSWORD_KEY = "joinmeuserpasswordkey";
    public static final String IS_USER_LOGGED_IN = "joinmeisuserloggedin";

    public static final String getUserEmail(Context ctx)
    {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(PREFERENCE_FILE, Context.MODE_PRIVATE);
        return sharedPreferences.getString(USER_EMAIL_KEY, "");
    }

    public static final String getUserPassword(Context ctx)
    {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(PREFERENCE_FILE, Context.MODE_PRIVATE);
        return sharedPreferences.getString(USER_PASSWORD_KEY, "");
    }

    public static final boolean isUserLoggedIn(Context ctx)
    {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(PREFERENCE_FILE, Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(IS_USER_LOGGED_IN, false);
    }

    public static final void logIn(String email, String password, Context ctx)
    {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(PREFERENCE_FILE, Context.MODE_PRIVATE);
        SharedPreferences.Editor shEditor = sharedPreferences.edit();
        shEditor.putString(USER_EMAIL_KEY, email);
        shEditor.putString(USER_PASSWORD_KEY, password);
        shEditor.putBoolean(IS_USER_LOGGED_IN, true);
        shEditor.apply();
    }

    public static final void logOut(Context ctx)
    {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(PREFERENCE_FILE, Context.MODE_PRIVATE);
        SharedPreferences.Editor shEditor = sharedPreferences.edit();
        shEditor.putString(USER_EMAIL_KEY, "");
        shEditor.putString(USER_PASSWORD_KEY, "");
        shEditor.putBoolean(IS_USER_LOGGED_IN, false);
        shEditor.apply();
    }
}