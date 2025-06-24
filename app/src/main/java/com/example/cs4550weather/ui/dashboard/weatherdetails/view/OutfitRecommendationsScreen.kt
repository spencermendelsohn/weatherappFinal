package com.example.cs4550weather.ui.dashboard.weatherdetails.view

import androidx.compose.foundation.background
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
        option1Text = "Light jacket or cardigan",
        option1Image = R.drawable.ic_launcher_foreground,
        option2Text = "Comfortable shoes",
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
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xFFDCE7E4))
            .verticalScroll(scrollState)
            .padding(24.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.height(40.dp))

        Text(
            text = cityName,
            fontSize = 28.sp,
            color = Color(0xFF557270),
            textAlign = TextAlign.Center
        )

        Spacer(Modifier.height(32.dp))

        Text(
            text = "Recommendations",
            fontSize = 24.sp,
            color = Color(0xFF557270)
        )

        Spacer(Modifier.height(24.dp))

        OutfitRow(option1Text, option1Image, "Option 1 Image")

        Spacer(Modifier.height(24.dp))

        OutfitRow(option2Text, option2Image, "Option 2 Image")

        Spacer(Modifier.height(40.dp))

        NavigationButton("BACK", onBackToMoreInfoClick)

        Spacer(Modifier.height(32.dp))
    }
}

@Composable
fun OutfitRow(text: String, image: Int, imageDescription: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .height(180.dp)
            .padding(horizontal = 8.dp)
    ) {
        Text(
            text = text,
            fontSize = 18.sp,
            color = Color(0xFF557270),
            modifier = Modifier.weight(1f)
        )

        Spacer(modifier = Modifier.width(16.dp))

        val defaultImage = if (image != 0) image else R.drawable.ic_launcher_foreground
        Image(
            painter = painterResource(id = defaultImage),
            contentDescription = imageDescription,
            contentScale = ContentScale.Fit,
            modifier = Modifier.size(120.dp)
        )
    }
}