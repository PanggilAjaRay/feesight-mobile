package com.example.feesight_mobile.view.transaction.adapter
import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.feesight_mobile.R
import com.example.feesight_mobile.data.response.TransactionsResponseItem
import com.example.feesight_mobile.databinding.ItemIncomeBinding
import com.example.feesight_mobile.model.IncomeItem


class IncomeAdapter(private val transactionsList: List<TransactionsResponseItem>) : RecyclerView.Adapter<IncomeAdapter.TransactionsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionsViewHolder {
        val binding = ItemIncomeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TransactionsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TransactionsViewHolder, position: Int) {
        val item = transactionsList[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = transactionsList.size

    class TransactionsViewHolder(private val binding: ItemIncomeBinding) : RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(transactionItem: TransactionsResponseItem) {
            // Set icon based on transaction type or any logic you need
            binding.icon.setImageResource(R.drawable.baseline_attach_money_24)
            binding.tvItemName.text = transactionItem.type
            binding.tvItemDescription.text = transactionItem.category
            binding.tvItemAmount.text =  "Rp. ${transactionItem.amount}"
            binding.tvItemDate.text = transactionItem.date
        }
    }
}
