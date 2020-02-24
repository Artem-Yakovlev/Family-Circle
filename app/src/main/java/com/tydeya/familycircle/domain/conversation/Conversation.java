package com.tydeya.familycircle.domain.conversation;

import com.tydeya.familycircle.domain.conversation.description.details.ConversationAttachments;
import com.tydeya.familycircle.domain.conversation.description.details.ConversationDescription;

public class Conversation {

    private ConversationDescription description;
    private ConversationAttachments attachments;

    public Conversation(ConversationDescription description, ConversationAttachments attachments) {
        this.description = description;
        this.attachments = attachments;
    }

    public ConversationDescription getDescription() {
        return description;
    }

    public ConversationAttachments getAttachments() {
        return attachments;
    }

    String getTitle() {
        return description.getTitle();
    }
}
