package com.tydeya.familycircle.presentation.ui.managerpart.menu.abstraction;

import com.tydeya.familycircle.presentation.ui.managerpart.menu.details.recyclerview.ManagerMenuItemType;

public interface ManagerMenuView {

    void signOut();

    void openPage(ManagerMenuItemType managerMenuItemType);
}
