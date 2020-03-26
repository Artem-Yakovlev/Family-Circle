package com.tydeya.familycircle.ui.conversationpart.chatpart;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.tydeya.familycircle.App;
import com.tydeya.familycircle.R;
import com.tydeya.familycircle.data.onlinetracker.OnlineTrackerActivity;
import com.tydeya.familycircle.domain.conversationsassistant.details.ConversationsAssistantImpl;
import com.tydeya.familycircle.domain.familyinteractor.details.FamilyInteractor;

import javax.inject.Inject;

public class MessagingActivity extends AppCompatActivity {

    public static String correspondenceKey;

    @Inject
    FamilyInteractor familyInteractor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messaging);
        App.getComponent().injectActivity(this);

        ConversationsAssistantImpl conversationsAssistant = new ConversationsAssistantImpl();

        correspondenceKey = getIntent().getStringExtra("correspondenceKey");
        Toolbar toolbar = findViewById(R.id.messaging_toolbar);
        toolbar.setTitle(conversationsAssistant.getConversationByKey(correspondenceKey).getDescription().getTitle());
    }

    @Override
    protected void onStart() {
        super.onStart();
        familyInteractor.getFamilyOnlineTracker().userOpenActivity(OnlineTrackerActivity.MESSAGING);
    }

    @Override
    protected void onStop() {
        super.onStop();
        familyInteractor.getFamilyOnlineTracker().userCloseActivity(OnlineTrackerActivity.MESSAGING);
    }
}
