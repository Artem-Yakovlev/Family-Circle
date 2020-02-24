package com.tydeya.familycircle;

import android.app.Application;

import com.tydeya.familycircle.data.component.AppComponent;
import com.tydeya.familycircle.data.component.DaggerAppComponent;

public class App extends Application {

    private static AppComponent component;

    @Override
    public void onCreate() {
        super.onCreate();
        component = DaggerAppComponent.create();
    }

    public static AppComponent getComponent() {
        return component;
    }

}
