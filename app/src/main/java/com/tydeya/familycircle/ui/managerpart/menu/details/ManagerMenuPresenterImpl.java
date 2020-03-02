package com.tydeya.familycircle.ui.managerpart.menu.details;

import com.tydeya.familycircle.ui.managerpart.menu.abstraction.ManagerMenuPresenter;
import com.tydeya.familycircle.ui.managerpart.menu.abstraction.ManagerMenuView;

class ManagerMenuPresenterImpl implements ManagerMenuPresenter {

    private ManagerMenuView view;

    ManagerMenuPresenterImpl(ManagerMenuView view) {
        this.view = view;
    }
}
