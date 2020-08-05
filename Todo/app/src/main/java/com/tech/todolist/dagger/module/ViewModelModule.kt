package com.tech.todolist.dagger.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tech.todolist.dagger.TodoViewModelFactory
import com.tech.todolist.dagger.ViewModelKey
import com.tech.todolist.view.todo.TodoViewModel
import com.tech.todolist.view.tododetails.TodoDetailsViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    abstract fun bindViewModelFactory(factory: TodoViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(TodoViewModel::class)
    abstract fun todoViewModel(todoViewModel: TodoViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(TodoDetailsViewModel::class)
    abstract fun todoDetailsViewModel(todoDetailsViewModel: TodoDetailsViewModel): ViewModel


}
