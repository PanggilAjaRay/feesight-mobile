package com.example.feesight_mobile.view.home.ui.invest.investmenu

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.feesight_mobile.databinding.ItemCryptoBinding
import com.example.feesight_mobile.databinding.ItemInvestBinding

class ListInvestAdapter(private val listInvest: List<Invest>) : RecyclerView.Adapter<ListInvestAdapter.ListViewHolder>() {

    inner class ListViewHolder(private val binding: ItemInvestBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(invest: Invest) {
            binding.imageInvestList.setImageResource(invest.photo)
            binding.textInvestList.text = invest.name
            binding.percentageInvest.text = invest.price
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = ItemInvestBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(listInvest[position])
    }

    override fun getItemCount(): Int = listInvest.size
}