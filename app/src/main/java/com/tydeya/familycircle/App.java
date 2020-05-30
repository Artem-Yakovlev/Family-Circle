package com.tydeya.familycircle;

import android.app.Application;

import com.tydeya.familycircle.domain.component.AppComponent;
import com.tydeya.familycircle.domain.component.DaggerAppComponent;

public class App extends Application {

    private static AppComponent component;

    @Override
    public void onCreate() {
        super.onCreate();
        componentInit();
    }

    private void componentInit() {
        component = DaggerAppComponent.builder().build();
    }

    public static AppComponent getComponent() {
        return component;
    }

}
