package com.empire.gemini_chat.data.repositories

import com.empire.gemini_chat.domain.repositories.GeminiRepository
import com.empire.gemini_chat.utils.Resource
import com.google.ai.client.generativeai.Chat
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.Content
import com.google.ai.client.generativeai.type.GenerateContentResponse
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GeminiRepositoryImpl @Inject constructor(
    private val generativeModel: GenerativeModel
) : GeminiRepository {

    override suspend fun startChat(history: List<Content>): Resource<Chat> {
        return try {
            Resource.Success(data = generativeModel.startChat(history))
        } catch (e: Exception) {
            Resource.Error(message = e.message ?: "An unknown error occurred!")
        }
    }

    override suspend fun sendMessage(
        chat: Chat, content: Content
    ): Resource<GenerateContentResponse> {
        return try {
            Resource.Success(data = chat.sendMessage(content))
        } catch (e: Exception) {
            Resource.Error(message = e.message ?: "An unknown error occurred!")
        }
    }

    override suspend fun sendMessageStream(
        chat: Chat, content: Content
    ): Flow<GenerateContentResponse> {
        return chat.sendMessageStream(content)
    }

}