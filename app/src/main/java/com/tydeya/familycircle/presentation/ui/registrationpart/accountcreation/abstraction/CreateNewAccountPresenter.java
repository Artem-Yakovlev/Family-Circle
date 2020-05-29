package com.tydeya.familycircle.presentation.ui.registrationpart.accountcreation.abstraction;

import android.content.ContentResolver;
import android.net.Uri;

public interface CreateNewAccountPresenter {

    void birthDateChanged(long birthDate);

    void onClickCreateAccount(String name, Uri imageUri, ContentResolver contentResolver);
}
