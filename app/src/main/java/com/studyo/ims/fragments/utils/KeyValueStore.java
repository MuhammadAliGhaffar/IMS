package com.studyo.ims.fragments.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.studyo.ims.fragments.user.model.User;

public class KeyValueStore {
    private static SharedPreferences.Editor editor;
    private static SharedPreferences sharedPref;

    public static void initPref(Activity activity) {
        sharedPref = activity.getPreferences(Context.MODE_PRIVATE);
        editor = sharedPref.edit();
    }

    public static void userDetails(User user) {
        editor.putString("username", user.getUsername());
        editor.putString("email", user.getEmail());
        editor.putString("password", user.getPassword());
        editor.putString("objectId", user.getObjectId());
        editor.apply();
    }

    public static User getUserDetails(){
        User user = new User();
        user.setUsername(sharedPref.getString("username",null));
        user.setEmail(sharedPref.getString("email",null));
        user.setPassword(sharedPref.getString("password",null));
        user.setObjectId(sharedPref.getString("objectId",null));
        return user;
    }

    public static void setUserBalance(String balance) {
        editor.putString("balance", balance);
        editor.apply();
    }

    public static String getUserBalance(){
        return sharedPref.getString("balance",null);
    }
    public static void clearPref(){
        editor.clear();
    }

}
