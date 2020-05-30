package com.tydeya.familycircle.domain.oldfamilyinteractor.injection;

import com.tydeya.familycircle.domain.oldfamilyinteractor.details.FamilyInteractor;

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
