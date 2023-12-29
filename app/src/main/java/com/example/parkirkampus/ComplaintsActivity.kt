package com.example.parkirkampus

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import com.example.parkirkampus.adapter.SpinnerParkingSlotsAdapter
import com.example.parkirkampus.api.ApiConfig
import com.example.parkirkampus.databinding.ActivityComplaintsBinding
import com.example.parkirkampus.request.ParkingActivitieModel
import com.example.parkirkampus.request.ParkingComplaintsModel
import com.example.parkirkampus.response.ParkingSlotsResponse
import com.example.parkirkampus.response.ResultsItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ComplaintsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityComplaintsBinding
    private lateinit var spinnerParkingSlotsAdapter: SpinnerParkingSlotsAdapter
    private var selectedSlotId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityComplaintsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val userId = intent.getIntExtra("id", -1)

        binding.edSlotIdComplaints.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedSlot = spinnerParkingSlotsAdapter.getItem(position) as ResultsItem?
                if (selectedSlot != null) {
                    selectedSlotId = selectedSlot.id ?: -1
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                Toast.makeText(this@ComplaintsActivity, "Pilih Slot terlebih dahulu", Toast.LENGTH_SHORT).show()
            }
        }


        binding.btnSubmitComplaints.setOnClickListener {
            val Desc = binding.edDescriptionComplaints.text.toString()

            if (selectedSlotId != -1) {
                val parkingComplaintsModel = ParkingComplaintsModel(
                    userId,
                    selectedSlotId,
                    Desc,
                    "",
                    "Diproses"
                )

                ApiConfig.getService().dataParkingComplaints(parkingComplaintsModel)
                    .enqueue(object : Callback<ParkingComplaintsModel> {
                        override fun onResponse(call: Call<ParkingComplaintsModel>, response: Response<ParkingComplaintsModel>) {
                            if (response.isSuccessful) {
                                Toast.makeText(
                                    this@ComplaintsActivity,
                                    "Aduan berhasil Terkirim!",
                                    Toast.LENGTH_SHORT
                                ).show()

                                Log.d("Booking", "Aduan berhasil dengan data: $parkingComplaintsModel")
                            } else {

                                Toast.makeText(
                                    this@ComplaintsActivity,
                                    "Gagal melakukan Aduan!",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }

                        override fun onFailure(call: Call<ParkingComplaintsModel>, t: Throwable) {

                            Log.e("Booking", "Gagal melakukan booking", t)
                        }
                    })
            } else {
                Toast.makeText(
                    this@ComplaintsActivity,
                    "Pilih slot terlebih dahulu!",
                    Toast.LENGTH_SHORT
                ).show()
            }

        }

        loadParkingSlots()
    }
    private fun loadParkingSlots() {
        ApiConfig.getService().getParkingSlots().enqueue(object : Callback<ParkingSlotsResponse> {
            override fun onResponse(
                call: Call<ParkingSlotsResponse>,
                response: Response<ParkingSlotsResponse>
            ) {
                if (response.isSuccessful) {
                    val parkingSlots = response.body()?.results ?: emptyList()
                    spinnerParkingSlotsAdapter = SpinnerParkingSlotsAdapter(parkingSlots)
                    binding.edSlotIdComplaints.adapter = spinnerParkingSlotsAdapter
                } else {
                    Toast.makeText(this@ComplaintsActivity, "Gagal memuat data", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ParkingSlotsResponse>, t: Throwable) {

            }
        })
    }
}
