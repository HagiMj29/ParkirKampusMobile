package com.example.parkirkampus.response

import com.google.gson.annotations.SerializedName

 data class UsersResponse(

	@field:SerializedName("results")
	val results: List<ResultsItem3?>? = null
)

 class ResultsItem3(

	@field:SerializedName("password")
	val password: String? = null,

	@field:SerializedName("role")
	val role: String? = null,

	@field:SerializedName("phone")
	val phone: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("photo")
	val photo: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("email")
	val email: String? = null
)
