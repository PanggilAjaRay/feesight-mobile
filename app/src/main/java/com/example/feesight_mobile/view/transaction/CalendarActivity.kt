package com.example.feesight_mobile.view.transaction

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.feesight_mobile.data.response.TransactionsResponse
import com.example.feesight_mobile.data.response.TransactionsResponseItem
import com.example.feesight_mobile.data.retrofit.ApiConfig
import com.example.feesight_mobile.view.home.HomeActivity
import com.example.feesight_mobile.view.transaction.adapter.IncomeAdapter
import com.example.feesight_mobile.view.transaction.expenseincome.ExpenseIncomeActivity
import com.example.feesight_mobile.databinding.ActivityCalendarBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CalendarActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCalendarBinding
    private lateinit var transactionsAdapter: IncomeAdapter
    private lateinit var transactionsList: MutableList<TransactionsResponseItem>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCalendarBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.recyclerIncome.layoutManager = LinearLayoutManager(this)
        binding.imageButton.setOnClickListener{
            startActivity(Intent(this,HomeActivity::class.java))
        }
        binding.imageButtonAdd.setOnClickListener{
            startActivity(Intent(this,ExpenseIncomeActivity::class.java))
        }
        setupRecyclerView()
        fetchTransactions()
    }

    private fun setupRecyclerView() {
        transactionsList = mutableListOf()
        transactionsAdapter = IncomeAdapter(transactionsList)
        binding.recyclerIncome.layoutManager = LinearLayoutManager(this)
        binding.recyclerIncome.adapter = transactionsAdapter
    }
    private fun fetchTransactions() {
        val apiService = ApiConfig.getApiService()

        val call = apiService.getTransactions()
        call.enqueue(object : Callback<TransactionsResponse> {
            override fun onResponse(call: Call<TransactionsResponse>, response: Response<TransactionsResponse>) {
                if (response.isSuccessful) {
                    val transactions = response.body() ?: emptyList()
                    transactionsList.clear()
                    transactionsList.addAll(transactions)
                    transactionsAdapter.notifyDataSetChanged()
                    Log.d("HomeFragment", "Data fetched successfully: $transactions")
                } else {
                    Log.e("HomeFragment", "Error response code: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<TransactionsResponse>, t: Throwable) {
                Log.e("HomeFragment", "API call failed", t)
            }
        })
    }
}