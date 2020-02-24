package com.tydeya.familycircle.data.familyinteractor.injection;

import com.tydeya.familycircle.data.familyinteractor.details.FamilyInteractor;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class FamilyInteractorModule {

    @Singleton
    @Provides
    FamilyInteractor provideFamilyInteractor() {
        return new FamilyInteractor();
    }

}
