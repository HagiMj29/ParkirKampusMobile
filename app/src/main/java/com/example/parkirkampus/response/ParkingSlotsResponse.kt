package com.example.parkirkampus.response

import com.google.gson.annotations.SerializedName

data class ParkingSlotsResponse(

	@field:SerializedName("results")
	val results: List<ResultsItem?>? = null
)

class ResultsItem(

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("slot")
	val slot: String? = null,

	@field:SerializedName("status")
	val status: String? = null
)
