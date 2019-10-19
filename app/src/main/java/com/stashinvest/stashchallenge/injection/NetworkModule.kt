package com.stashinvest.stashchallenge.injection

import android.content.Context
import com.google.gson.GsonBuilder
import com.stashinvest.stashchallenge.R
import com.stashinvest.stashchallenge.api.StashImageInterceptor
import com.stashinvest.stashchallenge.api.StashImagesApi
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class NetworkModule {
    @Provides
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }
    }
    
    @Provides
    @Singleton
    fun provideHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor,
                          stashImageInterceptor: StashImageInterceptor): OkHttpClient {
        val builder = OkHttpClient.Builder()
                .addInterceptor(httpLoggingInterceptor)
                .addInterceptor(stashImageInterceptor)
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
        return builder.build()
    }
    
    @Provides
    @Singleton
    fun provideGsonConverterFactory(): GsonConverterFactory {
        return GsonConverterFactory.create(GsonBuilder().create())
    }
    
    @Provides
    @Singleton
    fun provideImagesApi(@ForApplication context: Context,
                         httpClient: OkHttpClient,
                         gsonConverterFactory: GsonConverterFactory): StashImagesApi {
        val retrofit = Retrofit.Builder()
                .baseUrl(context.getString(R.string.base_url))
                .addConverterFactory(gsonConverterFactory)
                .client(httpClient)
                .build()
        return retrofit.create(StashImagesApi::class.java)
    }
}
