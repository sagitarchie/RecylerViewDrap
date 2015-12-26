package org.archie.app.recyclerviewdragdrop;

import android.app.Application;
import android.content.Context;

/**
 *
 * Created by archie on 15/12/26.
 */
public class MyApp extends Application {
    private static Context instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    public static Context getInstance() {
        return instance;
    }
}
