package com.tydeya.familycircle;

import android.app.Application;
import android.content.SharedPreferences;

import androidx.room.Room;

import com.tydeya.familycircle.domain.component.AppComponent;
import com.tydeya.familycircle.domain.component.DaggerAppComponent;
import com.tydeya.familycircle.domain.database.AppDatabase;
import com.tydeya.familycircle.domain.userinteractor.injection.UserInteractorModule;
import com.tydeya.familycircle.data.constants.User;

public class App extends Application {

    private static AppComponent component;
    private static AppDatabase database;

    @Override
    public void onCreate() {
        super.onCreate();
        componentInit();
        databaseInit();
    }

    private void componentInit() {
        SharedPreferences sharedPreferences = getSharedPreferences(User.USER_SHARED_PREFERENCES_FILE_NAME, MODE_PRIVATE);
        component = DaggerAppComponent.builder().userInteractorModule(new UserInteractorModule(sharedPreferences))
                .build();
    }

    private void databaseInit() {
        //TODO remove quire from ui thread
        database = Room.databaseBuilder(this, AppDatabase.class, "database").allowMainThreadQueries()
                .build();
    }

    public static AppComponent getComponent() {
        return component;
    }

    public static AppDatabase getDatabase() {
        return database;
    }
}
