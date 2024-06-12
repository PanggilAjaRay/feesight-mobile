package com.example.feesight_mobile.view.Transaction.Adapter
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.feesight_mobile.R
import com.example.feesight_mobile.databinding.ItemIncomeBinding
import com.example.feesight_mobile.model.IncomeItem

class IncomeAdapter(private val incomeList: List<IncomeItem>) : RecyclerView.Adapter<IncomeAdapter.IncomeViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IncomeViewHolder {
        val binding = ItemIncomeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return IncomeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: IncomeViewHolder, position: Int) {
        val item = incomeList[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = incomeList.size

    class IncomeViewHolder(private val binding: ItemIncomeBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(incomeItem: IncomeItem) {
            binding.icon.setImageResource(R.drawable.baseline_attach_money_24)
            binding.tvItemName.text = incomeItem.title
            binding.tvItemDescription.text = incomeItem.description
            binding.tvItemAmount.text = incomeItem.amount
            binding.tvItemDate.text = incomeItem.date
        }
    }
}