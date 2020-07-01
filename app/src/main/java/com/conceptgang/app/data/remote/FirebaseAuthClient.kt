package com.conceptgang.app.data.remote

import com.conceptgang.app.MainActivity
import com.conceptgang.app.data.model.SendOtpResult
import com.google.firebase.FirebaseException
import com.google.firebase.auth.*
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.tasks.await
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import kotlin.coroutines.resumeWithException

class FirebaseAuthClient @Inject constructor (private val mainActivity: MainActivity) {

    private val auth = FirebaseAuth.getInstance().apply { setLanguageCode("en") }

    //private lateinit var storedVerificationId: String
    private var resendToken: PhoneAuthProvider.ForceResendingToken? = null


    suspend fun signInWithOTP(sendOtpResult: SendOtpResult, otp: String): FirebaseUser {

        val credeltial = sendOtpResult.credential ?: PhoneAuthProvider.getCredential(sendOtpResult.verificationId!!, otp)

        val user = auth.signInWithCredential(credeltial).await()

        return user.user!!
    }

    suspend fun sendOTPCode(phoneNumber: String) =
        suspendCancellableCoroutine<SendOtpResult> { cont ->

            val callback = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

                override fun onVerificationCompleted(credential: PhoneAuthCredential) {

                    Timber.d("onVerificationCompleted:$credential")

                    if (cont.isActive) cont.resumeWith(Result.success(SendOtpResult(credential)))
                }

                override fun onVerificationFailed(e: FirebaseException) {
                    Timber.e(e, "onVerificationFailed")
                    cont.resumeWithException(e)
                }

                override fun onCodeSent(
                    verificationId: String,
                    token: PhoneAuthProvider.ForceResendingToken
                ) {
                    Timber.d("onCodeSent:$verificationId")

                    //storedVerificationId = verificationId
                    resendToken = token

                    if (cont.isActive) cont.resumeWith(Result.success(SendOtpResult(verificationId)))
                }
            }

            PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber, // Phone number to verify
                60, // Timeout duration
                TimeUnit.SECONDS, // Unit of timeout
                mainActivity, // Activity (for callback binding)
                callback,
                resendToken
            )
        }

}