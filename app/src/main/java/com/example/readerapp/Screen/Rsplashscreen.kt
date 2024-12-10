package com.example.readerapp.Screen

import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.readerapp.Navigation.ReaderScreens
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.delay


@Composable
fun RSplashScreen(navController: NavController) {
    // Animation scale state
    val scale = remember {
        Animatable(0f)
    }

    // Animation and Navigation Logic
    LaunchedEffect(key1 = true) {
        // Perform the scaling animation
        scale.animateTo(
            targetValue = 0.9f,
            animationSpec = tween(
                durationMillis = 800,
                easing = {
                    OvershootInterpolator(8f).getInterpolation(it)
                }
            )
        )
        // Delay for splash duration
        delay(2000L)

        // Navigate based on authentication state
        if (FirebaseAuth.getInstance().currentUser?.email.isNullOrEmpty()) {
            navController.navigate(ReaderScreens.LoginScreen.name)
        } else {
            navController.navigate(ReaderScreens.ReaderHomeScreen.name)
        }
    }

    // Splash Screen UI
    Surface(
        modifier = Modifier
            .padding(15.dp)
            .size(330.dp)
            .scale(scale.value), // Apply scale animation
        shape = CircleShape,
        color = Color.White,
        border = BorderStroke(width = 2.dp, color = Color.LightGray)
    ) {
        Column(
            modifier = Modifier.padding(1.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            logo() // Logo
            Spacer(modifier = Modifier.height(15.dp))
            Text(
                text = "\"Connect with Alums \"",
                style = MaterialTheme.typography.headlineSmall,
                color = Color.LightGray
            )
        }
    }
}

@Composable
fun logo(modifier: Modifier = Modifier) {
    Text(
        text = "AlumBridge",
        modifier = Modifier.padding(16.dp),
        style = MaterialTheme.typography.displayMedium,
        color = Color.Red
    )
}
