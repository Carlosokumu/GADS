package com.example.template

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import java.lang.NullPointerException

class MessagingService:FirebaseMessagingService() {
    private val TAG="MESSAGE"
    override fun onDeletedMessages() {
        super.onDeletedMessages()
    }
     var token: String?=null
    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        var notificationBody : String=""
        var notificationTitle : String=""
        var notificationData : String=""
        try {
           notificationBody= message.notification?.body.toString()
            notificationTitle=message.notification.toString()
            notificationData=message.data.toString()

        }catch (e: NullPointerException){
               Log.d("notsent",e.message!!)
        }
        Log.d("hereiam",notificationBody)
        Log.d("heream",notificationData)
        Log.d("heretitle",notificationTitle)
    }
    override fun onNewToken(token: String) {
        super.onNewToken(token)
        this.token=token
        Log.d("amnew",token)
        //sendRegistrationToServer(token)
    }
    fun sendRegistrationToServer(token: String){
        val db=FirebaseDatabase.getInstance().reference
        db.child("users").child(FirebaseAuth.getInstance().currentUser?.uid!!).child("messaging_token").setValue(token)
    }
}