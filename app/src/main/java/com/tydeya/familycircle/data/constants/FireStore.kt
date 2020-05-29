package com.tydeya.familycircle.data.constants

public object FireStore {
    /**
     * Const for firebase user object
     */
    public const val FIRESTORE_USERS_COLLECTION = "Users"
    public const val FIRESTORE_USERS_PHONE_TAG = "phone_number"
    public const val FIRESTORE_USERS_NAME_TAG = "name"
    public const val FIRESTORE_USERS_BIRTHDATE_TAG = "birth_date"
    public const val FIRESTORE_USERS_STUDY_TAG = "study_place"
    public const val FIRESTORE_USERS_WORK_TAG = "work_place"
    public const val FIRESTORE_USERS_IMAGE_ADDRESS = "image"
    public const val FIRESTORE_USERS_LAST_ONLINE = "last_online"
    public const val FIRESTORE_USERS_FAMILIES = "families"
    public const val FIRESTORE_USERS_CURRENT_FAMILY = "current_family"
    public const val FIRESTORE_USERS_FCM_TOKEN = "fcm_token"

    /**
     * Const of firebase conversation object
     */
    public const val FIRESTORE_CONVERSATION_COLLECTION = "Messenger"
    public const val FIRESTORE_CONVERSATION_TITLE = "title"
    public const val FIRESTORE_CONVERSATION_MEMBERS = "members"
    public const val FIRESTORE_CONVERSATION_MESSAGES = "messages"

    /**
     * Const of firebase message object
     */
    public const val FIRESTORE_MESSAGE_COLLECTION = "Messages"
    public const val FIRESTORE_MESSAGE_TEXT = "text"
    public const val FIRESTORE_MESSAGE_AUTHOR_PHONE = "authorPhoneNumber"
    public const val FIRESTORE_MESSAGE_DATETIME = "dateTime"
    public const val FIRESTORE_MESSAGE_UNREAD_PATTERN = "unread_by_"

    /**
     * Const of firebase buy catalog object
     */
    public const val FIRESTORE_KITCHEN_COLLECTION = "KitchenOrganizer"
    public const val FIRESTORE_BUYS_CATALOG_TITLE = "title"
    public const val FIRESTORE_BUYS_CATALOG_DATE = "date_of_create"
    public const val FIRESTORE_BUYS_CATALOG_FOODS = "foods"
    public const val FIRESTORE_BUYS_CATALOG_NUMBER_PRODUCTS = "number_of_products"
    public const val FIRESTORE_BUYS_CATALOG_NUMBER_PURCHASED = "number_of_purchased"

    /**
     * Const of firebase food object
     */
    public const val FIRESTORE_FOOD_TITLE = "title"
    public const val FIRESTORE_FOOD_STATUS = "food_status"
    public const val FIRESTORE_FOOD_QUANTITY_OF_MEASURE = "quantity_of_measure"
    public const val FIRESTORE_FOOD_MEASURE_TYPE = "measure_type"
    public const val FIRESTORE_FOOD_SHELF_LIFE_TIMESTAMP = "shelf_life_timestamp"

    /**
     * Const of firebase fridge
     */
    public const val FIRESTORE_FRIDGE_COLLECTION = "Fridge"

    /**
     * Const of firebase events
     */
    public const val FIRESTORE_EVENTS_COLLECTION = "Events"
    public const val FIRESTORE_EVENTS_AUTHOR = "author"
    public const val FIRESTORE_EVENTS_DATE = "date"
    public const val FIRESTORE_EVENTS_PRIORITY = "priority"
    public const val FIRESTORE_EVENTS_TITLE = "title"
    public const val FIRESTORE_EVENTS_TYPE = "type"
    public const val FIRESTORE_EVENTS_DESCRIPTION = "description"

    /**
     * Const of firebase tasks
     */
    public const val FIRESTORE_TASKS_COLLECTION = "Tasks"
    public const val FIRESTORE_TASKS_AUTHOR = "author"
    public const val FIRESTORE_TASKS_WORKER = "worker"
    public const val FIRESTORE_TASKS_TEXT = "text"
    public const val FIRESTORE_TASKS_STATUS = "status"
    public const val FIRESTORE_TASKS_TIME = "time"

    /**
     * Const of firebase conversation
     */
    public const val FIRESTORE_COOPERATION_COLLECTION = "Cooperation"
    public const val FIRESTORE_COOPERATION_AUTHOR = "author"
    public const val FIRESTORE_COOPERATION_TYPE = "type"
    public const val FIRESTORE_COOPERATION_ITEM = "item"
    public const val FIRESTORE_COOPERATION_TIME = "time"
}