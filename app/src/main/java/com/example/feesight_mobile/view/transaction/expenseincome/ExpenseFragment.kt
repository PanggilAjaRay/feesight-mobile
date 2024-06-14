package com.example.feesight_mobile.view.transaction.expenseincome

import android.app.DatePickerDialog
import android.graphics.Typeface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import com.example.feesight_mobile.R
import com.google.android.material.textfield.TextInputEditText
import java.util.Calendar

class ExpenseFragment : Fragment() {

    private var selectedCategory: String? = null
    private var selectedImageView: ImageView? = null
    private var selectedTextView: TextView? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_expense, container, false)

        // Handle ImageView click to navigate to IncomeFragment
        val imageView2 = view.findViewById<ImageView>(R.id.imageView2)
        imageView2.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, IncomeFragment())
                .addToBackStack(null)
                .commit()
        }

        // Handle TextInputEditText click to show DatePickerDialog
        val dateEditText = view.findViewById<TextInputEditText>(R.id.dateEditText)
        dateEditText.setOnClickListener {
            showDatePickerDialog(dateEditText)
        }

        // Set OnClickListener for each category ImageView
        val categories = listOf(
            Pair(view.findViewById<ImageView>(R.id.iv_meal), view.findViewById<TextView>(R.id.tv_categorize_1)),
            Pair(view.findViewById<ImageView>(R.id.iv_shopping), view.findViewById<TextView>(R.id.tv_categorize_2)),
            Pair(view.findViewById<ImageView>(R.id.iv_travel), view.findViewById<TextView>(R.id.tv_categorize_3)),
            Pair(view.findViewById<ImageView>(R.id.iv_health), view.findViewById<TextView>(R.id.tv_categorize_7)),
            Pair(view.findViewById<ImageView>(R.id.iv_fashion), view.findViewById<TextView>(R.id.tv_categorize_8)),
            Pair(view.findViewById<ImageView>(R.id.iv_expense_other), view.findViewById<TextView>(R.id.tv_categorize_9))
        )

        for (category in categories) {
            category.first.setOnClickListener {
                onCategorySelected(category.first, category.second)
            }
        }

        return view
    }

    private fun showDatePickerDialog(dateEditText: TextInputEditText) {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            requireContext(),
            { _, selectedYear, selectedMonth, selectedDay ->
                // Format the selected date and set it to the EditText
                val formattedDate = "${selectedDay}/${selectedMonth + 1}/${selectedYear}"
                dateEditText.setText(formattedDate)
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
}