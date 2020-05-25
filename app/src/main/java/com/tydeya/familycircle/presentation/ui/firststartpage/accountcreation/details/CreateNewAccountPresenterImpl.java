package com.tydeya.familycircle.presentation.ui.firststartpage.accountcreation.details;

import android.content.ContentResolver;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;

import com.tydeya.familycircle.framework.createnewaccount.abstraction.CreateNewAccountTool;
import com.tydeya.familycircle.framework.createnewaccount.abstraction.CreateNewAccountToolCallback;
import com.tydeya.familycircle.framework.createnewaccount.details.CreateNewAccountToolImpl;
import com.tydeya.familycircle.presentation.ui.firststartpage.accountcreation.abstraction.CreateNewAccountPresenter;
import com.tydeya.familycircle.presentation.ui.firststartpage.accountcreation.abstraction.CreateNewAccountView;

import java.io.IOException;

public class CreateNewAccountPresenterImpl implements CreateNewAccountPresenter, CreateNewAccountToolCallback {

    private CreateNewAccountView view;

    private CreateNewAccountTool createNewAccountTool;

    private String phoneNumber;
    private long birthDate = -1;

    CreateNewAccountPresenterImpl(CreateNewAccountView view, String phoneNumber) {
        this.view = view;
        this.phoneNumber = phoneNumber;
        this.createNewAccountTool = new CreateNewAccountToolImpl(this);
    }

    @Override
    public void birthDateChanged(long birthDate) {
        this.birthDate = birthDate;
    }

    @Override
    public void onClickCreateAccount(String name, Uri imageUri, ContentResolver contentResolver) {
        if (name.length() == 0) {
            view.invalidName();
        } else {
            if (imageUri == null) {
                createNewAccountTool.create(phoneNumber, name, birthDate, null);
            } else {
                Bitmap bitmap = null;
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(contentResolver, imageUri);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                createNewAccountTool.create(phoneNumber, name, birthDate, bitmap);
            }
        }
    }

    @Override
    public void accountCreatedSuccessful() {
        view.accountCreated();
    }

    @Override
    public void accountCreateFailure() {

    }
}
