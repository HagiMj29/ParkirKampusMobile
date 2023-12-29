package com.example.parkirkampus.request



data class ParkingActivitieModel (
    val user_id : Int,
    val slot_id : Int,
    val vehicle_number : String,
    val vehicle_brand : String,
    val in_datetime : String,
    val status : String
        )
