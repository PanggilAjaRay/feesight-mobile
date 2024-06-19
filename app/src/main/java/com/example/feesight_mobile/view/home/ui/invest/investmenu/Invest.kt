package com.example.feesight_mobile.view.home.ui.invest.investmenu

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Invest(
    val name: String,
    val price: String,
    val photo: Int
): Parcelable
