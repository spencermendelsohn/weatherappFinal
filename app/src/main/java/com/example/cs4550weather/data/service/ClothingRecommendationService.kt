package com.example.cs4550weather.data.service

import com.example.cs4550weather.data.model.ClothingRecommendation

class ClothingRecommendationService {
    
    fun getClothingRecommendation(temperature: String, humidity: String, windSpeed: String): ClothingRecommendation {
        // Extract numeric temperature value (remove °C)
        val tempValue = temperature.replace("°C", "").toDoubleOrNull() ?: 0.0
        val humidityValue = humidity.replace("%", "").toIntOrNull() ?: 50
        val windValue = windSpeed.replace(" km/h", "").toDoubleOrNull() ?: 0.0
        
        return when {
            tempValue <= 0 -> {
                ClothingRecommendation(
                    temperature = temperature,
                    recommendations = listOf(
                        "Heavy winter coat or parka",
                        "Warm hat, scarf, and gloves",
                        "Thermal underwear",
                        "Warm socks and boots",
                        "Layered clothing (sweater + jacket)"
                    ),
                    description = "Bundle up! It's very cold outside. Wear multiple layers and protect your extremities."
                )
            }
            tempValue <= 10 -> {
                ClothingRecommendation(
                    temperature = temperature,
                    recommendations = listOf(
                        "Warm jacket or coat",
                        "Light sweater or hoodie",
                        "Light scarf and gloves",
                        "Long pants or jeans",
                        "Closed-toe shoes"
                    ),
                    description = "It's chilly! Wear warm layers and protect yourself from the cold."
                )
            }
            tempValue <= 20 -> {
                ClothingRecommendation(
                    temperature = temperature,
                    recommendations = listOf(
                        "Light jacket or cardigan",
                        "Long pants or jeans",
                        "Comfortable shoes",
                        "Light sweater (optional)",
                        "Light scarf (if windy)"
                    ),
                    description = "Mild weather - comfortable layers work well. Consider a light jacket."
                )
            }
            tempValue <= 25 -> {
                ClothingRecommendation(
                    temperature = temperature,
                    recommendations = listOf(
                        "Light shirt or t-shirt",
                        "Shorts or light pants",
                        "Comfortable shoes",
                        "Light cardigan (for evening)",
                        "Sunglasses and hat"
                    ),
                    description = "Pleasant weather! Light, breathable clothing is perfect."
                )
            }
            tempValue <= 30 -> {
                ClothingRecommendation(
                    temperature = temperature,
                    recommendations = listOf(
                        "Light, breathable shirt",
                        "Shorts or light pants",
                        "Comfortable sandals or shoes",
                        "Sunglasses and hat",
                        "Light, loose-fitting clothing"
                    ),
                    description = "Warm weather! Stay cool with light, breathable fabrics."
                )
            }
            else -> {
                ClothingRecommendation(
                    temperature = temperature,
                    recommendations = listOf(
                        "Shorts and tank top/t-shirt",
                        "Sandals or breathable shoes",
                        "Wide-brimmed hat",
                        "Sunglasses and sunscreen"
                    ),
                    description = "Hot weather! Stay cool and protect yourself from the sun."
                )
            }
        }.let { baseRecommendation ->
            // Add wind-specific recommendations
            val windRecommendations = if (windValue > 20) {
                listOf("Windbreaker or jacket", "Secure hat")
            } else {
                emptyList()
            }
            
            // Add humidity-specific recommendations
            val humidityRecommendations = when {
                humidityValue > 80 -> listOf("Light, moisture-wicking fabrics", "Quick-dry clothing")
                humidityValue < 30 -> listOf("Moisturizer for skin", "Lip balm")
                else -> emptyList()
            }
            
            baseRecommendation.copy(
                recommendations = baseRecommendation.recommendations + windRecommendations + humidityRecommendations
            )
        }
    }
} 