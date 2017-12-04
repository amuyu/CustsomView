package com.amuyu.customview;


import android.app.Application;

import com.amuyu.logger.DefaultLogPrinter;
import com.amuyu.logger.Logger;

public class CustomViewApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        Logger.addLogPrinter(new DefaultLogPrinter(this));
    }
}
