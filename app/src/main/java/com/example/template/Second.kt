package com.example.template

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.template.databinding.ActivityMainBinding
import com.example.template.databinding.ActivitySecondBinding

class Second : AppCompatActivity() {
    private  lateinit var binding: ActivitySecondBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.deep.text=intent.getStringExtra("EXTRAS")
    }
}