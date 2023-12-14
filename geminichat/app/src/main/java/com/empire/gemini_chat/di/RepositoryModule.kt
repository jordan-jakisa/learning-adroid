package com.empire.gemini_chat.di

import com.empire.gemini_chat.data.repositories.GeminiRepositoryImpl
import com.empire.gemini_chat.domain.repositories.GeminiRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun providesGeminiRepository(geminiRepositoryImpl: GeminiRepositoryImpl): GeminiRepository
}