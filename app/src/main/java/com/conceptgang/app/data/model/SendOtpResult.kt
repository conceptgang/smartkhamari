package com.conceptgang.app.data.model

import android.os.Parcelable
import com.google.firebase.auth.PhoneAuthCredential
import kotlinx.android.parcel.Parcelize

@Parcelize
class SendOtpResult private constructor(val verificationId: String? = null, val credential: PhoneAuthCredential? = null): Parcelable{
    constructor (verificationId: String): this(verificationId, null)
    constructor (credential: PhoneAuthCredential): this(null, credential)
}