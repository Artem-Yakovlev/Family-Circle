package com.tydeya.familycircle.ui.mainlivepage.details;

import com.tydeya.familycircle.ui.mainlivepage.abstraction.LivePagePresenter;
import com.tydeya.familycircle.ui.mainlivepage.abstraction.LivePageView;
import com.tydeya.usecases.mainlivepage.LivePageInteractor;

public class SimpleLivePagePresenter implements LivePagePresenter {

    private LivePageView livePageView;
    private LivePageInteractor livePageInteractor;

    public SimpleLivePagePresenter(LivePageView livePageView, LivePageInteractor livePageInteractor) {
        this.livePageView = livePageView;
        this.livePageInteractor = livePageInteractor;
    }

    @Override
    public void clickOnPersonView(int position) {
        //int id =  livePageInteractor.getDataAboutPerson();
        livePageView.openPersonPage(position);
    }
}
