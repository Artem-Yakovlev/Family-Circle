package com.tydeya.familycircle.ui.firststartpage.accountcreation.abstraction;

public interface CreateNewAccountPresenter {

    void birthDateChanged(long birthDate);

    void onClickCreateAccount(String name);
}
