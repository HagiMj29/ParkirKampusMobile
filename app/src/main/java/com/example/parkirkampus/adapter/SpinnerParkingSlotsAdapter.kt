package com.example.parkirkampus.adapter

import android.database.DataSetObserver
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Spinner
import android.widget.SpinnerAdapter
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.parkirkampus.R
import com.example.parkirkampus.response.ResultsItem

class SpinnerParkingSlotsAdapter(val spinnerSlot : List<ResultsItem?>?):RecyclerView.Adapter<SpinnerParkingSlotsAdapter.ViewHolder>(),
SpinnerAdapter{
    class ViewHolder(view :View): RecyclerView.ViewHolder(view) {
        val txtSlot = view.findViewById<TextView>(R.id.idslot)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.rvparkingslot2,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        if (spinnerSlot != null){
            return spinnerSlot.size
        }
        return  0
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.txtSlot.text = spinnerSlot?.get(position)?.slot




    }

    override fun registerDataSetObserver(p0: DataSetObserver?) {

    }

    override fun unregisterDataSetObserver(p0: DataSetObserver?) {

    }

    override fun getCount(): Int {
        return spinnerSlot?.size ?: 0
    }

    override fun getItem(position: Int): Any? {
        return spinnerSlot?.get(position)
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = convertView ?: LayoutInflater.from(parent?.context)
            .inflate(android.R.layout.simple_spinner_item, parent, false)

        val txtSlot = view.findViewById<TextView>(android.R.id.text1)

        val slotItem = spinnerSlot?.get(position)
        txtSlot.text = slotItem?.slot

        return view
    }

    override fun getViewTypeCount(): Int {
        return 1
    }

    override fun isEmpty(): Boolean {
        return spinnerSlot.isNullOrEmpty()
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup?): View {
        return getView(position, convertView, parent)
    }

}