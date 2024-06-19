package com.example.feesight_mobile.data.retrofit

import com.example.feesight_mobile.data.response.InvestResponse
import com.example.feesight_mobile.data.response.TransactionsResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {
    @GET("user/transactions")
    fun getTransactions(): Call<TransactionsResponse>

    @POST("predict")
    fun predict(@Body request: Map<String, String>): Call<InvestResponse>
}