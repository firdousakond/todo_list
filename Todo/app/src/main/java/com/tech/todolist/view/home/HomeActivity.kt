package com.tech.todolist.view.home

import android.os.Bundle
import com.tech.todolist.R
import com.tech.todolist.view.base.BaseActivity
import com.tech.todolist.view.todo.TodoPagerAdapter
import com.tech.todolist.view.todo.TodoViewModel
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class HomeActivity : BaseActivity() {

    @Inject
    lateinit var todoViewModel: TodoViewModel
    private lateinit var todoPagerAdapter: TodoPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initUI()
    }


    private fun initUI() {
        setupViewPager()
    }

    private fun setupViewPager() {
        todoPagerAdapter = TodoPagerAdapter(
            supportFragmentManager,
            this
        )

        viewpager.adapter = todoPagerAdapter
        tlTodo.setupWithViewPager(viewpager)
    }

}