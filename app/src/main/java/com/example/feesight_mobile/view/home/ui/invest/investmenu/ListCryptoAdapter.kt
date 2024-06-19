package com.example.feesight_mobile.view.home.ui.invest.investmenu

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.feesight_mobile.databinding.ItemCryptoBinding

class ListCryptoAdapter(private val listCrypto: List<Invest>) : RecyclerView.Adapter<ListCryptoAdapter.ListViewHolder>() {

    inner class ListViewHolder(private val binding: ItemCryptoBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(invest: Invest) {
            binding.imageCryptoList.setImageResource(invest.photo)
            binding.textCryptoList.text = invest.name
            binding.percentageCrypto.text = invest.price
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = ItemCryptoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(listCrypto[position])
    }

    override fun getItemCount(): Int = listCrypto.size
}
