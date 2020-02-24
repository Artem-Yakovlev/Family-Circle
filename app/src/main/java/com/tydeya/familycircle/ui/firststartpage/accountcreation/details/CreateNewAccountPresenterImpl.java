package com.tydeya.familycircle.ui.firststartpage.accountcreation.details;

import com.tydeya.familycircle.framework.createnewaccount.abstraction.CreateNewAccountTool;
import com.tydeya.familycircle.framework.createnewaccount.abstraction.CreateNewAccountToolCallback;
import com.tydeya.familycircle.framework.createnewaccount.details.CreateNewAccountToolImpl;
import com.tydeya.familycircle.ui.firststartpage.accountcreation.abstraction.CreateNewAccountPresenter;
import com.tydeya.familycircle.ui.firststartpage.accountcreation.abstraction.CreateNewAccountView;

public class CreateNewAccountPresenterImpl implements CreateNewAccountPresenter, CreateNewAccountToolCallback {

    private CreateNewAccountView view;

    private CreateNewAccountTool createNewAccountTool;

    private String phoneNumber;
    private String birthDate = "";

    CreateNewAccountPresenterImpl(CreateNewAccountView view, String phoneNumber) {
        this.view = view;
        this.phoneNumber = phoneNumber;
        this.createNewAccountTool = new CreateNewAccountToolImpl(this);
    }

    @Override
    public void birthDateChanged(String birthDate) {
        this.birthDate = birthDate;
    }

    @Override
    public void onClickCreateAccount(String name) {
        if (name.length() == 0) {
            view.invalidName();
        } else {
            createNewAccountTool.create(phoneNumber, name, birthDate);
        }
    }

    @Override
    public void accountCreatedSuccessful() {
        view.accountCreated();
    }

    @Override
    public void accountCreateFailure() {

    }
}
