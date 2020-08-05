package com.tech.todolist.dagger.module

import com.tech.todolist.view.todo.TodoFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentBuildersModule {

    @ContributesAndroidInjector
    abstract fun contributeTodoFragment(): TodoFragment

}