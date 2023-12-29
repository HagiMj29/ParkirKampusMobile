package com.example.parkirkampus.api

import com.example.parkirkampus.request.*
import com.example.parkirkampus.response.*
import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface ApiServices {
    @POST("login")
    fun datalogin(@Body loginModel: LoginModel): Call<LoginResponse>

    @POST("register")
    fun dataRegister(@Body registerModel: RegisterModel): Call<RegisterModel>

    @GET("parkingslots")
    fun getParkingSlots(): Call<ParkingSlotsResponse>

    @GET("users")
    fun getUsers(@Query("id") userId: Int): Call<UsersResponse>

    @PUT("users/{id}")
    fun getEdit(@Path("id") id: Int, @Body editUsersModel: EditUsersModel): Call<EditUsersModel>

    @GET("parkingattendances")
    fun getParkingAttendances(): Call<AttendancesResponse>

    @POST("parkingactivities")
    fun dataParkingActivitie(@Body parkingActivityData: ParkingActivitieModel): Call<ParkingActivitieModel>

    @GET("parkingactivities")
    fun getParkingActivities(): Call<ParkingActivitieResponse>

    @POST("parkingcomplaints")
    fun dataParkingComplaints(@Body parkingComplaintsModel: ParkingComplaintsModel): Call<ParkingComplaintsModel>


}