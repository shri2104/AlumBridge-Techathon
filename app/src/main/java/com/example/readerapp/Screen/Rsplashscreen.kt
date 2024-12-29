package com.example.readerapp.Screen

import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
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
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.delay

@Composable
fun RSplashScreen(navController: NavController) {
    val scale = remember {
        Animatable(0f)
    }
    LaunchedEffect(key1 = true) {
        scale.animateTo(
            targetValue = 0.9f,
            animationSpec = tween(
                durationMillis = 800,
                easing = {
                    OvershootInterpolator(8f).getInterpolation(it)
                }
            )
        )
        delay(2000L)
        val user = FirebaseAuth.getInstance().currentUser
        if (user != null) {
            val firestore = FirebaseFirestore.getInstance()
            firestore.collection("users")
                .document(user.uid)
                .get()
                .addOnSuccessListener { document ->
                    if (document.exists()) {
                        val userType = document.getString("role")
                        if (userType == "student") {
                            navController.navigate(ReaderScreens.ReaderHomeScreen.name)
                        } else {
                            navController.navigate(ReaderScreens.InstituteHomeScreen.name)
                        }
                    } else {
                        navController.navigate(ReaderScreens.LoginScreen.name)
                    }
                }
                .addOnFailureListener {
                    navController.navigate(ReaderScreens.LoginScreen.name)
                }
        } else {
            navController.navigate(ReaderScreens.LoginScreen.name)
        }
    }
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Surface(
            modifier = Modifier
                .size(330.dp)
                .scale(scale.value),
            shape = CircleShape,
            color = Color.White,
            border = BorderStroke(width = 2.dp, color = Color.LightGray)
        ) {
            Column(
                modifier = Modifier.padding(1.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                logo()
                Spacer(modifier = Modifier.height(15.dp))
                Text(
                    text = "\"Connect with Alums \"",
                    style = MaterialTheme.typography.headlineSmall,
                    color = Color.LightGray
                )
            }
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
