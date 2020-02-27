package com.tydeya.familycircle.ui.livepart.memberpersonpage.details;

import com.tydeya.familycircle.ui.livepart.memberpersonpage.abstraction.MemberPersonPresenter;
import com.tydeya.familycircle.ui.livepart.memberpersonpage.abstraction.MemberPersonView;

class MemberPersonPresenterImpl implements MemberPersonPresenter {

    private MemberPersonView view;
    private int personPosition;

    MemberPersonPresenterImpl(MemberPersonView view, int personPosition) {
        this.view = view;
        this.personPosition = personPosition;
    }
}
