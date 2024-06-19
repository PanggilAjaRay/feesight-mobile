package com.example.feesight_mobile.view.home.ui.invest.investmenu

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.feesight_mobile.R
import com.example.feesight_mobile.data.response.InvestResponse
import com.example.feesight_mobile.data.retrofit.ApiConfig
import com.example.feesight_mobile.databinding.ActivityListInvestBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.DecimalFormat


class ListInvest : AppCompatActivity() {
    private lateinit var binding: ActivityListInvestBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListInvestBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        fetchInvestData()
    }

    private fun fetchInvestData() {
        showLoading(true)
        val request = mapOf("end_date" to "2024-06-19")
        val apiService = ApiConfig.getApiService()
        val call = apiService.predict(request)

        call.enqueue(object : Callback<InvestResponse> {
            override fun onResponse(call: Call<InvestResponse>, response: Response<InvestResponse>) {
                showLoading(false)
                if (response.isSuccessful) {
                    response.body()?.let { investResponse ->
                        val listInvest = arrayListOf(
                            Invest("TLKM/JK", "Rp. ${formatToTwoDecimalPlaces(investResponse.tLKMJK)}", R.drawable.telkom),
                            Invest("INDF/JK", "Rp. ${formatToTwoDecimalPlaces(investResponse.iNDFJK)}", R.drawable.indf),
                            Invest("BBCA/JK", "Rp. ${formatToTwoDecimalPlaces(investResponse.bBCAJK)}", R.drawable.bc),
                            Invest("BBRI/JK", "Rp. ${formatToTwoDecimalPlaces(investResponse.bBRIJK)}", R.drawable.bri),
                            Invest("AMZN", "$ ${formatToTwoDecimalPlaces(investResponse.aMZN)}", R.drawable.social)
                        )

                        setupRecyclerView(listInvest)
                    } ?: run {
                        Toast.makeText(this@ListInvest, "Response body is null", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this@ListInvest, "Error: ${response.message()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<InvestResponse>, t: Throwable) {
                showLoading(false)
                Toast.makeText(this@ListInvest, "Network Error: ${t.message}", Toast.LENGTH_SHORT).show()
                t.printStackTrace()
            }
        })
    }
    fun formatToTwoDecimalPlaces(value: Any): String {
        val decimalFormat = DecimalFormat("#,##0.00")
        return decimalFormat.format(value)
    }

    private fun setupRecyclerView(listInvest: ArrayList<Invest>) {
        val adapter = ListInvestAdapter(listInvest)
        binding.rvInvest.layoutManager = LinearLayoutManager(this)
        binding.rvInvest.adapter = adapter
    }
    private fun showLoading(state: Boolean) { binding.progressBar.visibility = if (state) View.VISIBLE else View.GONE }
}
