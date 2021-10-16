package com.example.template

import android.app.Notification
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.app.TaskStackBuilder

object ReminderNotification {
    const val REMINDER_CHANNEL="reminders"
    const val REMINDER_ID=0

    fun notify(context: Context,titleText: String,message: String){
        val shareIntent=PendingIntent.getActivity(context,0, Intent.createChooser(Intent(Intent.ACTION_SEND)
                .setType("text/plain")
                .putExtra(Intent.EXTRA_TEXT,message),"SHARE"),PendingIntent.FLAG_UPDATE_CURRENT)
        val intent=Intent(Intent(context,Second::class.java))
        //It ensures that once a user launches the pending intent from the notification,a backStack is maintained
        val pendingIntent=TaskStackBuilder.create(context).addNextIntentWithParentStack(intent).getPendingIntent(0,PendingIntent.FLAG_UPDATE_CURRENT)
        intent.putExtra("EXTRAS","Am Deep Linked")
        val builder=NotificationCompat.Builder(context, REMINDER_CHANNEL)
            //Set Defaults for the notification ie..Light,Sound,Vibration
            .setDefaults(Notification.DEFAULT_ALL)
            .setSmallIcon(R.drawable.ic_release)
            .setContentTitle(titleText)
            .setContentText(message)
                .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setTicker(titleText)
            .setAutoCancel(true)
                .addAction(R.drawable.ic_release,"Share",shareIntent)
       val m= NotificationManagerCompat.from(context)
        m.notify(REMINDER_ID,builder.build())

    }
}