package com.example.feesight_mobile.data.response

import com.google.gson.annotations.SerializedName

data class InvestResponse(
	@field:SerializedName("SOL-USD")
	val sOLUSD: Any,

	@field:SerializedName("BNB-USD")
	val bNBUSD: Any,

	@field:SerializedName("LINK-USD")
	val lINKUSD: Any,

	@field:SerializedName("ETH-USD")
	val eTHUSD: Any,

	@field:SerializedName("TLKM.JK")
	val tLKMJK: Any,

	@field:SerializedName("INDF.JK")
	val iNDFJK: Any,

	@field:SerializedName("BBCA.JK")
	val bBCAJK: Any,

	@field:SerializedName("BBRI.JK")
	val bBRIJK: Any,

	@field:SerializedName("NEAR-USD")
	val nEARUSD: Any,

	@field:SerializedName("AMZN")
	val aMZN: Any
)


