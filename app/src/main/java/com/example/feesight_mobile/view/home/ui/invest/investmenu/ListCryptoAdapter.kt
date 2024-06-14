package com.example.feesight_mobile.view.home.ui.invest.investmenu

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.feesight_mobile.R

class ListCryptoAdapter(private val listCrypto: ArrayList<Invest>) : RecyclerView.Adapter<ListCryptoAdapter.ListViewHolder>(){
    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgPhoto: ImageView = itemView.findViewById(R.id.image_crypto_list)
        val tvName: TextView = itemView.findViewById(R.id.text_crypto_list)
        val tvPercentage: TextView = itemView.findViewById(R.id.percentage_crypto)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_crypto, parent, false)
        return ListViewHolder(view)
    }

    override fun getItemCount(): Int = listCrypto.size

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val (name, percentage, photo) = listCrypto[position]
        holder.imgPhoto.setImageResource(photo)
        holder.tvName.text = name
        holder.tvPercentage.text = percentage
    }

}