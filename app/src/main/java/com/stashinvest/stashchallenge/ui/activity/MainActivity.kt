package com.stashinvest.stashchallenge.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.stashinvest.stashchallenge.App
import com.stashinvest.stashchallenge.R
import com.stashinvest.stashchallenge.ui.fragment.MainFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.instance.appComponent.inject(this)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager
                    .beginTransaction()
                    .add(R.id.fragmentContainer, MainFragment.newInstance())
                    .commit()
        }
    }
}
