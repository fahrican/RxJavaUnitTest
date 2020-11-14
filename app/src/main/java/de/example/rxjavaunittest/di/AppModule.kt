package de.example.rxjavaunittest.di

import dagger.Module
import dagger.Provides
import de.example.rxjavaunittest.data.networking.JsonPlaceholderApi
import de.example.rxjavaunittest.data.networking.JsonPlaceholderApiService
import de.example.rxjavaunittest.data.repository.Repository
import de.example.rxjavaunittest.data.repository.RepositoryImpl
import javax.inject.Singleton


@Module
object AppModule {

    @Singleton
    @Provides
    fun provideApiService(): JsonPlaceholderApi = JsonPlaceholderApiService.getClient()

    @Provides
    fun provideRepository(api: JsonPlaceholderApi): Repository = RepositoryImpl(api)

}