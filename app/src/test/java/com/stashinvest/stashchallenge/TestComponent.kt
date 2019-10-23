package com.stashinvest.stashchallenge

import com.stashinvest.stashchallenge.injection.AppComponent
import com.stashinvest.stashchallenge.injection.NetworkModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [TestModule::class, NetworkModule::class])
interface TestComponent : AppComponent {
    fun inject(mainPresenterTest: MainPresenterTest)
}