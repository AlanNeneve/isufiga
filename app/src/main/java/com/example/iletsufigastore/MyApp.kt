package com.example.iletsufigastore

import android.app.Application
import com.example.iletsufigastore.dependencyinjection.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MyApp: Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin{

            androidContext(this@MyApp)

            modules(viewModelModule)
        }
    }
}