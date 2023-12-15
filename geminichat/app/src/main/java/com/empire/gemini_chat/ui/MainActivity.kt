package com.empire.gemini_chat.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.empire.gemini_chat.ui.screens.chatScreen.ChatScreen
import com.empire.gemini_chat.ui.theme.GeminichatTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContent {
            GeminichatTheme {
                ChatScreen()
            }
        }
    }
}