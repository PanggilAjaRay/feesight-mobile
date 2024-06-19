package com.example.feesight_mobile.data.response

import com.google.gson.annotations.SerializedName

typealias TransactionsResponse = List<TransactionsResponseItem>

data class TransactionsResponseItem(

	@field:SerializedName("date")
	val date: String,

	@field:SerializedName("amount")
	val amount: Int,

	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("type")
	val type: String,

	@field:SerializedName("category")
	val category: String
)
