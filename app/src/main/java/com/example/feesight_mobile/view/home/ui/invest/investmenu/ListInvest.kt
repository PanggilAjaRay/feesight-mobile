package com.example.feesight_mobile.view.home.ui.invest.investmenu

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.feesight_mobile.R


class ListInvest : AppCompatActivity() {
    private lateinit var rvInvest: RecyclerView
    private val list = ArrayList<Invest>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_invest)
        rvInvest = findViewById(R.id.rv_invest)
        rvInvest.setHasFixedSize(true)
        list.addAll(getListHeroes())
        showRecyclerList()
    }


    private fun getListHeroes(): ArrayList<Invest> {
        val dataName = resources.getStringArray(R.array.data_invest)
        val dataPercentage = resources.getStringArray(R.array.percentage_invest)
        val dataPhoto = resources.obtainTypedArray(R.array.data_photo_invest)
        val listHero = ArrayList<Invest>()
        for (i in dataName.indices) {
            val hero = Invest(dataName[i], dataPercentage[i],dataPhoto.getResourceId(i, -1))
            listHero.add(hero)
        }
        return listHero
    }

    private fun showRecyclerList() {
        rvInvest.layoutManager = LinearLayoutManager(this)
        val listHeroAdapter = ListInvestAdapterTemp(list)
        rvInvest.adapter = listHeroAdapter
    }
}