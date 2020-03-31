package com.tydeya.familycircle.framework.createnewaccount.abstraction;

import android.graphics.Bitmap;

public interface CreateNewAccountTool {

    void create(String fullPhoneNumber, String name, long birthDate, Bitmap imageBitmap);

}
