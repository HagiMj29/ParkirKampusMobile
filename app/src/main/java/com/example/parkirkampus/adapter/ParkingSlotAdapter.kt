package com.example.parkirkampus.adapter

import android.database.DataSetObserver
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SpinnerAdapter
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.parkirkampus.R
import com.example.parkirkampus.response.ResultsItem

class ParkingSlotAdapter(val dataSlot: List<ResultsItem?>?) : RecyclerView.Adapter<ParkingSlotAdapter.ViewHolder>(),
    SpinnerAdapter {
    inner class ViewHolder(view : View): RecyclerView.ViewHolder(view) {
        val txtSlot = view.findViewById<TextView>(R.id.idslot)
        val txtStatus = view.findViewById<TextView>(R.id.idstatus)
    }

    interface SlotSelectedListener {
        fun onSlotSelected(slotId: Int)
    }

    var slotSelectedListener: SlotSelectedListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.rvparkingslot,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        if (dataSlot != null){
            return dataSlot.size
        }
        return  0
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.txtSlot.text = dataSlot?.get(position)?.slot
        holder.txtStatus.text = dataSlot?.get(position)?.status

        val currentSlot = dataSlot?.get(position)
        val slotId = currentSlot?.id ?: -1
        holder.txtSlot.text = currentSlot?.slot
        holder.txtStatus.text = currentSlot?.status

        Log.d("SlotID", "ID slot pada posisi $position: $slotId")

        holder.itemView.setOnClickListener {
            currentSlot?.id?.let { slotId ->
                slotSelectedListener?.onSlotSelected(slotId)
            }
        }
    }

    override fun registerDataSetObserver(p0: DataSetObserver?) {

    }

    override fun unregisterDataSetObserver(p0: DataSetObserver?) {

    }

    override fun getCount(): Int {
        return dataSlot?.size ?: 0
    }

    override fun getItem(position: Int): Any? {
        return dataSlot?.get(position)
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = convertView ?: LayoutInflater.from(parent?.context)
            .inflate(R.layout.rvparkingslot, parent, false)

        val txtSlot = view.findViewById<TextView>(R.id.idslot)
        val txtStatus = view.findViewById<TextView>(R.id.idstatus)

        val slotItem = dataSlot?.get(position)
        txtSlot.text = slotItem?.slot
        txtStatus.text = slotItem?.status

        return view
    }

    override fun getViewTypeCount(): Int {
        return 1 // Return the number of view types used by the adapter
    }

    override fun isEmpty(): Boolean {
        return dataSlot.isNullOrEmpty()
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup?): View {
        return getView(position, convertView, parent)
    }
}