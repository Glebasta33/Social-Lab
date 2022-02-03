package com.trusov.sociallab

import android.app.Application
import com.trusov.sociallab.di.DaggerApplicationComponent

class SocialLabApp : Application() {
    val component by lazy {
        DaggerApplicationComponent.factory()
            .create(this)
    }
}