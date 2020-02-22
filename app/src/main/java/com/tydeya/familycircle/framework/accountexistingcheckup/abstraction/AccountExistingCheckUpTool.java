package com.tydeya.familycircle.framework.accountexistingcheckup.abstraction;

public interface AccountExistingCheckUpTool {
    void isExist(String phoneNumber, AccountExistingCheckUpRecipient recipient);
}
