package com.tech.todolist.view.todo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.tech.todolist.R
import com.tech.todolist.model.TodoModel
import com.tech.todolist.util.*
import com.tech.todolist.view.NavigationManager
import com.tech.todolist.view.base.BaseFragment
import com.tech.todolist.view.tododetails.TodoDetailsActivity
import kotlinx.android.synthetic.main.fragment_todo.*
import javax.inject.Inject

class TodoFragment : BaseFragment(), TodoAdapter.TodoClickListener {

    @Inject
    lateinit var todoViewModel: TodoViewModel
    private lateinit var adapter: TodoAdapter
    private var todoModel: TodoModel? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_todo, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setAdapter()
        setListener()
        initObserver()
    }

    private fun initObserver() {
        todoViewModel.todoListLiveData.observe(viewLifecycleOwner, todoObserver)
        todoViewModel.deleteTodoLiveData.observe(viewLifecycleOwner, deleteTodoObserver)
    }

    private val todoObserver = Observer<LiveDataResult<List<TodoModel>>> { result ->
        when (result?.status) {

            LiveDataResult.Status.LOADING -> {
                progressbar.show()
            }
            LiveDataResult.Status.SUCCESS -> {
                progressbar.hide()
                val todoList = result.data
                if (!todoList.isNullOrEmpty()) {
                    adapter.addAll(todoList)
                } else {
                    tvNoData.show()
                }
            }
            LiveDataResult.Status.ERROR -> {
                progressbar.hide()
                displaySnackbar(
                    requireActivity(),
                    getString(R.string.todo_error),
                    MessageType.ERROR
                )
            }
        }

    }

    private val deleteTodoObserver = Observer<LiveDataResult<Long>> { result ->
        when (result?.status) {

            LiveDataResult.Status.LOADING -> {
                //todo show progressbar
            }
            LiveDataResult.Status.SUCCESS -> {
                val value = result.data
                if (value!! > 0) {
                    adapter.delete(todoModel!!)
                }
                if (adapter.itemCount == 0) {
                    tvNoData.show()
                }
            }
            LiveDataResult.Status.ERROR -> {
                displaySnackbar(
                    requireActivity(),
                    getString(R.string.todo_error),
                    MessageType.ERROR
                )
            }
        }

    }


    private fun setListener() {
        fabAdd.setOnClickListener {
            val bundle = Bundle()
            bundle.putString(HEADER, getString(R.string.create_todo))
            NavigationManager.openClass(requireActivity(), AddEditTodoActivity::class.java, bundle)
        }
    }

    private fun setAdapter() {
        adapter = TodoAdapter(requireContext(), this)
        rvTodo.initialize(adapter)
    }

    override fun onResume() {
        super.onResume()
        tvNoData.hide()
        adapter.deleteAll()
        todoViewModel.fetchTodo()
    }

    override fun onTodoClick(todoModel: TodoModel, type: String) {
        this.todoModel = todoModel
        when (type) {
            TYPE_EDIT -> {
                val bundle = Bundle()
                bundle.putString(HEADER, getString(R.string.edit_todo))
                bundle.putBoolean(IS_EDIT, true)
                bundle.putParcelable(TODO_MODEL, todoModel)
                NavigationManager.openClass(
                    requireActivity(),
                    AddEditTodoActivity::class.java,
                    bundle
                )
            }
            TYPE_DELETE -> {
                todoViewModel.deleteTodo(todoModel)
            }
            TYPE_VIEW -> {
                val bundle = Bundle()
                bundle.putParcelable(TODO_MODEL, todoModel)
                NavigationManager.openClass(
                    requireActivity(),
                    TodoDetailsActivity::class.java,
                    bundle
                )
            }
            else -> {
                Logger.debug("Else Part")
            }
        }

    }

}