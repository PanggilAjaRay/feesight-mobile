package com.example.feesight_mobile.view.transaction

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.feesight_mobile.data.response.BalanceResponse
import com.example.feesight_mobile.data.response.SpareMoneyResponse
import com.example.feesight_mobile.data.response.TransactionsResponse
import com.example.feesight_mobile.data.response.TransactionsResponseItem
import com.example.feesight_mobile.data.retrofit.ApiConfig
import com.example.feesight_mobile.databinding.ActivityCalendarBinding
import com.example.feesight_mobile.view.home.HomeActivity
import com.example.feesight_mobile.view.transaction.adapter.IncomeAdapter
import com.example.feesight_mobile.view.transaction.expenseincome.ExpenseIncomeActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

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

        val dateFormat = SimpleDateFormat("yyyy-M-dd", Locale.getDefault())
        val currentDate = dateFormat.format(Date())
        fetchTransactionsByDate(currentDate)
        fetchBalance(currentDate)
        fetchSpareMoney(currentDate)

        // Mendapatkan tanggal dari calendar
        binding.calendarView.setOnDateChangeListener { view, year, month, dayOfMonth ->
            val calendar = Calendar.getInstance()
            calendar.set(year, month, dayOfMonth)
            val sdf = SimpleDateFormat("yyyy-M-dd", Locale.getDefault())
            val selectedDate = sdf.format(calendar.time)

            Log.d("DATE_LOG", selectedDate)
            // Memanggil fungsi fetchTransactionsByDate dengan tanggal yang dipilih
            fetchTransactionsByDate(selectedDate)
            fetchBalance(selectedDate)
            fetchSpareMoney(selectedDate)
        }
    }

    private fun setupRecyclerView() {
        transactionsList = mutableListOf()
        transactionsAdapter = IncomeAdapter(transactionsList)
        binding.recyclerIncome.adapter = transactionsAdapter
    }

    private fun fetchTransactionsByDate(date: String) {
        val apiService = ApiConfig.getApiService()

        val call = apiService.getTransactionsByDate(date)
        call.enqueue(object : Callback<TransactionsResponse> {
            override fun onResponse(call: Call<TransactionsResponse>, response: Response<TransactionsResponse>) {
                if (response.isSuccessful) {
                    val transactions = response.body() ?: emptyList()
                    transactionsList.clear()
                    transactionsList.addAll(transactions)
                    transactionsAdapter.notifyDataSetChanged()
                    Log.d("CalendarActivity", "Data fetched successfully: $transactions")
                } else {
                    Log.e("CalendarActivity", "Error response code: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<TransactionsResponse>, t: Throwable) {
                Log.e("CalendarActivity", "API call failed", t)
            }
        })
    }

    private fun fetchBalance(selectedDate: String) {
        val apiService = ApiConfig.getApiService()

        val call = apiService.getBalance(selectedDate)
        call.enqueue(object : Callback<BalanceResponse> {
            override fun onResponse(call: Call<BalanceResponse>, response: Response<BalanceResponse>) {
                if (response.isSuccessful) {
                    val balance = response.body()?.balance ?: "RP.0.00"
                    binding.balance.text = "Rp. $balance"
                    Log.d("Calendar", "Balance fetched successfully: $balance")
                } else {
                    Log.e("Calendar", "Error response code: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<BalanceResponse>, t: Throwable) {
                Log.e("Calendar", "API call failed", t)
            }
        })
    }

    private fun fetchSpareMoney(selectedDate: String) {
        val apiService = ApiConfig.getApiService()

        val call = apiService.getSpareMoney(selectedDate)
        call.enqueue(object : Callback<SpareMoneyResponse> {
            override fun onResponse(call: Call<SpareMoneyResponse>, response: Response<SpareMoneyResponse>) {
                if (response.isSuccessful) {
                    val balance = response.body()?.balance ?: "RP.0.00"
                    binding.balanceProject.text = "Rp. $balance"
                    Log.d("Calendar", "Spare Money fetched successfully: $balance")
                } else {
                    Log.e("Calendar", "Error response code: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<SpareMoneyResponse>, t: Throwable) {
                Log.e("Calendar", "API call failed", t)
            }
        })
    }

}

