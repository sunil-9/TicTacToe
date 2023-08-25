package com.example.tictactoe.helper;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

public class SharedPref {

    public static final String PREFS_NAME = "TicTacToe";
    public static final String Player1 = "Player1";
    public static final String Player2 = "Player2";
    private static final String TAG = "SharedPref";

    public static void setString(Context context,String key, String value) {
        SharedPreferences settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public static String getString(Context context, String key) {
        SharedPreferences settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        Log.d(TAG, "getPlayer1: "+settings.getString(Player1, ""));

        return settings.getString(key, "");
    }

}
