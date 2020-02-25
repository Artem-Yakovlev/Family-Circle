package com.tydeya.familycircle;

import android.app.Application;
import android.content.SharedPreferences;

import com.tydeya.familycircle.data.component.AppComponent;
import com.tydeya.familycircle.data.component.DaggerAppComponent;
import com.tydeya.familycircle.data.userinteractor.injection.UserInteractorModule;
import com.tydeya.familycircle.domain.constants.User;

public class App extends Application {

    private static AppComponent component;

    @Override
    public void onCreate() {
        super.onCreate();

        SharedPreferences sharedPreferences = getSharedPreferences(User.USER_SHARED_PREFERENCES_FILE_NAME, MODE_PRIVATE);
        component = DaggerAppComponent.builder().userInteractorModule(new UserInteractorModule(sharedPreferences))
                .build();
    }

    public static AppComponent getComponent() {
        return component;
    }

}
