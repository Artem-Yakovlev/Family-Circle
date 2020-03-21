package com.tydeya.familycircle.domain.familyinteractor.injection;

import com.tydeya.familycircle.domain.familyinteractor.details.FamilyInteractor;

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
