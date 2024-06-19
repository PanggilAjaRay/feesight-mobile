package com.example.feesight_mobile.view.home.ui.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.feesight_mobile.data.response.TransactionsResponse
import com.example.feesight_mobile.data.response.TransactionsResponseItem
import com.example.feesight_mobile.data.retrofit.ApiConfig
import com.example.feesight_mobile.databinding.FragmentHomeBinding
import com.example.feesight_mobile.view.home.HomeActivity
import com.example.feesight_mobile.view.login.LoginActivity
import com.example.feesight_mobile.view.transaction.CalendarActivity
import com.example.feesight_mobile.view.transaction.adapter.IncomeAdapter
import com.google.firebase.auth.FirebaseAuth
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth
    private lateinit var transactionsAdapter: IncomeAdapter
    private lateinit var transactionsList: MutableList<TransactionsResponseItem>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        auth = FirebaseAuth.getInstance()
//        val currentUser = auth.currentUser
//        if (currentUser == null) {
//            startActivity(Intent(activity, LoginActivity::class.java))
//            activity?.finish()
//        } else {
//            binding.userName.text = currentUser.displayName
//            binding.dateIcon.setOnClickListener {
//                startActivity(Intent(activity, CalendarActivity::class.java))
//            }
//        }
        val sharedPreferences = requireContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val token = sharedPreferences.getString("token", null)
        val name = sharedPreferences.getString("name", null)
        Log.d("TOKEN_LOG", token.toString())
        if (token == null) {
            startActivity(Intent(activity, LoginActivity::class.java))
            activity?.finish()
        } else {
            binding.userName.text = name
            binding.dateIcon.setOnClickListener {
                startActivity(Intent(activity, CalendarActivity::class.java))
            }
        }
        setupRecyclerView()
        fetchTransactions()
    }

    private fun setupRecyclerView() {
        transactionsList = mutableListOf()
        transactionsAdapter = IncomeAdapter(transactionsList)
        binding.recyclerIncome.layoutManager = LinearLayoutManager(activity)
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
