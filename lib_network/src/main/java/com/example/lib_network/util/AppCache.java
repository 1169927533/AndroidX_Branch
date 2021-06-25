package com.example.lib_network.util;

import android.app.Application;


public class AppCache {
    private static Application context;

    public static Application getContext() {
        return context;
    }

    public static void setContext(Application app) {
        if (app == null) {
            AppCache.context = null;
            return;
        }
        AppCache.context = app;
    }
}
