package com.empire.gemini_chat.ui.screens.chatScreen

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.empire.gemini_chat.domain.repositories.GeminiRepository
import com.empire.gemini_chat.utils.Resource
import com.google.ai.client.generativeai.Chat
import com.google.ai.client.generativeai.type.content
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.LinkedList
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val geminiRepository: GeminiRepository
) : ViewModel() {
    var uiState by mutableStateOf(ChatScreenUIState())
        private set

    var incomingMessage by mutableStateOf("")
        private set

    init {
        startChat()
    }

    private fun startChat() {
        viewModelScope.launch {
            when (val response = geminiRepository.startChat(emptyList())) {
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
    }

    fun sendMessageStream(prompt: String) {
        val content = content(role = "user") { text(prompt) }
        uiState = uiState.copy(
            isLoading = true
        )
        var charBuffer = ""
        val queue = LinkedList<String>()
        var hasStartedSteaming = false
        viewModelScope.launch {
            launch {
                try {
                    uiState.chat?.let {
                        uiState.chats.add(prompt)
                        uiState.chats.add("")
                        val lastIndex = uiState.chats.lastIndex
                        geminiRepository.sendMessageStream(it, content).collect { chunk ->

                            charBuffer += chunk.text
                            queue.push(chunk.text)
                            hasStartedSteaming = true
//                            incomingMessage += chunk.text
                            val updatedLastChat = uiState.chats[lastIndex] + chunk.text
                            uiState.chats[lastIndex] = updatedLastChat

                            uiState = uiState.copy(
                                chats = uiState.chats, isLoading = false
                            )
                        }
                    }
                } catch (e: Exception) {
                    Log.d("Response", "error: ${e.message}")
                    uiState = uiState.copy(
                        error = e.message ?: "An unknown error occurred!", isLoading = false
                    )
                }
            }.invokeOnCompletion { hasStartedSteaming = false }
            launch {
                while (!hasStartedSteaming) {
                    delay(20)
                }
                var chunck = queue.poll()
                while (chunck != null) {
                    chunck.forEach {
                        delay(2)
                        incomingMessage += it
                    }
                    chunck = queue.poll()
                }

//                while (charBuffer.isNotEmpty()){
//                    delay(2)
//                }
            }

        }
    }

    fun sendMessage(prompt: String) {
        val content =
            content(role = "user") { text("Provide brief and concise response to this question and all responses should be in markdown for formatting: $prompt") }
        uiState = uiState.copy(
            isLoading = true, hasCompleted = false
        )

        viewModelScope.launch {
            launch {
                try {
                    uiState.chat?.let {

                        uiState.chats.add(prompt)
                        when (val response = geminiRepository.sendMessage(it, content)) {
                            is Resource.Error -> {

                                uiState = uiState.copy(
                                    error = response.message, isLoading = false, hasCompleted = true
                                )
                            }

                            is Resource.Success -> {
                                response.data?.text?.let { text ->
                                    uiState.chats.add(text)
                                }
                                uiState = uiState.copy(
                                    isLoading = false, hasCompleted = true
                                )
                            }
                        }
                    }
                } catch (e: Exception) {
                    Log.d("Response", "error: ${e.message}")
                    uiState = uiState.copy(
                        error = e.message ?: "An unknown error occurred!",
                        isLoading = false,
                        hasCompleted = true
                    )
                }
            }
        }

    }

}

data class ChatScreenUIState(
    val chat: Chat? = null,
    val chats: MutableList<String> = mutableListOf(),
    var error: String? = null,
    val lastMessageContent: String? = null,
    val isLoading: Boolean = false,
    val hasCompleted: Boolean = false
)
