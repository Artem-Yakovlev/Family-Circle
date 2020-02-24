package com.tydeya.familycircle.ui.conversationpart.chatpart;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.tydeya.familycircle.R;

public class MessagingActivity extends AppCompatActivity {

    public static int correspondencePosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messaging);
        correspondencePosition = getIntent().getIntExtra("correspondencePosition", -1);

    }
}
