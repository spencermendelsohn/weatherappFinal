package com.example.cs4550weather.ui.dashboard

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cs4550weather.R
import com.example.cs4550weather.data.service.ClothingRecommendationService
import com.example.cs4550weather.databinding.FragmentDashboardBinding
import com.example.cs4550weather.ui.dashboard.SavedCitiesAdapter

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: SavedCitiesAdapter
    private val clothingService = ClothingRecommendationService()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val dashboardViewModel = ViewModelProvider(this).get(DashboardViewModel::class.java)

        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // Initialize the repository
        dashboardViewModel.initializeRepository(requireContext())

        // Set up RecyclerView
        adapter = SavedCitiesAdapter(
            cities = emptyList(),
            onDeleteClick = { city ->
                dashboardViewModel.removeCity(city)
            },
            onCityClick = { city ->
                showClothingRecommendationDialog(city)
            }
        )

        binding.savedCitiesRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = this@DashboardFragment.adapter
        }

        // Observe saved cities changes
        dashboardViewModel.savedCities.observe(viewLifecycleOwner) { cities ->
            adapter.updateCities(cities)
            updateEmptyState(cities.isEmpty())
        }

        return root
    }

    private fun showClothingRecommendationDialog(city: com.example.cs4550weather.data.model.SavedCity) {
        val recommendation = clothingService.getClothingRecommendation(
            city.temperature,
            city.humidity,
            city.windSpeed
        )

        val dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_clothing_recommendation, null)
        
        // Set up dialog content
        dialogView.findViewById<TextView>(R.id.cityNameText).text = city.name
        dialogView.findViewById<TextView>(R.id.weatherInfoText).text = 
            "${city.temperature} • ${city.humidity} • ${city.windSpeed}"
        dialogView.findViewById<TextView>(R.id.descriptionText).text = recommendation.description

        // Add recommendations to the container
        val recommendationsContainer = dialogView.findViewById<LinearLayout>(R.id.recommendationsContainer)
        recommendation.recommendations.forEach { item ->
            val textView = TextView(context).apply {
                text = "• $item"
                textSize = 14f
                setPadding(0, 8, 0, 8)
            }
            recommendationsContainer.addView(textView)
        }

        // Create dialog
        val dialog = AlertDialog.Builder(requireContext())
            .setView(dialogView)
            .setCancelable(true)
            .create()

        // Set up close button to dismiss the dialog
        dialogView.findViewById<Button>(R.id.closeButton).setOnClickListener {
            dialog.dismiss()
        }

        // Show dialog
        dialog.show()
    }

    private fun updateEmptyState(isEmpty: Boolean) {
        if (isEmpty) {
            binding.emptyStateText.visibility = View.VISIBLE
            binding.savedCitiesRecyclerView.visibility = View.GONE
        } else {
            binding.emptyStateText.visibility = View.GONE
            binding.savedCitiesRecyclerView.visibility = View.VISIBLE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}