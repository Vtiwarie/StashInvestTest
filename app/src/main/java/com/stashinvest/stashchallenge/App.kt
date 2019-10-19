package com.stashinvest.stashchallenge

import android.app.Application

import com.stashinvest.stashchallenge.injection.AppComponent
import com.stashinvest.stashchallenge.injection.AppModule
import com.stashinvest.stashchallenge.injection.DaggerAppComponent

class App : Application() {
    lateinit var appComponent: AppComponent
        protected set
    
    override fun onCreate() {
        super.onCreate()
        instance = this
        appComponent = DaggerAppComponent.builder()
                .appModule(AppModule(this))
                .build()
        appComponent.inject(this)
    }
    
    companion object {
        lateinit var instance: App
            private set
    }
}
