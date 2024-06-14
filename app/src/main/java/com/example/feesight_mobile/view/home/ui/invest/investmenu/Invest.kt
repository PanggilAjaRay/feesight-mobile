package com.example.feesight_mobile.view.home.ui.invest.investmenu

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Invest(
    val name: String,
    val percentage: String,
    val photo: Int
): Parcelable
