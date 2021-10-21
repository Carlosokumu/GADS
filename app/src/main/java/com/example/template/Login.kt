package com.example.template

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.template.databinding.ActivityLoginBinding
import com.example.template.databinding.ActivitySecondBinding
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase
import java.lang.Exception

class Login : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = FirebaseAuth.getInstance()
        if (auth.currentUser != null){
            startActivity(Intent(this,MainActivity::class.java))
        }
        binding.signUp.setOnClickListener {
            signUp()
        }

    }
    private fun signUp(){
        auth.createUserWithEmailAndPassword(binding.email.text.toString(),binding.password.text.toString())
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user=User("mike")
                    val db= FirebaseDatabase.getInstance().reference
                    db.child("users").child(FirebaseAuth.getInstance().currentUser?.uid!!).setValue(user)
                            .addOnCompleteListener(object : OnCompleteListener<Void>{
                                override fun onComplete(p0: Task<Void>) {
                                    Toast.makeText(this@Login,"success",Toast.LENGTH_SHORT).show()
                                }

                            }).addOnFailureListener(object: OnFailureListener{
                                override fun onFailure(p0: Exception) {
                                   Toast.makeText(this@Login,"failed",Toast.LENGTH_SHORT).show()
                                }

                            })

                } else {
                    Toast.makeText(this,task.exception.toString(),Toast.LENGTH_SHORT).show()
                }
            }
    }

}