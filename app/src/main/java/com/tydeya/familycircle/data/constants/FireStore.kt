package com.tydeya.familycircle.data.constants

object FireStore {

    /**
     * Const for firestore family
     * */

    const val FAMILY_COLLECTION = "Families"
    const val FAMILY_TITLE_TAG = "title"
    const val FAMILY_NUMBER_OF_MEMBERS_TAG = "number_of_members"
    const val FAMILY_AUTHOR_PHONE_TAG = "author_phone"

    /**
     * Const for firestore tweets
     * */

    const val TWEET_COLLECTION = "Twitter"
    const val TWEET_TEXT = "text"
    const val TWEET_PHONE = "author_phone"
    const val TWEET_NAME = "author_name"
    const val TWEET_TIME = "author_time"

    /**
     * Const for firestore user object
     */
    const val USERS_COLLECTION = "Users"
    const val USERS_PHONE_TAG = "phone_number"
    const val USERS_NAME_TAG = "name"
    const val USERS_BIRTH_TAG = "birth_date"
    const val USERS_STUDY_TAG = "study_place"
    const val USERS_WORK_TAG = "work_place"
    const val USERS_IMAGE_PATH = "image"
    const val USERS_LAST_ONLINE = "last_online"
    const val USERS_FAMILY_TITLES = "family_titles"
    const val USERS_FAMILY_ICONS = "family_icons"
    const val USERS_FAMILY_SIZES = "family_sizes"
    const val USERS_FAMILY_INVITES = "family_invites"
    const val USERS_FAMILY_IDS = "family_ids"
    const val USERS_FCM_TOKEN = "fcm_token"

    /**
     * Const of firestore conversation object
     */
    const val CONVERSATION_COLLECTION = "Messenger"
    const val CONVERSATION_TITLE = "title"
    const val CONVERSATION_MEMBERS = "members"
    const val CONVERSATION_MESSAGES = "messages"

    /**
     * Const of firestore message object
     */
    const val MESSAGE_COLLECTION = "Messages"
    const val MESSAGE_TEXT = "text"
    const val MESSAGE_AUTHOR_PHONE = "authorPhoneNumber"
    const val MESSAGE_DATETIME = "dateTime"
    const val MESSAGE_UNREAD_PATTERN = "unread_by_"

    /**
     * Const of firestore buy catalog object
     */
    const val KITCHEN_COLLECTION = "KitchenOrganizer"
    const val BUYS_CATALOG_TITLE = "title"
    const val BUYS_CATALOG_DATE = "date_of_create"
    const val BUYS_CATALOG_FOODS = "foods"
    const val BUYS_CATALOG_NUMBER_PRODUCTS = "number_of_products"
    const val BUYS_CATALOG_NUMBER_PURCHASED = "number_of_purchased"

    /**
     * Const of firestore food object
     */
    const val FOOD_TITLE = "title"
    const val FOOD_STATUS = "food_status"
    const val FOOD_QUANTITY_OF_MEASURE = "quantity_of_measure"
    const val FOOD_MEASURE_TYPE = "measure_type"
    const val FOOD_SHELF_LIFE_TIMESTAMP = "shelf_life_timestamp"

    /**
     * Const of firestore fridge
     */
    const val FRIDGE_COLLECTION = "Fridge"

    /**
     * Const of firestore events
     */
    const val EVENTS_COLLECTION = "Events"
    const val EVENTS_AUTHOR = "author"
    const val EVENTS_DATE = "date"
    const val EVENTS_PRIORITY = "priority"
    const val EVENTS_TITLE = "title"
    const val EVENTS_TYPE = "type"
    const val EVENTS_DESCRIPTION = "description"

    /**
     * Const of firestore tasks
     */
    const val TASKS_COLLECTION = "Tasks"
    const val TASKS_AUTHOR = "author"
    const val TASKS_WORKER = "workers"
    const val TASKS_TITLE = "title"
    const val TASKS_TEXT = "text"
    const val TASKS_STATUS = "status"

    /**
     * Const of firestore conversation
     */
    const val COOPERATION_COLLECTION = "Cooperation"
    const val COOPERATION_AUTHOR = "author"
    const val COOPERATION_TYPE = "type"
    const val COOPERATION_ITEM = "item"
    const val COOPERATION_TIME = "time"
}