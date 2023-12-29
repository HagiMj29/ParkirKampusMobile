package com.example.parkirkampus

import android.content.Intent
import android.icu.text.SimpleDateFormat
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.parkirkampus.adapter.SpinnerParkingSlotsAdapter
import com.example.parkirkampus.api.ApiConfig
import com.example.parkirkampus.databinding.ActivityParkingActivitieBinding
import com.example.parkirkampus.request.ParkingActivitieModel
import com.example.parkirkampus.response.ParkingSlotsResponse
import com.example.parkirkampus.response.ResultsItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*


class ParkingActivitieActivity : AppCompatActivity() {

    private lateinit var binding: ActivityParkingActivitieBinding
    private lateinit var spinnerParkingSlotsAdapter: SpinnerParkingSlotsAdapter
    private var selectedSlotId: Int = -1


    val brands = arrayOf("Honda","Suzuki","Yamaha","Lainnya")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityParkingActivitieBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val currentDateTime = Calendar.getInstance().time
        val formattedDateTime = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(currentDateTime)
        val editableFormattedDateTime = Editable.Factory.getInstance().newEditable(formattedDateTime)
        binding.edInDatetime.text = editableFormattedDateTime
        
        val spinner_brand =binding.vehicleBrand
        val arrayAdapter = ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, brands)
        var selectedVehicleBrand: String = ""

        spinner_brand.adapter = arrayAdapter
        spinner_brand.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                selectedVehicleBrand = brands[p2]
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

        }

        val userId = intent.getIntExtra("id", -1)





        binding.edSlotId.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedSlot = spinnerParkingSlotsAdapter.getItem(position) as ResultsItem?
                if (selectedSlot != null) {
                    // Mengisi selectedSlotId dengan ID slot yang dipilih
                    this@ParkingActivitieActivity.selectedSlotId = selectedSlot.id ?: -1
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Do nothing
            }
        }

        binding.btnBooking.setOnClickListener {
            val vehicleNumber = binding.edVehicleNumber.text.toString()
            val vehicleBrand = binding.vehicleBrand.selectedItem?.toString() ?: ""
            val inDatetime = binding.edInDatetime.text.toString()

            if (selectedSlotId != -1) {
                val parkingActivityData = ParkingActivitieModel(
                    userId,
                    selectedSlotId,
                    vehicleNumber,
                    vehicleBrand,
                    inDatetime,
                    "Masuk"
                )

                ApiConfig.getService().dataParkingActivitie(parkingActivityData)
                    .enqueue(object : Callback<ParkingActivitieModel> {
                        override fun onResponse(call: Call<ParkingActivitieModel>, response: Response<ParkingActivitieModel>) {
                            if (response.isSuccessful) {
                                // Menampilkan toast berhasil
                                Toast.makeText(
                                    this@ParkingActivitieActivity,
                                    "Booking berhasil!",
                                    Toast.LENGTH_SHORT
                                ).show()

                                Log.d("Booking", "Booking berhasil dengan data: $parkingActivityData")
                            } else {

                                Toast.makeText(
                                    this@ParkingActivitieActivity,
                                    "Gagal melakukan booking!",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }

                        override fun onFailure(call: Call<ParkingActivitieModel>, t: Throwable) {
                            Toast.makeText(
                                this@ParkingActivitieActivity,
                                "Booking Gagal. Periksa Jaringan atau hubungi admin!",
                                Toast.LENGTH_SHORT
                            ).show()
                            Log.e("Booking", "Gagal melakukan booking", t)
                        }
                    })
            } else {
                Toast.makeText(
                    this@ParkingActivitieActivity,
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
                    binding.edSlotId.adapter = spinnerParkingSlotsAdapter
                } else {

                }
            }

            override fun onFailure(call: Call<ParkingSlotsResponse>, t: Throwable) {

            }
        })
    }


}