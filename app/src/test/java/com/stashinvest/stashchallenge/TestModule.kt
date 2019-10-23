package com.stashinvest.stashchallenge

import android.app.Application
import com.stashinvest.stashchallenge.injection.AppModule
import dagger.Module

@Module
class TestModule(application: Application) : AppModule(application)
