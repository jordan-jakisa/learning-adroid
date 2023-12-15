package com.empire.gemini_chat.di

import com.empire.gemini_chat.BuildConfig
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.generationConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class GoogleAiModule {

    val config = generationConfig {
        temperature = 0.9f
        maxOutputTokens = 200
        stopSequences = listOf("red")
    }

    @Provides
    @Singleton
    fun providesGenerativeModel() = GenerativeModel(
        modelName = "gemini-pro",
        apiKey = BuildConfig.GEMINI_API_KEY,
        generationConfig = config
    )
}