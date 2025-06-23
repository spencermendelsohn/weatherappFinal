package com.example.cs4550weather.data.model

import com.google.gson.annotations.SerializedName

data class WeatherResponse(
    val latitude: Double,
    val longitude: Double,
    val generationtime_ms: Double,
    val utc_offset_seconds: Int,
    val timezone: String,
    val timezone_abbreviation: String,
    val elevation: Double,
    val current_units: CurrentUnits,
    val current: Current,
    val hourly_units: HourlyUnits,
    val hourly: Hourly
)

data class CurrentUnits(
    val time: String,
    val interval: String,
    val temperature_2m: String,
    val relative_humidity_2m: String,
    val wind_speed_10m: String
)

data class Current(
    val time: String,
    val interval: Int,
    val temperature_2m: Double,
    val relative_humidity_2m: Int,
    val wind_speed_10m: Double,
    val weather_code: Int,
    val uv_index: Double
)

data class HourlyUnits(
    val time: String,
    val temperature_2m: String,
    val relative_humidity_2m: String,
    val wind_speed_10m: String
)

data class Hourly(
    val time: List<String>,
    val temperature_2m: List<Double>,
    val relative_humidity_2m: List<Int>,
    val wind_speed_10m: List<Double>,
    val precipitation_probability: List<Int>
)

data class GeocodingResponse(
    val results: List<GeocodingResult>
)

data class GeocodingResult(
    val id: Int,
    val name: String,
    val latitude: Double,
    val longitude: Double,
    val elevation: Double,
    val feature_code: String,
    val country_code: String,
    val admin1_id: Int,
    val admin2_id: Int,
    val admin3_id: Int,
    val admin4_id: Int,
    val timezone: String,
    val population: Int,
    val postcodes: List<String>,
    val country_id: Int,
    val country: String,
    val admin1: String,
    val admin2: String,
    val admin3: String,
    val admin4: String
) 