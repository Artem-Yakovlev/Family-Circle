package com.tydeya.familycircle.ui.conversationpart.chatpart;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.tydeya.familycircle.App;
import com.tydeya.familycircle.R;
import com.tydeya.familycircle.data.conversationsinteractor.details.ConversationInteractor;

import javax.inject.Inject;

public class MessagingActivity extends AppCompatActivity {

    public static int correspondencePosition;
    private Toolbar toolbar;

    @Inject
    ConversationInteractor conversationInteractor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messaging);
        App.getComponent().injectActivity(this);

        correspondencePosition = getIntent().getIntExtra("correspondencePosition", -1);
        toolbar = findViewById(R.id.messaging_toolbar);
        toolbar.setTitle(conversationInteractor.getConversations().get(correspondencePosition).getDescription().getTitle());
    }
}
