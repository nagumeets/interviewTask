package com.example.interviewtask.data

import com.example.interviewtask.data.models.StockOwnerResponse
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface MyApi {

    @GET("answers")
    suspend fun getStockersData(
            @Query("page") page: Int=1,
            @Query("pagesize") pagesize: Int = 50,
            @Query("site") site: String = "stackoverflow"
    ): StockOwnerResponse

    companion object {

        private const val BASE_URL = "http://api.stackexchange.com/2.2/"

        operator fun invoke(): MyApi = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(OkHttpClient.Builder().also { client ->
                    val logging = HttpLoggingInterceptor()
                    logging.setLevel(HttpLoggingInterceptor.Level.BODY)
                    client.addInterceptor(logging)
                }.build())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(MyApi::class.java)
    }
}