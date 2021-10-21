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
import android.widget.Button
import android.widget.Toast
import com.example.template.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.getValue

class MainActivity : AppCompatActivity() {
    private  lateinit var binding: ActivityMainBinding
    private  lateinit var updateName: String
    private lateinit var db: DatabaseReference
    private  lateinit var updatePhone: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.appbar.toolbar)
        db=FirebaseDatabase.getInstance().reference
        createNotificationChannel()
        val user=FirebaseAuth.getInstance().currentUser
        if (user != null) {
            Toast.makeText(this,user.uid,Toast.LENGTH_SHORT).show()
        }
        else {
            Toast.makeText(this,"am null",Toast.LENGTH_SHORT).show()
        }
      //  initFcm()
        binding.updateName.setOnClickListener {
            UpdateName()
        }
        binding.updatePhone.setOnClickListener {
            updatePhone()
        }
        getAccountDetails()
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

  fun updatePhone(){
      db.child("users").child(FirebaseAuth.getInstance().currentUser?.uid!!)
              .child("phone")
              .setValue(binding.phone.text.toString())
  }
 fun UpdateName()  {
     db.child("users").child(FirebaseAuth.getInstance().currentUser?.uid!!)
             .child("name")
             .setValue(binding.name.text.toString())
 }
  fun getAccountDetails()  {
      /*
         Query method One
       */
      val query=db.child("users").orderByKey().equalTo(FirebaseAuth.getInstance().currentUser?.uid)
              .addListenerForSingleValueEvent(object :ValueEventListener{
                  override fun onDataChange(snapshot: DataSnapshot) {
                      for (dataSnapshot in snapshot.children){
                          val user=dataSnapshot.getValue<User>()
                          if (user != null) {
                              Log.d("querryOne", user.name)
                          }
                      }
                  }

                  override fun onCancelled(error: DatabaseError) {

                  }

              })
  }


}