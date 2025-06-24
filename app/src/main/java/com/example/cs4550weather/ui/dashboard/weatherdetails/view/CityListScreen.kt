package com.example.cs4550weather.ui.dashboard.weatherdetails.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.cs4550weather.data.model.SavedCity
import com.example.cs4550weather.ui.dashboard.DashboardViewModel

@Composable
fun CityListScreen(
    onCityClick: (String) -> Unit,
    viewModel: DashboardViewModel = viewModel()
) {
    val context = LocalContext.current
    val savedCities by viewModel.savedCities.collectAsState()

    // Initialize repository
    LaunchedEffect(Unit) {
        viewModel.initializeRepository(context)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFDCE7E4))
            .padding(24.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Title
            Text(
                text = "Cities",
                fontSize = 35.sp,
                color = Color(0xFF557270),
                fontWeight = FontWeight.Normal,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 40.dp)
            )

            // Cities List or Empty State
            if (savedCities.isEmpty()) {
                // Empty state
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "No saved cities yet.\nAdd cities from the Search tab.",
                        fontSize = 16.sp,
                        color = Color(0xFF557270),
                        textAlign = TextAlign.Center
                    )
                }
            } else {
                // Cities list
                LazyColumn(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(savedCities) { city ->
                        CityCard(
                            city = city,
                            onCityClick = { onCityClick(city.name) }
                        )
                    }
                }
            }

            // Add City Button (always at bottom)
            Button(
                onClick = { /* Navigate to search/add city */ },
                modifier = Modifier
                    .width(180.dp)
                    .height(42.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF546980)
                ),
                shape = RoundedCornerShape(0.dp)
            ) {
                Text(
                    text = "Add City",
                    color = Color.White,
                    fontSize = 16.sp
                )
            }
        }
    }
}

@Composable
fun CityCard(
    city: SavedCity,
    onCityClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(71.dp)
            .clickable { onCityClick() },
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = city.name,
                    fontSize = 18.sp,
                    color = Color(0xFF557270),
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "${city.temperature} • ${city.humidity} • ${city.windSpeed}",
                    fontSize = 14.sp,
                    color = Color(0xFF557270)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CityListScreenPreview() {
    CityListScreen(onCityClick = {})
}