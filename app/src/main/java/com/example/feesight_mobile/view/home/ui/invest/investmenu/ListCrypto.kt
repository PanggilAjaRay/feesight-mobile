package com.example.feesight_mobile.view.home.ui.invest.investmenu

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.feesight_mobile.R

class ListCrypto : AppCompatActivity() {
    private lateinit var rvCrypto: RecyclerView
    private val list = ArrayList<Invest>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_list_crypto)
        rvCrypto = findViewById(R.id.rv_crypto)
        rvCrypto.setHasFixedSize(true)

        list.addAll(getListCrypto())
        showRecyclerList()

    }
    private fun getListCrypto(): ArrayList<Invest> {
        val dataName = resources.getStringArray(R.array.data_crypto)
        val dataDescription = resources.getStringArray(R.array.percentage_invest)
        val dataPhoto = resources.obtainTypedArray(R.array.data_photo_crypto)
        val listCrypto = ArrayList<Invest>()
        for (i in dataName.indices) {
            val crypto = Invest(dataName[i], dataDescription[i], dataPhoto.getResourceId(i, -1))
            listCrypto.add(crypto)
        }
        return listCrypto
    }
    private fun showRecyclerList() {
        rvCrypto.layoutManager = LinearLayoutManager(this)
        val listHeroAdapter = ListCryptoAdapter(list)
        rvCrypto.adapter = listHeroAdapter
    }
}