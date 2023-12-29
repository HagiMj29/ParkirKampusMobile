package com.example.parkirkampus.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.parkirkampus.R
import com.example.parkirkampus.response.ResultsItem2

class ParkingHistoryAdapter(val dataParkingHistory : List<ResultsItem2?>?): RecyclerView.Adapter<ParkingHistoryAdapter.ViewHolder>() {
    class ViewHolder(view: View):RecyclerView.ViewHolder(view) {
        val historyName = view.findViewById<TextView>(R.id.txtNameHistory)
        val slotHistory = view.findViewById<TextView>(R.id.txtSlotHistory)
        val vehicleNumHistory = view.findViewById<TextView>(R.id.txtVehicleNumHistory)
        val statusHistory = view.findViewById<TextView>(R.id.txtStatusHistory)
        val vehicleBrandHistory = view.findViewById<TextView>(R.id.txtVehicleBrandHistory)
        val inDatetimeHistory = view.findViewById<TextView>(R.id.txtInDatetimeHistory)
        val outDatetimeHistory = view.findViewById<TextView>(R.id.txtOutDatetimeHistory)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.rvparkinghistory,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        if (dataParkingHistory != null){
            return dataParkingHistory.size
        }
        return  0
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.historyName.text = dataParkingHistory?.get(position)?.userName ?: "Nama Tidak Ditemukan"
        holder.slotHistory.text = dataParkingHistory?.get(position)?.slotNumber ?: "Slot Tidak Ditemukan"
        holder.vehicleNumHistory.text = dataParkingHistory?.get(position)?.vehicleNumber
        holder.statusHistory.text = dataParkingHistory?.get(position)?.status
        holder.vehicleBrandHistory.text = dataParkingHistory?.get(position)?.vehicleBrand
        holder.inDatetimeHistory.text = dataParkingHistory?.get(position)?.inDatetime
        holder.outDatetimeHistory.text = dataParkingHistory?.get(position)?.outDatetime
    }
}