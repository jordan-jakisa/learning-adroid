package com.empire.weatherapp

import android.util.Log
import com.empire.weatherapp.data.remote.WeatherAPI
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.HttpRequestRetry
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Assert
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    private lateinit var mockWebServer: MockWebServer
    private lateinit var client: HttpClient

    @Before
    fun setup(){
        client = HttpClient(Android) {
            install(HttpRequestRetry) {
                retryOnServerErrors(maxRetries = 5)
                exponentialDelay()
            }
        }

        mockWebServer = MockWebServer()
    }

    @Test
    fun `testing normal fetching`() = runBlocking {

        val response = MockResponse().setResponseCode(200).setBody("""
            Something nice
        """.trimIndent())
        mockWebServer.enqueue(response)

        val result = WeatherAPI.getWeather("50", "50")
        assertEquals("4", result.toString())
    }

    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

}