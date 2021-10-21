package com.example.template

import com.google.firebase.database.DataSnapshot

fun DataSnapshot.toUser(){
    this.getValue(User::class.java)
}