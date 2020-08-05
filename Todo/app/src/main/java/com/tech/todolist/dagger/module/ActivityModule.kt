package com.tech.todolist.dagger.module

import com.tech.todolist.view.home.HomeActivity
import com.tech.todolist.view.todo.AddEditTodoActivity
import com.tech.todolist.view.tododetails.TodoDetailsActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityModule {


    @ContributesAndroidInjector(modules = [FragmentBuildersModule::class])
    abstract fun contributeHomeActivity() : HomeActivity

    @ContributesAndroidInjector(modules = [FragmentBuildersModule::class])
    abstract fun contributeCreateTodoActivity() : AddEditTodoActivity

    @ContributesAndroidInjector(modules = [FragmentBuildersModule::class])
    abstract fun contributeTodoDetailsActivity() : TodoDetailsActivity

}