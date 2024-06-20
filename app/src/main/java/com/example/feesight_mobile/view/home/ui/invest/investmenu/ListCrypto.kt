package com.example.feesight_mobile.view.home.ui.invest.investmenu

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.feesight_mobile.R
import com.example.feesight_mobile.data.response.InvestResponse
import com.example.feesight_mobile.data.retrofit.ApiConfig
import com.example.feesight_mobile.databinding.ActivityListCryptoBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class ListCrypto : AppCompatActivity() {
    private lateinit var binding: ActivityListCryptoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListCryptoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        fetchCryptoData()
    }

    private fun fetchCryptoData() {
        showLoading(true)
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DAY_OF_YEAR, 1)
        val getDate = dateFormat.format(calendar.time) // Mengambil tanggal besok
        val request = mapOf("end_date" to getDate)
        val apiService = ApiConfig.getApiService()
        val call = apiService.predict(request)

        call.enqueue(object : Callback<InvestResponse> {
            override fun onResponse(call: Call<InvestResponse>, response: Response<InvestResponse>) {
                showLoading(false)
                if (response.isSuccessful) {
                    response.body()?.let { investResponse ->
                        val listCrypto = arrayListOf(
                            Invest("SOL/USD", "$ ${formatToTwoDecimalPlaces(investResponse.sOLUSD)}", R.drawable.solana),
                            Invest("BNB/USD", "$ ${formatToTwoDecimalPlaces(investResponse.bNBUSD)}", R.drawable.binance),
                            Invest("LINK/USD", "$ ${formatToTwoDecimalPlaces(investResponse.lINKUSD)}", R.drawable.chain_link),
                            Invest("ETH/USD", "$ ${formatToTwoDecimalPlaces(investResponse.eTHUSD)}", R.drawable.ethereum),
                            Invest("NEAR/USD", "$ ${formatToTwoDecimalPlaces(investResponse.nEARUSD)}", R.drawable.near_protocol),
                        )

                        setupRecyclerView(listCrypto)
                    } ?: run {
                        Toast.makeText(this@ListCrypto, "Response body is null", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this@ListCrypto, "Error: ${response.message()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<InvestResponse>, t: Throwable) {
                showLoading(false)
                Toast.makeText(this@ListCrypto, "Network Error: ${t.message}", Toast.LENGTH_SHORT).show()
                t.printStackTrace()
            }
        })
    }
    fun formatToTwoDecimalPlaces(value: Any): String {
        val decimalFormat = DecimalFormat("#,##0.00")
        return decimalFormat.format(value)
    }

    private fun setupRecyclerView(listCrypto: ArrayList<Invest>) {
        val adapter = ListCryptoAdapter(listCrypto)
        binding.rvCrypto.layoutManager = LinearLayoutManager(this)
        binding.rvCrypto.adapter = adapter
    }
    private fun showLoading(state: Boolean) { binding.progressBar.visibility = if (state) View.VISIBLE else View.GONE }
}
