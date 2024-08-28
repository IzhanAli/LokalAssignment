package com.lokalassignment.applokal.retrofit

import com.lokalassignment.applokal.util.JobsAPI
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitInstance {
    companion object {
         private const val URL: String = "https://testapi.getlokalapp.com/"


         val retrofit by lazy {
            Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

        val api by lazy {
            retrofit.create(JobsAPI::class.java)
        }
    }
}