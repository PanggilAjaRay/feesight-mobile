package com.example.feesight_mobile.view.home.ui.invest

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.example.feesight_mobile.R
import com.example.feesight_mobile.view.home.HomeActivity
import com.example.feesight_mobile.view.home.ui.invest.investmenu.ListCrypto
import com.example.feesight_mobile.view.home.ui.invest.investmenu.ListInvest


class InvestFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_invest, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val imageView: ImageView = view.findViewById(R.id.rectangle_4)
        imageView.setOnClickListener {
            val intent = Intent(activity, ListCrypto::class.java)
            startActivity(intent)
        }
        val imageViewCrypto: ImageView = view.findViewById(R.id.rectangle_3)
        imageViewCrypto.setOnClickListener {
            val intent = Intent(activity, ListInvest::class.java)
            startActivity(intent)
        }
    }
}