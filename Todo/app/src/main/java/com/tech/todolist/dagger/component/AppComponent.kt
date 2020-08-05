package com.tech.todolist.dagger.component

import android.app.Application
import android.content.Context
import com.tech.todolist.TodoApplication
import com.tech.todolist.dagger.module.ActivityModule
import com.tech.todolist.dagger.module.AppModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton



@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class,
        AppModule::class,
        ActivityModule::class]
)
interface AppComponent {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        @BindsInstance
        fun appContext(context: Context) : Builder

        fun build(): AppComponent
    }

    fun inject(todoApplication: TodoApplication)

}