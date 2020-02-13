package com.tydeya.familycircle.accountsynchronization;

import com.google.firebase.firestore.QuerySnapshot;

public interface AccountIsExistResultRecipient {

    void isExist(QuerySnapshot queryDocumentSnapshots);

    void isNotExist();

    void isError(Exception e);
}
