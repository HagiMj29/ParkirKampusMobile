package com.example.parkirkampus.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.parkirkampus.R
import com.example.parkirkampus.response.ResultsItem1

class AttendancesAdapter(val dataAttendance : List<ResultsItem1?>?) : RecyclerView.Adapter<AttendancesAdapter.ViewHolder>() {
    class ViewHolder(view : View): RecyclerView.ViewHolder(view) {
        val txtNameAttendances = view.findViewById<TextView>(R.id.txtNameAttendances)
        val imgAttendances= view.findViewById<ImageView>(R.id.imgAttendances)
     }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.rvattendances,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        if(dataAttendance != null){
            return dataAttendance.size
        }
        return  0
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.txtNameAttendances.text = dataAttendance?.get(position)?.name

        Glide.with(holder.imgAttendances)
            .load(dataAttendance?.get(position)?.photo)
            .error(R.drawable.ic_launcher_background)
            .into(holder.imgAttendances)
    }
}