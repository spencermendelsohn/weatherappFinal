package com.example.cs4550weather.ui.dashboard

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cs4550weather.R
import com.example.cs4550weather.data.model.SavedCity

class SavedCitiesAdapter(
    private var cities: List<SavedCity>,
    private val onDeleteClick: (SavedCity) -> Unit,
    private val onCityClick: (SavedCity) -> Unit
) : RecyclerView.Adapter<SavedCitiesAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val cityNameText: TextView = view.findViewById(R.id.cityNameText)
        val temperatureText: TextView = view.findViewById(R.id.temperatureText)
        val humidityText: TextView = view.findViewById(R.id.humidityText)
        val windSpeedText: TextView = view.findViewById(R.id.windSpeedText)
        val deleteButton: ImageButton = view.findViewById(R.id.deleteButton)
        val cardView: View = view
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_saved_city, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val city = cities[position]
        
        holder.cityNameText.text = city.name
        holder.temperatureText.text = city.temperature
        holder.humidityText.text = "Humidity: ${city.humidity}"
        holder.windSpeedText.text = "Wind Speed: ${city.windSpeed}"
        
        // Set up click listener for the entire card
        holder.cardView.setOnClickListener {
            onCityClick(city)
        }
        
        // Set up delete button click listener
        holder.deleteButton.setOnClickListener {
            onDeleteClick(city)
        }
    }

    override fun getItemCount() = cities.size

    fun updateCities(newCities: List<SavedCity>) {
        cities = newCities
        notifyDataSetChanged()
    }
} 