package com.posts.cont;

import android.Manifest;
import android.app.Activity;

import androidx.core.app.ActivityCompat;

public class PERMISION
{

    public static final int RE=1;
    public static final int WR=2;

public static void askPermision(Activity act){
    ActivityCompat.requestPermissions(act,new String[]{Manifest.permission.READ_CONTACTS}, 1);
}


    public static void askPermision2(Activity act) {
        ActivityCompat.requestPermissions(act,new String[]{Manifest.permission.WRITE_CONTACTS}, 2);
    }
}
