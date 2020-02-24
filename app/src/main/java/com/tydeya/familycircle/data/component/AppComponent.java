package com.tydeya.familycircle.data.component;

import com.tydeya.familycircle.data.familyInteractor.injection.FamilyInteractorModule;
import com.tydeya.familycircle.ui.livepart.main.details.MainLivePage;
import com.tydeya.familycircle.ui.livepart.memberpersonpage.details.FamilyMemberPersonPage;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {FamilyInteractorModule.class})
public interface AppComponent {

    void injectFragment(MainLivePage mainLivePage);

    void injectFragment(FamilyMemberPersonPage familyMemberPersonPage);
}
