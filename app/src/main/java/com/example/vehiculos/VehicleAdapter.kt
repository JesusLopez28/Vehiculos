package com.example.vehiculos

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class VehicleAdapter(private val vehicles: List<Vehicle>) :
    RecyclerView.Adapter<VehicleAdapter.VehicleViewHolder>() {

    class VehicleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val brandTextView: TextView = itemView.findViewById(R.id.brandTextView)
        val modelTextView: TextView = itemView.findViewById(R.id.modelTextView)
        val yearTextView: TextView = itemView.findViewById(R.id.yearTextView)
        val priceTextView: TextView = itemView.findViewById(R.id.priceTextView)
        val descriptionTextView: TextView = itemView.findViewById(R.id.descriptionTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VehicleViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_vehicle, parent, false)
        return VehicleViewHolder(view)
    }

    override fun onBindViewHolder(holder: VehicleViewHolder, position: Int) {
        val vehicle = vehicles[position]
        holder.brandTextView.text = "Marca: ${vehicle.marca}"
        holder.modelTextView.text = "Modelo: ${vehicle.modelo}"
        holder.yearTextView.text = "Año: ${vehicle.año}"
        holder.priceTextView.text = "Precio: $${vehicle.precio}"
        holder.descriptionTextView.text = "Descripción: ${vehicle.descripcion}"
    }

    override fun getItemCount(): Int = vehicles.size
}
