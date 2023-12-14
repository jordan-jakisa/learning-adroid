package com.empire.gemini_chat.domain.repositories

import com.empire.gemini_chat.utils.Resource
import com.google.ai.client.generativeai.Chat
import com.google.ai.client.generativeai.type.Content
import com.google.ai.client.generativeai.type.GenerateContentResponse
import kotlinx.coroutines.flow.Flow

interface GeminiRepository {
    suspend fun startChat(history: List<Content>): Resource<Chat>

    suspend fun sendMessage(chat: Chat, content: Content): Resource<GenerateContentResponse>

    suspend fun sendMessageStream(chat: Chat, content: Content): Flow<GenerateContentResponse>
}