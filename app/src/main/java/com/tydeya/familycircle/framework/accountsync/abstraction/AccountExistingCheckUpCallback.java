package com.tydeya.familycircle.framework.accountsync.abstraction;

import com.google.firebase.firestore.QuerySnapshot;

public interface AccountExistingCheckUpCallback {

    void accountIsNotExist();

    void accountIsExist(QuerySnapshot querySnapshot);
}
