package com.conceptgang.app.data.model

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

object FireStorePath {
    const val user = "user"
    const val cow = "cow"
    const val sell = "sell"
    const val customer = "customer"
    const val defaultCowID = "cow"
    const val defaultCowImage = "url"

    val firebaseUser by lazy { FirebaseAuth.getInstance().currentUser!! }

    val userCowStore by lazy { Firebase.firestore.collection(FireStorePath.user).document(firebaseUser.uid).collection(FireStorePath.cow) }
}