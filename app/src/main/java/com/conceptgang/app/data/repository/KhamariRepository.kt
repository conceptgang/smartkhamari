package com.conceptgang.app.data.repository

import android.content.SharedPreferences
import com.conceptgang.app.Database
import com.conceptgang.app.data.remote.FirebaseAuthClient

class KhamariRepository (
    val firebaseAuthClient: FirebaseAuthClient,
    val database: Database
){

}