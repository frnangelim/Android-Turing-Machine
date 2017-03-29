package com.example.huawei.maquinadeturing.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.huawei.maquinadeturing.models.Profile;
import com.google.gson.Gson;

public class SessionManager {

    private static final String USER_SESSION_PREF = "user_session";
    private static final String USER_SESSION_KEY = "user";
    private static final String USER_NOT_LOGGED = "";

    private static Context context;
    private static SessionManager instance;
    private final Gson gson;
    boolean firstLogin;

    public boolean isFirstLogin(){
        return firstLogin;
    }

    public void setFirstLogin(boolean bool){
        this.firstLogin = bool;
    }

    private SessionManager() {
        gson = new Gson();
    }

    public static SessionManager getInstance(Context ctx) {
        context = ctx;

        if(instance == null) {
            instance = new SessionManager();
        }

        return instance;
    }

    public Profile getUserSession() {
        SharedPreferences sharedPref = context.getSharedPreferences(
                USER_SESSION_PREF, Context.MODE_PRIVATE);

        String userString = sharedPref.getString(USER_SESSION_KEY, USER_NOT_LOGGED);

        return gson.fromJson(userString, Profile.class);
    }

    public void setUser(Profile user){
        SharedPreferences sharedPref = context.getSharedPreferences(
                USER_SESSION_PREF, Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPref.edit();

        editor.putString(USER_SESSION_KEY, gson.toJson(user).toString());
        editor.commit();
    }

    public boolean isUserLogged() {
        SharedPreferences sharedPref = context.getSharedPreferences(
                USER_SESSION_PREF, Context.MODE_PRIVATE);

        String userString = sharedPref.getString(USER_SESSION_KEY, USER_NOT_LOGGED);

        return !userString.equals(USER_NOT_LOGGED);
    }

    public void startUserSession(Profile user) {
        SharedPreferences sharedPref = context.getSharedPreferences(
                USER_SESSION_PREF, Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPref.edit();

        editor.putString(USER_SESSION_KEY, gson.toJson(user).toString());
        editor.commit();
    }

    public void stopUserSession() {
        SharedPreferences sharedPref = context.getSharedPreferences(USER_SESSION_PREF, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(USER_SESSION_KEY, USER_NOT_LOGGED);
        editor.commit();
    }

}

