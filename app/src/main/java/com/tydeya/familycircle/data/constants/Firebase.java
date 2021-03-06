package com.tydeya.familycircle.data.constants;

public final class Firebase {

    private Firebase() {
    }

    /**
     * Const for firebase user object
     */
    public static final String FIRESTORE_USERS_COLLECTION = "Users";
    public static final String FIRESTORE_USERS_PHONE_TAG = "phone_number";
    public static final String FIRESTORE_USERS_NAME_TAG = "name";
    public static final String FIRESTORE_USERS_BIRTHDATE_TAG = "birth_date";
    public static final String FIRESTORE_USERS_STUDY_TAG = "study_place";
    public static final String FIRESTORE_USERS_WORK_TAG = "work_place";
    public static final String FIRESTORE_USERS_IMAGE_ADDRESS = "image";
    public static final String FIRESTORE_USERS_LAST_ONLINE = "last_online";

    /**
     * Const of firebase conversation object
     */
    public static final String FIRESTORE_CONVERSATION_COLLECTION = "Messenger";
    public static final String FIRESTORE_CONVERSATION_TITLE = "title";
    public static final String FIRESTORE_CONVERSATION_MEMBERS = "members";
    public static final String FIRESTORE_CONVERSATION_MESSAGES = "messages";

    /**
     * Const of firebase message object
     */
    public static final String FIRESTORE_MESSAGE_COLLECTION = "Messages";
    public static final String FIRESTORE_MESSAGE_TEXT = "text";
    public static final String FIRESTORE_MESSAGE_AUTHOR_PHONE = "authorPhoneNumber";
    public static final String FIRESTORE_MESSAGE_DATETIME = "dateTime";
    public static final String FIRESTORE_MESSAGE_UNREAD_PATTERN = "unread_by_";

    /**
     * Const of firebase buy catalog object
     */

    public static final String FIRESTORE_KITCHEN_COLLECTION = "KitchenOrganizer";
    public static final String FIRESTORE_BUY_CATALOG_TITLE = "title";
    public static final String FIRESTORE_BUY_CATALOG_DATE = "date_of_create";
    public static final String FIRESTORE_BUY_CATALOG_FOODS = "foods";

    /**
     * Const of firebase food object
     */
    public static final String FIRESTORE_FOOD_TITLE = "title";
    public static final String FIRESTORE_FOOD_DESCRIPTION = "description";
    public static final String FIRESTORE_FOOD_STATUS = "food_status";
    public static final String FIRESTORE_FOOD_CALORIES = "calories";
    public static final String FIRESTORE_FOOD_PROTEIN = "protein";
    public static final String FIRESTORE_FOOD_FATS = "fats";

    /**
     * Const of firebase fridge
     */

    public static final String FIRESTORE_FRIDGE_COLLECTION = "Fridge";

    /**
     * Const of firebase events
     * */

    public static final String FIRESTORE_EVENTS_COLLECTION = "Events";
    public static final String FIRESTORE_EVENTS_AUTHOR = "author";
    public static final String FIRESTORE_EVENTS_DATE = "date";
    public static final String FIRESTORE_EVENTS_PRIORITY = "priority";
    public static final String FIRESTORE_EVENTS_TITLE = "title";
    public static final String FIRESTORE_EVENTS_TYPE = "type";
    public static final String FIRESTORE_EVENTS_DESCRIPTION = "description";

    /**
     * Const of firebase tasks
     * */

    public static final String FIRESTORE_TASKS_COLLECTION = "Tasks";
    public static final String FIRESTORE_TASKS_AUTHOR = "author";
    public static final String FIRESTORE_TASKS_WORKER = "worker";
    public static final String FIRESTORE_TASKS_TEXT = "text";
    public static final String FIRESTORE_TASKS_STATUS = "status";
    public static final String FIRESTORE_TASKS_TIME = "time";

    /**
     * Const of firebase conversation
     * */

    public static final String FIRESTORE_COOPERATION_COLLECTION = "Cooperation";
    public static final String FIRESTORE_COOPERATION_AUTHOR = "author";
    public static final String FIRESTORE_COOPERATION_TYPE = "type";
    public static final String FIRESTORE_COOPERATION_ITEM = "item";
    public static final String FIRESTORE_COOPERATION_TIME = "time";
}
