package com.tech.todolist.dagger.module

import android.app.Application
import androidx.room.Room
import com.tech.todolist.db.TodoDao
import com.tech.todolist.db.TodoDb
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module(includes = [ViewModelModule::class])
class AppModule {

    /**
     * add provides dao classes here
     */
    @Singleton
    @Provides
    fun provideTodoDao(db: TodoDb): TodoDao {
        return db.todoDao()
    }

    /**
     * provide db here
     */
    @Singleton
    @Provides
    fun provideDb(app: Application): TodoDb {
        return Room
            .databaseBuilder(app, TodoDb::class.java, "todo.db")
            .fallbackToDestructiveMigration()
            .build()
    }

}