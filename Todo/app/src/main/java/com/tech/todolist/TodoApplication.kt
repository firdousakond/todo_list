package com.tech.todolist

import android.app.Application
import com.tech.todolist.dagger.AppInjector
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

class TodoApplication : Application(), HasAndroidInjector {

    companion object {
        lateinit var context: TodoApplication
            private set
    }

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Any>

    override fun onCreate() {
        super.onCreate()
        AppInjector.init(this)
        context = this
    }

    override fun androidInjector(): AndroidInjector<Any>  = dispatchingAndroidInjector


}