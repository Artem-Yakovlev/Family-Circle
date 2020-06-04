package com.tydeya.familycircle.data.constants

object FireStore {

    /**
     * Const for firestore family
     * */

    const val FIRESTORE_FAMILY_COLLECTION = "Families"
    const val FIRESTORE_FAMILY_TITLE_TAG = "title"
    const val FIRESTORE_FAMILY_NUMBER_OF_MEMBERS_TAG = "number_of_members"
    const val FIRESTORE_FAMILY_AUTHOR_PHONE_TAG = "author_phone"

    /**
     * Const for firestore user object
     */
    const val FIRESTORE_USERS_COLLECTION = "Users"
    const val FIRESTORE_USERS_PHONE_TAG = "phone_number"
    const val FIRESTORE_USERS_NAME_TAG = "name"
    const val FIRESTORE_USERS_BIRTH_TAG = "birth_date"
    const val FIRESTORE_USERS_STUDY_TAG = "study_place"
    const val FIRESTORE_USERS_WORK_TAG = "work_place"
    const val FIRESTORE_USERS_IMAGE_PATH = "image"
    const val FIRESTORE_USERS_LAST_ONLINE = "last_online"
    const val FIRESTORE_USERS_FAMILY_TITLES = "family_titles"
    const val FIRESTORE_USERS_FAMILY_ICONS = "family_icons"
    const val FIRESTORE_USERS_FAMILY_SIZES = "family_sizes"
    const val FIRESTORE_USERS_FAMILY_INVITES = "family_invites"
    const val FIRESTORE_USERS_FAMILY_IDS = "family_ids"
    const val FIRESTORE_USERS_FCM_TOKEN = "fcm_token"

    /**
     * Const of firestore conversation object
     */
    const val FIRESTORE_CONVERSATION_COLLECTION = "Messenger"
    const val FIRESTORE_CONVERSATION_TITLE = "title"
    const val FIRESTORE_CONVERSATION_MEMBERS = "members"
    const val FIRESTORE_CONVERSATION_MESSAGES = "messages"

    /**
     * Const of firestore message object
     */
    const val FIRESTORE_MESSAGE_COLLECTION = "Messages"
    const val FIRESTORE_MESSAGE_TEXT = "text"
    const val FIRESTORE_MESSAGE_AUTHOR_PHONE = "authorPhoneNumber"
    const val FIRESTORE_MESSAGE_DATETIME = "dateTime"
    const val FIRESTORE_MESSAGE_UNREAD_PATTERN = "unread_by_"

    /**
     * Const of firestore buy catalog object
     */
    const val FIRESTORE_KITCHEN_COLLECTION = "KitchenOrganizer"
    const val FIRESTORE_BUYS_CATALOG_TITLE = "title"
    const val FIRESTORE_BUYS_CATALOG_DATE = "date_of_create"
    const val FIRESTORE_BUYS_CATALOG_FOODS = "foods"
    const val FIRESTORE_BUYS_CATALOG_NUMBER_PRODUCTS = "number_of_products"
    const val FIRESTORE_BUYS_CATALOG_NUMBER_PURCHASED = "number_of_purchased"

    /**
     * Const of firestore food object
     */
    const val FIRESTORE_FOOD_TITLE = "title"
    const val FIRESTORE_FOOD_STATUS = "food_status"
    const val FIRESTORE_FOOD_QUANTITY_OF_MEASURE = "quantity_of_measure"
    const val FIRESTORE_FOOD_MEASURE_TYPE = "measure_type"
    const val FIRESTORE_FOOD_SHELF_LIFE_TIMESTAMP = "shelf_life_timestamp"

    /**
     * Const of firestore fridge
     */
    const val FIRESTORE_FRIDGE_COLLECTION = "Fridge"

    /**
     * Const of firestore events
     */
    const val FIRESTORE_EVENTS_COLLECTION = "Events"
    const val FIRESTORE_EVENTS_AUTHOR = "author"
    const val FIRESTORE_EVENTS_DATE = "date"
    const val FIRESTORE_EVENTS_PRIORITY = "priority"
    const val FIRESTORE_EVENTS_TITLE = "title"
    const val FIRESTORE_EVENTS_TYPE = "type"
    const val FIRESTORE_EVENTS_DESCRIPTION = "description"

    /**
     * Const of firestore tasks
     */
    const val FIRESTORE_TASKS_COLLECTION = "Tasks"
    const val FIRESTORE_TASKS_AUTHOR = "author"
    const val FIRESTORE_TASKS_WORKER = "worker"
    const val FIRESTORE_TASKS_TEXT = "text"
    const val FIRESTORE_TASKS_STATUS = "status"
    const val FIRESTORE_TASKS_TIME = "time"

    /**
     * Const of firestore conversation
     */
    const val FIRESTORE_COOPERATION_COLLECTION = "Cooperation"
    const val FIRESTORE_COOPERATION_AUTHOR = "author"
    const val FIRESTORE_COOPERATION_TYPE = "type"
    const val FIRESTORE_COOPERATION_ITEM = "item"
    const val FIRESTORE_COOPERATION_TIME = "time"
}