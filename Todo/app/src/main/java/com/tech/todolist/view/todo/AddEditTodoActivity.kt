package com.tech.todolist.view.todo

import android.os.Bundle
import android.os.Handler
import androidx.lifecycle.Observer
import com.tech.todolist.R
import com.tech.todolist.dialog.DateTimeDialog
import com.tech.todolist.model.TodoModel
import com.tech.todolist.util.*
import com.tech.todolist.view.base.BaseActivity
import com.tech.todolist.view.tododetails.TodoDetailsViewModel
import kotlinx.android.synthetic.main.activity_create_todo.*
import kotlinx.android.synthetic.main.activity_toolbar.*
import javax.inject.Inject

class AddEditTodoActivity : BaseActivity() {

    @Inject
    lateinit var todoViewModel: TodoViewModel

    @Inject
    lateinit var todoDetailsViewModel: TodoDetailsViewModel
    private var taskTime = 0L
    private var isEdit = false

    private var todoModel: TodoModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_todo)
        initToolbar(tool_bar)
        getBundle()
        setListener()
        initObserver()
    }

    private fun initObserver() {
        todoViewModel.createTodoLiveData.observe(this, createTodoObserver)
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

    private val createTodoObserver = Observer<LiveDataResult<Long>> { result ->
        when (result?.status) {

            LiveDataResult.Status.LOADING -> {
                //todo show progressbar
            }
            LiveDataResult.Status.SUCCESS -> {
                val value = result.data
                if (value!! > 0) {
                    displaySnackbar(this, getString(R.string.todo_success), MessageType.SUCCESS)
                    etTitle.text?.clear()
                    etDescription.text?.clear()
                    tvTaskTime.text = null
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
            val header = bundle.getString(HEADER)
            isEdit = bundle.getBoolean(IS_EDIT)
            todoModel = bundle.getParcelable(TODO_MODEL)
            tvHeader.text = header
            if (todoModel != null) {
                setData()
            }
        }
    }

    private fun setData() {
        etTitle.setText(todoModel?.title)
        etDescription.setText(todoModel?.description)
        taskTime = todoModel?.taskTime!!
        val time = DateUtils.getDateTimeMonthFromMillis(todoModel?.taskTime!!)
        tvTaskTime.text = time
    }

    private fun setListener() {
        tvTaskTime.setOnClickListener {
            val dt = DateTimeDialog(this)
            dt.show()
        }
        btnSubmit.setOnClickListener { saveTodo() }
    }

    private fun saveTodo() {
        val title = etTitle.text.toString()
        val description = etDescription.text.toString()

        if (isValidField(title, description)) {

            if (isEdit && todoModel != null) {
                todoModel?.title = title
                todoModel?.description = description
                todoModel?.taskTime = taskTime
                todoDetailsViewModel.updateTodo(todoModel!!)
            } else {
                val todoModel =
                    TodoModel(null, title, description, getString(R.string.pending), taskTime)
                todoViewModel.createTodo(todoModel)
            }
        }
    }

    private fun isValidField(title: String, description: String): Boolean {

        if (title.isEmpty()) {
            displaySnackbar(this, getString(R.string.error_title), MessageType.ERROR)
            return false
        }
        if (description.isEmpty()) {
            displaySnackbar(this, getString(R.string.error_description), MessageType.ERROR)
            return false
        }
        if (taskTime == 0L) {
            displaySnackbar(this, getString(R.string.error_date_time), MessageType.ERROR)
            return false
        }
        return true
    }

    fun setDateTime(dateTime: Long) {
        taskTime = dateTime
        val date = DateUtils.getDateTimeMonthFromMillis(dateTime)
        tvTaskTime.text = date
    }

}