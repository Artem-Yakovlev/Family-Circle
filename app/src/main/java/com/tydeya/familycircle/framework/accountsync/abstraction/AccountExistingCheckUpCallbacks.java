package com.tydeya.familycircle.framework.accountsync.abstraction;

import com.google.firebase.firestore.QuerySnapshot;

public interface AccountExistingCheckUpCallbacks {

    void accountIsNotExist();

    void accountIsExist(QuerySnapshot querySnapshot);
}
