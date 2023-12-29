package com.example.parkirkampus.response

import com.google.gson.annotations.SerializedName

data class AttendancesResponse(

	@field:SerializedName("results")
	val results: List<ResultsItem1?>? = null
)

data class ResultsItem1(

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("photo")
	val photo: String? = null,

	@field:SerializedName("id")
	val id: Int? = null
)
