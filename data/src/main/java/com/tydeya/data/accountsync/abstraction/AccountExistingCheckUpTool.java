package com.tydeya.data.accountsync.abstraction;

public interface AccountExistingCheckUpTool {
    void isExist(String phoneNumber, AccountExistingCheckUpRecipient recipient);
}
