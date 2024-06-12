package com.example.feesight_mobile.view.Transaction

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.feesight_mobile.databinding.ActivityCalendarBinding
import com.example.feesight_mobile.model.IncomeItem
import com.example.feesight_mobile.view.MainActivity
import com.example.feesight_mobile.view.Transaction.Adapter.IncomeAdapter

class CalendarActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCalendarBinding
    private lateinit var incomeAdapter: IncomeAdapter
    private lateinit var incomeList: MutableList<IncomeItem>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCalendarBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.item.layoutManager = LinearLayoutManager(this)
        incomeList = mutableListOf(
            IncomeItem("Salary", "Monthly Salary", "Rp 10,000,000", "01/01/2024"),
            IncomeItem("Freelance", "Project Payment", "Rp 2,000,000", "15/01/2024"),
            IncomeItem("Bonus", "Year End Bonus", "Rp 5,000,000", "31/12/2023"),
            IncomeItem("Investment", "Dividends", "Rp 1,500,000", "20/01/2024"),
            IncomeItem("Gajian", "Dividends", "Rp 1,500,000", "20/01/2024"),
            IncomeItem("Gajian", "Dividends", "Rp 1,500,000", "20/01/2024"),
            IncomeItem("Gajian", "Dividends", "Rp 1,500,000", "20/01/2024"),
            IncomeItem("Gajian", "Dividends", "Rp 1,500,000", "20/01/2024")
        )
        incomeAdapter = IncomeAdapter(incomeList)
        binding.item.adapter = incomeAdapter

        binding.imageButton.setOnClickListener{
            startActivity(Intent(this,MainActivity::class.java))
        }
    }
}