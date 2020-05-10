package com.conceptgang.app

import android.app.Application
import android.util.Log
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

class MainApp: Application(){

    /*
    private val PRIVATE_MODE = 0
    private val PREF_NAME = "MYAPPP"
    private val BASE_URL = "https://domain.com/"
    */

    companion object{
        const val NAME = "MainApp"
    }

    override fun onCreate() {
        super.onCreate()


        /*
        val prefs = getSharedPreferences(PREF_NAME, PRIVATE_MODE)


        val logging = HttpLoggingInterceptor()
        logging.level = (HttpLoggingInterceptor.Level.BODY)

        val okHttpClient = OkHttpClient
            .Builder()
            .connectTimeout(120, TimeUnit.MINUTES)
            .readTimeout(120, TimeUnit.SECONDS)
            .writeTimeout(120, TimeUnit.SECONDS)
            .addInterceptor(logging)
            .addNetworkInterceptor { chain ->

                val originalResponse: Response = chain.proceed(chain.request())

                originalResponse
                    .newBuilder()
                    .body(ProgressResponseBody(originalResponse.body) { bytesRead, b, c ->

                        byteProgressMutableLiveData.postValue(bytesRead)

                        Log.d("okhttpprogress", "$bytesRead $b $c")

                    })
                    .build()

            }
            .addNetworkInterceptor { chain ->

                val originalRequest: Request = chain.request()

                if (originalRequest.body == null) {
                    chain.proceed(originalRequest)
                } else {

                    val progressRequest: Request = originalRequest.newBuilder()
                        .method(
                            originalRequest.method,
                            CountingRequestBody(originalRequest.body){bytesWritten, b ->
                                byteProgressMutableLiveData.postValue(bytesWritten)
                                Log.d("okhttpprogress", "third party $bytesWritten $b ")
                            }
                        )
                        .build()

                    chain.proceed(progressRequest)
                }


            }
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
        */
    }

}