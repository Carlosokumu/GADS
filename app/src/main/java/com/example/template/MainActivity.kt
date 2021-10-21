package com.example.template

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.example.template.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class MainActivity : AppCompatActivity() {
    private  lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.appbar.toolbar)
        createNotificationChannel()
        val user=FirebaseAuth.getInstance().currentUser
        if (user != null) {
            Toast.makeText(this,user.uid,Toast.LENGTH_SHORT).show()
        }
        else {
            Toast.makeText(this,"am null",Toast.LENGTH_SHORT).show()
        }
      //  initFcm()
    }
    private fun createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = getString(R.string.app_name)
            val descriptionText = getString(R.string.app_name)
            val importance = NotificationManager.IMPORTANCE_DEFAULT

            val channel = NotificationChannel(ReminderNotification.REMINDER_CHANNEL, name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
    fun initFcm(){
        val svs=MessagingService()
        svs?.token?.let { Log.d("amsent", it) }
        svs.token?.let { sendRegistrationToServer(it) }
    }
    fun sendRegistrationToServer(token: String){
        val db= FirebaseDatabase.getInstance().reference
        db.child("users").child(FirebaseAuth.getInstance().currentUser?.uid!!).child("messaging_token").setValue(token)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.notification ->{
                ReminderNotification.notify(this,"Carlos","Learning Notifications")
            }
        }
        return true
    }



    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu,menu)
        return true
    }




}