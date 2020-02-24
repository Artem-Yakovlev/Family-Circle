package com.tydeya.familycircle.data.component;

import com.tydeya.familycircle.data.familyInteractor.injection.FamilyInteractorModule;
import com.tydeya.familycircle.ui.livepart.details.MainLivePage;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {FamilyInteractorModule.class})
public interface AppComponent {

    void injectFragment(MainLivePage mainLivePage);
}
