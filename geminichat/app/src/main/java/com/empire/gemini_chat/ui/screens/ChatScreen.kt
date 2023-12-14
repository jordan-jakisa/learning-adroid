package com.empire.gemini_chat.ui.screens

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun ChatScreen(
    vm: ChatViewModel = hiltViewModel()
) {
    val uiState = vm.uiState

}