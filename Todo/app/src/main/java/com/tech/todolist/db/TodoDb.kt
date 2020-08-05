package com.tech.todolist.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.tech.todolist.model.TodoModel


@Database(
    entities = [TodoModel::class],
    version = 1,
    exportSchema = false
)
abstract class TodoDb : RoomDatabase() {
    abstract fun  todoDao(): TodoDao
}