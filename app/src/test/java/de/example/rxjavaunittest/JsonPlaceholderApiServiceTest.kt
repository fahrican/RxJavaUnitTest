package de.example.rxjavaunittest

import de.example.rxjavaunittest.data.networking.JsonPlaceholderApi
import de.example.rxjavaunittest.data.networking.JsonPlaceholderApiService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.junit.Test
import org.junit.Assert.*
import org.junit.Before
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

class JsonPlaceholderApiServiceTest {

    @Mock
    lateinit var webService: JsonPlaceholderApi

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
    }

    private fun createOkHttpClient(): OkHttpClient {
        val logger = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        return OkHttpClient.Builder()
            .addInterceptor(logger)
            .connectTimeout(15, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
            .build()
    }

    @Test
    fun `given two instances of retrofit check those`() {
        val retro = Retrofit.Builder()
            .client(createOkHttpClient())
            .baseUrl("https://jsonplaceholder.typicode.com")
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create(JsonPlaceholderApi::class.java)

        assertNotEquals(webService, retro)
    }

    @Test
    fun getClient() {
        val service2 = JsonPlaceholderApiService.getClient()
        assertNotEquals(webService, service2)
    }

}