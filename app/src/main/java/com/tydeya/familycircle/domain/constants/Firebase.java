package com.tydeya.familycircle.domain.constants;

public final class Firebase {

    private Firebase(){}

    /**
     * Const for firebase user object
     */
    public static final String FIRESTORE_USERS_COLLECTION = "Users";
    public static final String FIRESTORE_USERS_PHONE_TAG = "phone_number";
    public static final String FIRESTORE_USERS_NAME_TAG = "name";
    public static final String FIRESTORE_USERS_BIRTHDATE_TAG = "birth_date";


    /**
     * Const of firebase conversation object
     */

    public static final String FIRESTORE_CONVERSATION_COLLECTION = "Conversations";
    public static final String FIRESTORE_CONVERSATION_ID = "id";
    public static final String FIRESTORE_CONVERSATION_NAME = "name";

    /**
     * Const of firebase message object
     */
    public static final String FIRESTORE_MESSAGE_COLLECTION = "Messages";
    public static final String FIRESTORE_MESSAGE_TEXT = "text";
    public static final String FIRESTORE_MESSAGE_AUTHOR_PHONE = "authorPhoneNumber";
    public static final String FIRESTORE_MESSAGE_DATETIME = "dateTime";
    public static final String FIRESTORE_MESSAGE_UNREAD_PATTERN = "unread_by_";
}
