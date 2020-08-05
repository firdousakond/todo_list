package com.tech.todolist.util
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

fun View.show (){
    visibility = View.VISIBLE
}

fun View.hide (){
    visibility = View.GONE
}

fun RecyclerView.initialize(adapter: RecyclerView.Adapter<*>?) {
    setAdapter(adapter)
    layoutManager = LinearLayoutManager(context)
}

fun RecyclerView.initializeHorizontal(adapter: RecyclerView.Adapter<*>?) {
    setAdapter(adapter)
    layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
}



