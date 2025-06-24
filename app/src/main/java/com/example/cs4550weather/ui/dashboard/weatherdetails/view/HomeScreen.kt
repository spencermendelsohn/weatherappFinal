package com.example.cs4550weather.ui.dashboard.weatherdetails.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import android.widget.Toast
import com.example.cs4550weather.ui.home.HomeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel = viewModel()
) {
    val context = LocalContext.current
    val keyboardController = LocalSoftwareKeyboardController.current
    var cityName by remember { mutableStateOf("") }
    val weatherState by viewModel.weatherState.collectAsState()

    // Initialize repository
    LaunchedEffect(Unit) {
        viewModel.initializeRepository(context)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFDCE7E4))
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            // Title
            Text(
                text = "Add City",
                fontSize = 35.sp,
                color = Color(0xFF557270),
                fontWeight = FontWeight.Normal,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 24.dp)
            )

            // Input Field
            OutlinedTextField(
                value = cityName,
                onValueChange = { cityName = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(8.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    focusedBorderColor = Color.Transparent,
                    unfocusedBorderColor = Color.Transparent,
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black
                ),
                singleLine = true,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(
                    onDone = {
                        if (cityName.isNotBlank()) {
                            viewModel.searchWeather(cityName)
                            keyboardController?.hide()
                        }
                    }
                )
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Submit Button
            Button(
                onClick = {
                    if (cityName.isNotBlank()) {
                        viewModel.searchWeather(cityName)
                        keyboardController?.hide()
                    }
                },
                modifier = Modifier
                    .width(180.dp)
                    .height(42.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF546980)
                ),
                shape = RoundedCornerShape(0.dp),
                enabled = !weatherState.isLoading
            ) {
                if (weatherState.isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(20.dp),
                        color = Color.White,
                        strokeWidth = 2.dp
                    )
                } else {
                    Text(
                        text = "Submit",
                        color = Color.White,
                        fontSize = 16.sp
                    )
                }
            }

            // Show success message and save option when weather is found
            if (weatherState.cityName.isNotEmpty() && weatherState.error == null) {
                Spacer(modifier = Modifier.height(30.dp))

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = weatherState.cityName,
                            fontSize = 20.sp,
                            color = Color(0xFF557270),
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "${weatherState.temperature} • ${weatherState.weatherCondition}",
                            fontSize = 16.sp,
                            color = Color(0xFF557270),
                            modifier = Modifier.padding(vertical = 8.dp)
                        )

                        Button(
                            onClick = {
                                viewModel.saveCurrentCity()
                                Toast.makeText(context, "City saved!", Toast.LENGTH_SHORT).show()
                            },
                            modifier = Modifier
                                .width(180.dp)
                                .height(42.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFF546980)
                            ),
                            shape = RoundedCornerShape(0.dp),
                            enabled = !viewModel.isCurrentCitySaved()
                        ) {
                            Text(
                                text = if (viewModel.isCurrentCitySaved()) "Saved ✓" else "Save City",
                                color = Color.White,
                                fontSize = 16.sp
                            )
                        }
                    }
                }
            }

            // Show error message if any
            if (weatherState.error != null) {
                Spacer(modifier = Modifier.height(20.dp))
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFFFE5E5)),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        text = weatherState.error!!,
                        modifier = Modifier.padding(16.dp),
                        color = Color(0xFF8B0000),
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen()
}