package com.example.feesight_mobile.view.transaction.expenseincome

import android.app.DatePickerDialog
import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import com.example.feesight_mobile.R
import com.example.feesight_mobile.data.response.AddTransactionResponse
import com.example.feesight_mobile.data.response.TransactionRequest
import com.example.feesight_mobile.data.retrofit.ApiConfig
import com.example.feesight_mobile.databinding.FragmentExpenseBinding
import com.example.feesight_mobile.view.transaction.CalendarActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.Calendar

class ExpenseFragment : Fragment() {

    private var _binding: FragmentExpenseBinding? = null
    private val binding get() = _binding!!

    private var selectedCategory: String? = null
    private var selectedImageView: ImageView? = null
    private var selectedTextView: TextView? = null
    private var selectedDate: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentExpenseBinding.inflate(inflater, container, false)
        val view = binding.root

        // Handle ImageView click to navigate to IncomeFragment
        binding.imageView2.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, IncomeFragment())
                .addToBackStack(null)
                .commit()
        }

        // Handle TextInputEditText click to show DatePickerDialog
        binding.dateEditText.setOnClickListener {

            showDatePickerDialog()


        }

        // Set OnClickListener for each category ImageView
        val categories = listOf(
            Pair(binding.ivMeal, binding.tvCategorize1),
            Pair(binding.ivShopping, binding.tvCategorize2),
            Pair(binding.ivTravel, binding.tvCategorize3),
            Pair(binding.ivHealth, binding.tvCategorize7),
            Pair(binding.ivFashion, binding.tvCategorize8),
            Pair(binding.ivExpenseOther, binding.tvCategorize9)
        )

        for (category in categories) {
            category.first.setOnClickListener {
                onCategorySelected(category.first, category.second)
            }
        }

        // Set OnClickListener for sendButton
        binding.btnExpense.setOnClickListener {
            Toast.makeText(requireContext(), "Successful expense money added!", Toast.LENGTH_SHORT).show()
            val intent = Intent(requireActivity(), CalendarActivity::class.java)
            startActivity(intent)
            // Akhiri Fragment saat ini agar tidak kembali ke halaman sebelumnya setelah Intent ke MainActivity
            requireActivity().finish()
            sendDataToApi()
        }

        return view
    }

    private fun showDatePickerDialog() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            requireContext(),
            { _, selectedYear, selectedMonth, selectedDay ->
                // Format the selected date and set it to the EditText
                val formattedDate = "${selectedYear}-${selectedMonth + 1}-${selectedDay}"
                binding.dateEditText.setText(formattedDate)
                selectedDate = formattedDate
            },
            year, month, day
        )
        datePickerDialog.show()
    }

    private fun onCategorySelected(imageView: ImageView, textView: TextView) {
        // Reset the previous selected ImageView and TextView
        selectedImageView?.setBackgroundResource(0)
        selectedTextView?.setTypeface(null, Typeface.NORMAL)

        // Set the new selected ImageView and TextView
        selectedImageView = imageView
        selectedTextView = textView
        selectedTextView?.setTypeface(null, Typeface.BOLD)

        // Set the selected category
        selectedCategory = when (imageView.id) {
            R.id.iv_meal -> "Meal"
            R.id.iv_shopping -> "Shopping"
            R.id.iv_travel -> "Travel"
            R.id.iv_health -> "Health"
            R.id.iv_fashion -> "Fashion"
            R.id.iv_expense_other -> "Other"
            else -> null
        }
        val typeface = ResourcesCompat.getFont(requireContext(), R.font.poppins_semibold)
        selectedTextView?.typeface = typeface

        // Show a Toast message
        Toast.makeText(requireContext(), "Selected: $selectedCategory", Toast.LENGTH_SHORT).show()
    }

    private fun sendDataToApi() {
        val amount = binding.totalEditText.text.toString().toIntOrNull()
        val type = "expense"  // Menetapkan tipe transaksi sebagai "expense"
        val category = selectedCategory // Menggunakan selectedCategory atau default "Other" jika null
        val date = selectedDate

        if (amount == null || date == null || category == null) {
            Toast.makeText(requireContext(), "Data yang dimasukkan masih kosong !", Toast.LENGTH_SHORT).show()
            return
        }

        val transaction = TransactionRequest(
            amount = amount,
            type = type,
            category = category,
            date = date
        )

        ApiConfig.getApiService().createTransaction(transaction).enqueue(object : Callback<AddTransactionResponse> {
            override fun onResponse(call: Call<AddTransactionResponse>, response: Response<AddTransactionResponse>) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        val logMessage = "Transaction performed: ${it.message}, ID: ${it.id}"
                        Log.d("TransactionLog", logMessage) // Menyimpan log dengan level DEBUG
                    }
                } else {
                    Log.e(
                        "TransactionLog",
                        "Failed to perform transaction"
                    ) // Jika respons tidak berhasil
                }
            }
                override fun onFailure(call: Call<AddTransactionResponse>, t: Throwable) {
                Toast.makeText(requireContext(), "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}