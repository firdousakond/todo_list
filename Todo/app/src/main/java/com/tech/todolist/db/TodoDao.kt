package com.tech.todolist.db

import androidx.room.*
import com.tech.todolist.model.TodoModel
import io.reactivex.Completable
import io.reactivex.Maybe

@Dao
interface TodoDao {

    @Query("SELECT * FROM TodoModel ORDER BY id DESC")
    fun loadAll(): Maybe<List<TodoModel>>

    @Query("DELETE FROM TodoModel")
    fun deleteAllFromTable() : Completable

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(todoModel: TodoModel) : Completable

    @Update
    fun update(todoModel: TodoModel) : Completable

    @Delete
    fun delete(todoModel: TodoModel) : Completable

}