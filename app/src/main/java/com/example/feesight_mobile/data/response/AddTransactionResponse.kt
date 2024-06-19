package com.example.feesight_mobile.data.response

import com.google.gson.annotations.SerializedName


data class TransactionRequest(
	val amount: Int,
	val type: String,
	val category: String,
	val date: String
)

data class AddTransactionResponse(

	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("message")
	val message: String
)
