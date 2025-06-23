package com.example.cs4550weather

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.cs4550weather.data.model.SavedCity
import com.example.cs4550weather.data.repository.SavedCitiesRepository
import com.example.cs4550weather.ui.dashboard.DashboardViewModel
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.*
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class DashboardViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: DashboardViewModel
    private lateinit var repository: SavedCitiesRepository

    @Before
    fun setUp() {
        repository = mock()
        viewModel = DashboardViewModel()
        val repoField = DashboardViewModel::class.java.getDeclaredField("savedCitiesRepository")
        repoField.isAccessible = true
        repoField.set(viewModel, repository)
    }

    @Test
    fun loadSavedCities() {
        val cities = listOf(
            SavedCity("Boston", "10°C", "50%", "10 km/h"),
            SavedCity("New York", "12°C", "60%", "12 km/h")
        )
        whenever(repository.getSavedCities()).thenReturn(cities)

        viewModel.loadSavedCities()

        assert(viewModel.savedCities.value == cities)
    }

    @Test
    fun `removeCity calls repository and updates LiveData`() {
        val city = SavedCity("Boston", "10°C", "50%", "10 km/h")
        val updatedCities = listOf<SavedCity>()
        whenever(repository.getSavedCities()).thenReturn(updatedCities)

        viewModel.removeCity(city)

        verify(repository).removeCity(city.name)
        assert(viewModel.savedCities.value == updatedCities)
    }
} 