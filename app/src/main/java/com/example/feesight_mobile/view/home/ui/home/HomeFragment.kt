package com.example.feesight_mobile.view.home.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.feesight_mobile.databinding.FragmentHomeBinding
import com.example.feesight_mobile.model.IncomeItem
import com.example.feesight_mobile.view.login.LoginActivity
import com.example.feesight_mobile.view.transaction.CalendarActivity
import com.example.feesight_mobile.view.transaction.adapter.IncomeAdapter
import com.google.firebase.auth.FirebaseAuth

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth
    private lateinit var incomeAdapter: IncomeAdapter
    private lateinit var incomeList: MutableList<IncomeItem>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = FirebaseAuth.getInstance()
        val currentUser = auth.currentUser
        if (currentUser == null) {
            startActivity(Intent(activity, LoginActivity::class.java))
            activity?.finish()
        } else {
            binding.userName.text = currentUser.displayName
            binding.userIcon.setOnClickListener {
                auth.signOut()
                startActivity(Intent(activity, LoginActivity::class.java).apply {
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                })
                activity?.finish()
            }
            binding.dateIcon.setOnClickListener {
                startActivity(Intent(activity, CalendarActivity::class.java))
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
        binding.recyclerIncome.layoutManager = LinearLayoutManager(activity)
        binding.recyclerIncome.adapter = incomeAdapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}