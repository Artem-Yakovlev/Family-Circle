package com.tydeya.familycircle.synchronization.accountcreate;

public interface SyncAccountCreatedRecipient {
    void accountSuccessfullyCreated();
    void accountCreationFailed(Exception e);
}
