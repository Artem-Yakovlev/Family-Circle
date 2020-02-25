package com.tydeya.familycircle.data.userinteractor.injection;

import android.content.SharedPreferences;

import com.tydeya.familycircle.data.userinteractor.details.UserInteractor;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class UserInteractorModule {

    private SharedPreferences sharedPreferences;

    public UserInteractorModule(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }

    @Singleton
    @Provides
    UserInteractor provideUserInteractor() {
        return new UserInteractor(sharedPreferences);
    }
}
