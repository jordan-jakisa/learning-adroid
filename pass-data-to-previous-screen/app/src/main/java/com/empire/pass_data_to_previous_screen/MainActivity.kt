package com.empire.pass_data_to_previous_screen

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.empire.pass_data_to_previous_screen.ui.theme.PassdatatopreviousscreenTheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PassdatatopreviousscreenTheme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController, startDestination = "screen1"
                ) {

                    composable("screen1") { entry ->
                        val text = entry.savedStateHandle.get<String>("my_text")

                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(16.dp)
                        ) {
                            text?.let {
                                Text(text = it)
                            }
                            Button(onClick = {
                                navController.navigate("screen2")
                            }) {
                                Text(text = "Navigate to screen 2")
                            }
                        }
                    }

                    composable("screen2") {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(16.dp)
                        ) {
                            var text by remember {
                                mutableStateOf("")
                            }
                            OutlinedTextField(value = text, onValueChange = { text = it })
                            Button(onClick = {
                                navController.previousBackStackEntry?.savedStateHandle?.set(
                                        "my_text",
                                        text
                                    )
                                navController.popBackStack()
                            }) {
                                Text(text = "Navigate to screen 1")
                            }
                        }
                    }

                }
            }
        }
    }
}