package com.empire.gemini_chat.ui.screens

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.empire.gemini_chat.domain.repositories.GeminiRepository
import com.empire.gemini_chat.utils.Resource
import com.google.ai.client.generativeai.Chat
import com.google.ai.client.generativeai.type.Content
import com.google.ai.client.generativeai.type.GenerateContentResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectIndexed
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val geminiRepository: GeminiRepository
): ViewModel() {
    var uiState by mutableStateOf(ChatScreenUIState())
        private set

    fun startChat() = viewModelScope.launch {
        when (val response =  geminiRepository.startChat(uiState.chats)){
            is Resource.Success -> {
                response.data?.let {
                    uiState = uiState.copy(
                        chat = it
                    )
                }
            }
            is Resource.Error -> {
                uiState = uiState.copy(
                    error = response.message
                )
            }
        }
    }

    fun sendMessageStream(content: Content) = viewModelScope.launch{
        try {
            uiState.chat?.let {
                geminiRepository.sendMessageStream(it, content).collect{content ->
                    val lastMessageIndex =  uiState.chats.size - 1
                    uiState = uiState.copy(
                        lastMessageContent = content,
                        chats = uiState.chats
                    )
                }
            }
        }
        catch (e: Exception){
            uiState = uiState.copy(
                error = e.message ?: "An unknown error occurred!"
            )
        }
    }

}

data class ChatScreenUIState(
    val chat: Chat? = null,
    val chats: MutableList<Content> = mutableListOf(),
    val error: String? = null,
    val lastMessageContent: GenerateContentResponse? = null
)