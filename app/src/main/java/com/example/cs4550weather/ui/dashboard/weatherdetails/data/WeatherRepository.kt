package com.example.cs4550weather.ui.dashboard.weatherdetails.data

import com.example.cs4550weather.data.model.Current
import com.example.cs4550weather.data.model.CurrentUnits
import com.example.cs4550weather.data.model.Hourly
import com.example.cs4550weather.data.model.HourlyUnits
import com.example.cs4550weather.data.model.WeatherResponse
import com.example.cs4550weather.data.repository.WeatherAppRepository

class WeatherRepository(
    private val apiRepository: WeatherAppRepository,
    private val weatherDao: WeatherDao
) {
    suspend fun getWeatherByCity(cityName: String): Result<WeatherResponse> {
        return try {
            val result = apiRepository.getWeatherByCity(cityName)

            result.onSuccess { response ->
                val entity = WeatherEntity(
                    cityName = cityName,
                    temperature = "${response.current.temperature_2m}°C",
                    humidity = "${response.current.relative_humidity_2m}%",
                    windSpeed = "${response.current.wind_speed_10m} km/h",
                    weatherCondition = getWeatherCondition(response.current.weather_code),
                    rainToday = "${response.hourly.precipitation_probability.firstOrNull() ?: 0}%",
                    uvIndex = "${response.current.uv_index}"
                )
                weatherDao.insertWeather(entity)
            }
            result
        } catch (e: Exception) {
            weatherDao.getCity(cityName)?.let {
                Result.success(it.toWeatherResponse())
            } ?: Result.failure(e)
        }
    }

    suspend fun getSavedWeather(cityName: String): WeatherEntity? {
        return weatherDao.getCity(cityName)
    }

    private fun WeatherEntity.toWeatherResponse(): WeatherResponse {
        return WeatherResponse(
            latitude = 0.0,
            longitude = 0.0,
            generationtime_ms = 0.0,
            utc_offset_seconds = 0,
            timezone = "",
            timezone_abbreviation = "",
            elevation = 0.0,
            current_units = CurrentUnits("", "", "", "", ""),
            current = Current(
                time = "",
                interval = 0,
                temperature_2m = temperature.removeSuffix("°C").toDoubleOrNull() ?: 0.0,
                relative_humidity_2m = humidity.removeSuffix("%").toIntOrNull() ?: 0,
                wind_speed_10m = windSpeed.removeSuffix(" km/h").toDoubleOrNull() ?: 0.0,
                weather_code = getWeatherCode(weatherCondition),
                uv_index = uvIndex.toDoubleOrNull() ?: 0.0
            ),
            hourly_units = HourlyUnits("", "", "", ""),
            hourly = Hourly(
                time = emptyList(),
                temperature_2m = emptyList(),
                relative_humidity_2m = emptyList(),
                wind_speed_10m = emptyList(),
                precipitation_probability = listOf(rainToday.removeSuffix("%").toIntOrNull() ?: 0)
            )
        )
    }


    private fun getWeatherCondition(code: Int): String {
        return when (code) {
            0 -> "Clear"
            1 -> "Mainly Clear"
            2 -> "Partly Cloudy"
            3 -> "Overcast"
            45, 48 -> "Foggy"
            51, 53, 55 -> "Drizzle"
            56, 57 -> "Freezing Drizzle"
            61, 63, 65 -> "Rain"
            66, 67 -> "Freezing Rain"
            71, 73, 75, 77 -> "Snow"
            80, 81, 82 -> "Rain Showers"
            85, 86	-> "Snow Showers"
            95, 96, 99 -> "Thunderstorm"
            else -> "Unknown"
        }
    }

    private fun getWeatherCode(condition: String): Int {
        return when (condition) {
            "Clear" -> 0
            "Mainly Clear" -> 1
            "Partly Cloudy" -> 2
            "Overcast" -> 3
            "Foggy" -> 45
            "Drizzle" -> 51
            "Freezing Drizzle" -> 56
            "Rain" -> 61
            "Freezing Rain" -> 66
            "Snow" -> 71
            "Rain Showers" -> 80
            "Snow Showers" -> 85
            "Thunderstorm" -> 95
            else -> -1
        }
    }
}