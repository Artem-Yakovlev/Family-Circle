package com.tydeya.data.accountsync.abstraction;

public interface AccountSync {

    void isAccountExist(String fullPhoneNumber);

    void accountCreate();
}
