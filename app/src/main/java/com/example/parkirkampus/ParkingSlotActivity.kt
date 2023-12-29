package com.example.parkirkampus

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.parkirkampus.adapter.ParkingSlotAdapter
import com.example.parkirkampus.api.ApiConfig
import com.example.parkirkampus.databinding.ActivityParkingSlotBinding
import com.example.parkirkampus.response.ParkingSlotsResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ParkingSlotActivity : AppCompatActivity() {

    private lateinit var binding: ActivityParkingSlotBinding
    private lateinit var listSlot: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityParkingSlotBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        listSlot = binding.listslots

        ApiConfig.getService().getParkingSlots().enqueue(object : Callback<ParkingSlotsResponse> {
            override fun onResponse(
                call: Call<ParkingSlotsResponse>,
                response: Response<ParkingSlotsResponse>
            ) {
                if(response.isSuccessful){
                    val apiResponse = response.body()
                    val dataSlots = apiResponse?.results
                    val parkingSlotAdapter = ParkingSlotAdapter(dataSlots)

                    parkingSlotAdapter.slotSelectedListener = object : ParkingSlotAdapter.SlotSelectedListener {
                        override fun onSlotSelected(slotId: Int) {
                            val intent = Intent(this@ParkingSlotActivity, ParkingActivitieActivity::class.java)
                            intent.putExtra("selectedSlotId", slotId)
                            startActivity(intent)
                        }
                    }

                    listSlot.apply {
                        layoutManager =LinearLayoutManager(this@ParkingSlotActivity)
                        setHasFixedSize(true)
                        adapter = parkingSlotAdapter
                    }
                }
            }

            override fun onFailure(call: Call<ParkingSlotsResponse>, t: Throwable) {
                Toast.makeText(applicationContext, t.localizedMessage, Toast.LENGTH_SHORT).show()
            }

        })
    }

}



