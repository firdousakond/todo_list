package com.tech.todolist.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity
data class TodoModel(

    @PrimaryKey(autoGenerate = true)
    var id: Int?=null,

    @ColumnInfo(name = "title")
    var title: String,

    @ColumnInfo(name = "description")
    var description: String,

    @ColumnInfo(name = "status")
    var status: String,

    @ColumnInfo(name = "taskTime")
    var taskTime: Long
): Parcelable