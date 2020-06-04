package com.tydeya.familycircle.domain.kitchenorganizer.notifications

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.tydeya.familycircle.R
import com.tydeya.familycircle.data.constants.FireStore.FRIDGE_COLLECTION
import com.tydeya.familycircle.domain.kitchenorganizer.utils.convertServerDataToFood
import com.tydeya.familycircle.utils.extensions.currentFamilyId
import com.tydeya.familycircle.utils.extensions.firestoreFamily
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*
import kotlin.collections.ArrayList

const val KITCHEN_CHANNEL_ID = "kitchen_channel"
const val NOTIFY_ID = 200
const val TWO_DAYS_IN_MILLISECONDS = 172800000L
const val SHELF_LIFE_TAG = "SHELF_LIFE"

class KitchenOrganizerShelfLifeReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        firestoreFamily(context.currentFamilyId).collection(FRIDGE_COLLECTION).get()
                .addOnSuccessListener { querySnapshot ->

                    val actualTimestamp = System.currentTimeMillis()
                    val spoiledProductsTitles = ArrayList<String>()
                    val notFreshProductsTitles = ArrayList<String>()

                    querySnapshot.documents.forEach { document ->
                        val food = convertServerDataToFood(document)
                        if (food.shelfLifeTimeStamp != -1L) {
                            if (food.shelfLifeTimeStamp < actualTimestamp) {
                                spoiledProductsTitles.add(food.title)
                            } else if (food.shelfLifeTimeStamp - actualTimestamp <=
                                    TWO_DAYS_IN_MILLISECONDS) {
                                notFreshProductsTitles.add(food.title)
                            }
                        }
                    }

                    if (spoiledProductsTitles.size > 0 || notFreshProductsTitles.size > 0) {

                        val notificationTextBody = StringBuilder()

                        if (spoiledProductsTitles.size > 0) {
                            notificationTextBody.append(
                                    context.getString(
                                            R.string.shelf_life_notification_spoiled_placeholder,
                                            spoiledProductsTitles.joinToString(", "))
                            ).append("\n")
                        }

                        if (notFreshProductsTitles.size > 0) {
                            notificationTextBody.append(
                                    context.getString(
                                            R.string.shelf_life_notification_not_fresh_placeholder,
                                            notFreshProductsTitles.joinToString(", "))
                            )
                        }
                        sendShelfLifeNotification(context, notificationTextBody.toString())
                    }

                }.addOnFailureListener {
                    Log.e(SHELF_LIFE_TAG, it.toString())
                }

    }

    private fun sendShelfLifeNotification(context: Context, text: String) {
        val builder = NotificationCompat.Builder(context, KITCHEN_CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_close_black_24dp)
                .setContentTitle(context.getString(R.string.shelf_life_notification_title))
                .setContentText(text)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        val notificationManager: NotificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = context.getString(R.string.kitchen_organizer_notification_channel_title)
            val descriptionText = context.getString(R.string.kitchen_organizer_notification_channel_description)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(KITCHEN_CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            notificationManager.createNotificationChannel(channel)
        }

        notificationManager.notify(NOTIFY_ID, builder.build())
    }

    companion object {

        fun initAlarm(context: Context) = GlobalScope.launch(Dispatchers.Default) {

            val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            val intent = Intent(context, KitchenOrganizerShelfLifeReceiver::class.java)
            val pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0)

            val calendar: Calendar = Calendar.getInstance().apply {
                timeInMillis = System.currentTimeMillis()
                set(Calendar.HOUR_OF_DAY, 18)
                set(Calendar.MINUTE, 0)
                if (timeInMillis < System.currentTimeMillis()) {
                    add(Calendar.DAY_OF_MONTH, 1)
                }
            }
            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.timeInMillis,
                    AlarmManager.INTERVAL_DAY, pendingIntent)
        }
    }

}
