package com.example.parkirkampus.response

import com.google.gson.annotations.SerializedName

 data class ParkingActivitieResponse(

	@field:SerializedName("results")
	val results: List<ResultsItem2?>? = null
)

 data class ResultsItem2(

	 @field:SerializedName("vehicle_brand")
	 val vehicleBrand: String? = null,

	 @field:SerializedName("user_id")
	 val userId: Int? = null,

	 @field:SerializedName("slot_id")
	 val slotId: Int? = null,

	 @field:SerializedName("userName")
	 val userName: String? = null,

	 @field:SerializedName("slotNumber")
	 val slotNumber: String? = null,

	 @field:SerializedName("vehicle_number")
	 val vehicleNumber: String? = null,

	 @field:SerializedName("in_datetime")
	 val inDatetime: String? = null,

	 @field:SerializedName("out_datetime")
	 val outDatetime: String? = null,

	 @field:SerializedName("status")
	 val status: String? = null
)
