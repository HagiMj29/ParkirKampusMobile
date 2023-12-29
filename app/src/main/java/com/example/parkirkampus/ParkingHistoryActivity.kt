package com.example.parkirkampus

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.parkirkampus.adapter.ParkingHistoryAdapter
import com.example.parkirkampus.api.ApiConfig
import com.example.parkirkampus.databinding.ActivityParkingHistoryBinding
import com.example.parkirkampus.response.ParkingActivitieResponse
import com.example.parkirkampus.response.ParkingSlotsResponse
import com.example.parkirkampus.response.UsersResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ParkingHistoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityParkingHistoryBinding
    private  lateinit var parkingHistory : RecyclerView
    private var usersResponse: UsersResponse? = null
    private var slotsResponse: ParkingSlotsResponse? = null
    private var userId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityParkingHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sharedPreferences = getSharedPreferences("user_data", MODE_PRIVATE)
        userId = sharedPreferences.getInt("user_id", -1)

        parkingHistory = binding.rchistoryparking

        ApiConfig.getService().getUsers(userId).enqueue(object : Callback<UsersResponse> {
            override fun onResponse(call: Call<UsersResponse>, response: Response<UsersResponse>) {
                if (response.isSuccessful) {
                    usersResponse = response.body()
                    fetchParkingSlots()
                }
            }

            override fun onFailure(call: Call<UsersResponse>, t: Throwable) {
                // Handle kesalahan jika diperlukan
            }
        })



    }

    private fun fetchParkingSlots() {
        ApiConfig.getService().getParkingSlots().enqueue(object : Callback<ParkingSlotsResponse> {
            override fun onResponse(
                call: Call<ParkingSlotsResponse>,
                response: Response<ParkingSlotsResponse>
            ) {
                if (response.isSuccessful) {
                    slotsResponse = response.body()
                    fetchParkingActivities()
                }
            }

            override fun onFailure(call: Call<ParkingSlotsResponse>, t: Throwable) {
                Toast.makeText(this@ParkingHistoryActivity, "Gagal memuat data", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun fetchParkingActivities() {
        ApiConfig.getService().getParkingActivities().enqueue(object : Callback<ParkingActivitieResponse> {
            override fun onResponse(
                call: Call<ParkingActivitieResponse>,
                response: Response<ParkingActivitieResponse>
            ) {
                if (response.isSuccessful) {
                    val apiResponse = response.body()
                    val dataParkingHistory = apiResponse?.results

                    val filteredData = dataParkingHistory?.filter { it?.userId == userId }

                    val modifiedData = filteredData?.map { parkingActivity ->
                        val userName = getUserById(usersResponse, parkingActivity?.userId)
                        val slotNumber = getSlotById(slotsResponse, parkingActivity?.slotId)
                        parkingActivity?.copy(userName = userName, slotNumber = slotNumber)
                    }

                    val parkingHistoryAdapter = ParkingHistoryAdapter(modifiedData)

                    parkingHistory.apply {
                        layoutManager = LinearLayoutManager(this@ParkingHistoryActivity)
                        setHasFixedSize(true)
                        adapter = parkingHistoryAdapter
                    }
                }
            }

            override fun onFailure(call: Call<ParkingActivitieResponse>, t: Throwable) {
                Toast.makeText(this@ParkingHistoryActivity, "Gagal memuat data", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun getUserById(usersResponse: UsersResponse?, userId: Int?): String {
        val user = usersResponse?.results?.find { it?.id == userId }
        return user?.name ?: "Nama Tidak Ditemukan"
    }

    private fun getSlotById(slotsResponse: ParkingSlotsResponse?, slotId: Int?): String {
        val slot = slotsResponse?.results?.find { it?.id == slotId }
        return slot?.slot ?: "Slot Tidak Ditemukan"
    }
}