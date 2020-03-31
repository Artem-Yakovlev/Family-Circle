package com.tydeya.familycircle.ui.managerpart.menu.abstraction;

import com.tydeya.familycircle.ui.managerpart.menu.details.recyclerview.ManagerMenuItemType;

public interface ManagerMenuView {

    void signOut();

    void openPage(ManagerMenuItemType managerMenuItemType);
}
