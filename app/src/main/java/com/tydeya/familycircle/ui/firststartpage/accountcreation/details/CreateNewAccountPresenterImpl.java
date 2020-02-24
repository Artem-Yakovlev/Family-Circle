package com.tydeya.familycircle.ui.firststartpage.accountcreation.details;

import com.tydeya.familycircle.ui.firststartpage.accountcreation.abstraction.CreateNewAccountPresenter;
import com.tydeya.familycircle.ui.firststartpage.accountcreation.abstraction.CreateNewAccountView;

public class CreateNewAccountPresenterImpl implements CreateNewAccountPresenter {

    private CreateNewAccountView view;

    CreateNewAccountPresenterImpl(CreateNewAccountView view) {
        this.view = view;
    }

    @Override
    public void onClickCreateAccount(String name) {
        if (name.length() == 0) {
            view.invalidName();
        }

    }
}
