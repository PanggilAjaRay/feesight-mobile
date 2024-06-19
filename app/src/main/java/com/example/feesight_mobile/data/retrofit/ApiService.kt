package com.example.feesight_mobile.data.retrofit

import com.example.feesight_mobile.data.request.LoginRequest
import com.example.feesight_mobile.data.request.RegisterRequest
import com.example.feesight_mobile.data.response.AddTransactionResponse
import com.example.feesight_mobile.data.response.InvestResponse
import com.example.feesight_mobile.data.response.LoginResponse
import com.example.feesight_mobile.data.response.RegisterResponse
import com.example.feesight_mobile.data.response.TransactionRequest
import com.example.feesight_mobile.data.response.TransactionsResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.*

interface ApiService {
    @GET("user/transactions")
    fun getTransactions(): Call<TransactionsResponse>

    @GET("user/transactions")
    fun getTransactionsByDate(@Query("date") date: String): Call<TransactionsResponse>

    @POST("predict")
    fun predict(@Body request: Map<String, String>): Call<InvestResponse>

    @POST("user/transactions")
    fun createTransaction(@Body transaction: TransactionRequest): Call<AddTransactionResponse>

    // Endpoint untuk signup
    @POST("signup")
    fun register(@Body registerRequest: RegisterRequest): Call<RegisterResponse>

    // Endpoint untuk login
    @POST("login")
    fun login(@Body loginRequest: LoginRequest): Call<LoginResponse>
}