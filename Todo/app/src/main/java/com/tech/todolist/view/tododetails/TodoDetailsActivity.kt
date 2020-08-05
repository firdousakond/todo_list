package com.tech.todolist.view.tododetails

import android.os.Bundle
import android.os.Handler
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.tech.todolist.R
import com.tech.todolist.model.TodoModel
import com.tech.todolist.util.*
import com.tech.todolist.view.base.BaseActivity
import kotlinx.android.synthetic.main.activity_todo_details.*
import kotlinx.android.synthetic.main.activity_toolbar.*
import javax.inject.Inject

class TodoDetailsActivity : BaseActivity() {

    @Inject
    lateinit var todoDetailsViewModel: TodoDetailsViewModel
    private var todoModel: TodoModel? = null
//    override fun getViewModel(): TodoDetailsViewModel? = todoDetailsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_todo_details)
        initToolbar(tool_bar)
        tvHeader.text = getString(R.string.task_details)
        setListener()
        getBundle()
        initObserver()
    }

    private fun initObserver() {
        todoDetailsViewModel.todoStatusLiveData.observe(this, statusTodoObserver)
    }

    private val statusTodoObserver = Observer<LiveDataResult<Long>> { result ->
        when (result?.status) {

            LiveDataResult.Status.LOADING -> {
                //todo show progressbar
            }
            LiveDataResult.Status.SUCCESS -> {
                val value = result.data
                if (value!! > 0) {
                    displaySnackbar(
                        this,
                        getString(R.string.todo_success_status),
                        MessageType.SUCCESS
                    )
                    Handler().postDelayed({ finish() }, 2000)
                }
            }
            LiveDataResult.Status.ERROR -> {
                displaySnackbar(this, getString(R.string.todo_error), MessageType.ERROR)
            }
        }

    }

    private fun getBundle() {
        val bundle = intent.extras
        if (bundle != null) {
            todoModel = bundle.getParcelable(TODO_MODEL)
            setData()
        }
    }

    private fun setData() {
        tvTitle.text = todoModel?.title
        tvDescription.text = todoModel?.description
        val date = DateUtils.getDateTimeMonthFromMillis(todoModel?.taskTime!!)
        tvTaskTime.text = date
        if (todoModel?.status == STATUS_COMPLETED) {
            tvStatus.setTextColor(ContextCompat.getColor(this, R.color.primary_color))
            btnCompleted.hide()

        } else {
            tvStatus.setTextColor(ContextCompat.getColor(this, R.color.red_color))
        }
        tvStatus.text = todoModel?.status
    }

    private fun setListener() {
        btnCompleted.setOnClickListener { markAsCompleted() }
    }

    private fun markAsCompleted() {
        if (todoModel != null) {
            todoModel?.status = getString(R.string.completed)
            todoDetailsViewModel.updateTodo(todoModel!!)
        }
    }
}