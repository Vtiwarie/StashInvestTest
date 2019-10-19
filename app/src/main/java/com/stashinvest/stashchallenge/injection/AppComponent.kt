package com.stashinvest.stashchallenge.injection

import com.stashinvest.stashchallenge.App
import com.stashinvest.stashchallenge.ui.activity.MainActivity
import com.stashinvest.stashchallenge.ui.fragment.MainFragment
import com.stashinvest.stashchallenge.ui.fragment.PopUpDialogFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, NetworkModule::class])
interface AppComponent {
    fun inject(app: App)
    
    fun inject(activity: MainActivity)
    
    fun inject(fragment: MainFragment)
    
    fun inject(fragment: PopUpDialogFragment)
}