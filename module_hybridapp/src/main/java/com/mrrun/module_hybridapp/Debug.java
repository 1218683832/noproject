package com.mrrun.module_hybridapp;

import android.util.Log;

/**
 * @author lipin
 * @version 1.0
 */
public class Debug {

    private static boolean DEGUG = true;

    public static void isDebug(boolean debug) {
        DEGUG = debug;
    }

    public static void D(String msg){
        if (DEGUG) {
            Log.d("module_hybridapp", msg);
        }
    }
}
