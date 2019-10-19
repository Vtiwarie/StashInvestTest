package com.stashinvest.stashchallenge.injection

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(private var application: Application) {
    
    @Provides
    @Singleton
    @ForApplication
    fun provideContext(): Context {
        return application
    }
}
