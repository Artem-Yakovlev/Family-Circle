package com.tydeya.familycircle.presentation.ui.managerpart.menu

import com.tydeya.familycircle.presentation.ui.managerpart.menu.recyclerview.ManagerMenuItemType

interface ManagerMenuView {

    fun signOut()

    fun openPage(itemType: ManagerMenuItemType)

    fun showDialog(itemType: ManagerMenuItemType)
}
