package com.example.feesight_mobile.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.feesight_mobile.databinding.ActivityMainBinding
import com.example.feesight_mobile.view.Transaction.CalendarActivity
import com.example.feesight_mobile.view.login.LoginActivity
import com.example.feesight_mobile.model.IncomeItem
import com.example.feesight_mobile.view.Transaction.Adapter.IncomeAdapter
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var incomeAdapter: IncomeAdapter
    private lateinit var incomeList: MutableList<IncomeItem>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = FirebaseAuth.getInstance()
        val currentUser = auth.currentUser
        if (currentUser == null) {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        } else {
            binding.userName.text = currentUser.displayName
            binding.userIcon.setOnClickListener {
                auth.signOut()
                startActivity(Intent(this, LoginActivity::class.java).apply {
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                })
                finish()
            }
            binding.dateIcon.setOnClickListener {
                startActivity(Intent(this, CalendarActivity::class.java))
            }
        }
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
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
        binding.recyclerIncome.layoutManager = LinearLayoutManager(this)
        binding.recyclerIncome.adapter = incomeAdapter
    }
}
