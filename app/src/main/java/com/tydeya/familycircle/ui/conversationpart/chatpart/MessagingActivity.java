package com.tydeya.familycircle.ui.conversationpart.chatpart;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.tydeya.familycircle.R;
import com.tydeya.familycircle.domain.conversationsassistant.details.ConversationsAssistantImpl;

public class MessagingActivity extends AppCompatActivity {

    public static String correspondenceKey;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messaging);

        ConversationsAssistantImpl conversationsAssistant = new ConversationsAssistantImpl();

        correspondenceKey = getIntent().getStringExtra("correspondenceKey");
        toolbar = findViewById(R.id.messaging_toolbar);
        toolbar.setTitle(conversationsAssistant.getConversationByKey(correspondenceKey).getDescription().getTitle());
    }
}
