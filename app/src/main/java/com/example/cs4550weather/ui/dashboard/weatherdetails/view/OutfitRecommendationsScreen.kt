package com.example.cs4550weather.ui.dashboard.weatherdetails.view

import androidx.compose.foundation.background
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cs4550weather.R

@Preview(showBackground = true)
@Composable
fun OutfitScreenPreview() {
    OutfitRecommendationsScreen (
        cityName = "Boston",
        option1Text = "Pants",
        option1Image = R.drawable.ic_launcher_foreground,
        option2Text = "Shirt",
        option2Image = R.drawable.ic_launcher_foreground,
        onBackToMoreInfoClick = {}
    )
}

@Composable
fun OutfitRecommendationsScreen(
    cityName: String,
    option1Text: String,
    option1Image: Int,
    option2Text: String,
    option2Image: Int,
    onBackToMoreInfoClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xFFDCE7E4))
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = cityName,
                fontSize = 35.sp,
                color = Color(0xFF557270),
                textAlign = TextAlign.Center
            )

            Spacer(Modifier.height(40.dp))

            Text("Recommendations", fontSize = 35.sp, color = Color(0xFF557270))

            Spacer(Modifier.height(20.dp))

            OutfitRow(option1Text, option1Image, "Option 1 Image")

            Spacer(Modifier.height(40.dp))

            OutfitRow(option2Text, option2Image, "Option 2 Image")

            Spacer(Modifier.height(60.dp))

            NavigationButton("BACK", onBackToMoreInfoClick)
        }
    }
}

@Composable
fun OutfitRow(text: String, image: Int, imageDescription : String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.size(width = 260.dp, height = 220.dp)
    ) {
        Text(
            text = text,
            fontSize = 20.sp,
            color = Color(0xFF557270)
        )

        Image(
            painter = painterResource(id = image),
            contentDescription = imageDescription,
            contentScale = ContentScale.Fit
        )
    }
}


