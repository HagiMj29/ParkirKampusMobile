package com.example.parkirkampus

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.parkirkampus.adapter.AttendancesAdapter
import com.example.parkirkampus.api.ApiConfig
import com.example.parkirkampus.databinding.ActivityAttendancesBinding
import com.example.parkirkampus.response.AttendancesResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AttendancesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAttendancesBinding
    private lateinit var listAttendances : RecyclerView



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAttendancesBinding.inflate(layoutInflater)
        setContentView(binding.root)


        listAttendances = binding.rcattendances

        ApiConfig.getService().getParkingAttendances().enqueue(object : Callback<AttendancesResponse>{
            override fun onResponse(
                call: Call<AttendancesResponse>,
                response: Response<AttendancesResponse>
            ) {
                val apiResponse = response.body()
                val dataAttendance = apiResponse?.results
                val attendancesAdapter = AttendancesAdapter(dataAttendance)

                listAttendances.apply {
                    layoutManager = LinearLayoutManager(this@AttendancesActivity)
                    setHasFixedSize(true)
                    adapter = attendancesAdapter
                }
            }

            override fun onFailure(call: Call<AttendancesResponse>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }
}