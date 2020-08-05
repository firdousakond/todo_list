package com.tech.todolist.view.todo

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tech.todolist.db.TodoDao
import com.tech.todolist.model.TodoModel
import com.tech.todolist.util.LiveDataResult
import com.tech.todolist.util.Logger
import io.reactivex.CompletableObserver
import io.reactivex.MaybeObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class TodoViewModel @Inject constructor(private var todoDao: TodoDao) : ViewModel() {

    var createTodoLiveData = MutableLiveData<LiveDataResult<Long>>()
    var deleteTodoLiveData = MutableLiveData<LiveDataResult<Long>>()
    private var disposable: Disposable = CompositeDisposable()
    var todoListLiveData = MutableLiveData<LiveDataResult<List<TodoModel>>>()

    fun fetchTodo() {
        todoDao.loadAll()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(LoadTodo())
    }

    fun createTodo(todoModel: TodoModel) {
        todoDao.insert(todoModel)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(CreateTodoResult())
    }

    fun deleteTodo(todoModel: TodoModel) {
        todoDao.delete(todoModel)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(DeleteTodoResult())
    }

    inner class CreateTodoResult : CompletableObserver {

        override fun onComplete() {
            this@TodoViewModel.createTodoLiveData.postValue(LiveDataResult.success(1))
        }

        override fun onSubscribe(d: Disposable) {
            this@TodoViewModel.createTodoLiveData.postValue(LiveDataResult.loading())
        }

        override fun onError(e: Throwable) {
            this@TodoViewModel.createTodoLiveData.postValue(LiveDataResult.error(e))
        }

    }


    inner class DeleteTodoResult : CompletableObserver {

        override fun onComplete() {
            this@TodoViewModel.deleteTodoLiveData.postValue(LiveDataResult.success(1))
        }

        override fun onSubscribe(d: Disposable) {
            this@TodoViewModel.deleteTodoLiveData.postValue(LiveDataResult.loading())
        }

        override fun onError(e: Throwable) {
            this@TodoViewModel.deleteTodoLiveData.postValue(LiveDataResult.error(e))
        }

    }

    inner class LoadTodo : MaybeObserver<List<TodoModel>> {


        override fun onSubscribe(d: Disposable) {
            this@TodoViewModel.todoListLiveData.postValue(LiveDataResult.loading())
        }

        override fun onError(e: Throwable) {
            this@TodoViewModel.todoListLiveData.postValue(LiveDataResult.error(e))
        }

        override fun onSuccess(t: List<TodoModel>) {
            this@TodoViewModel.todoListLiveData.postValue(LiveDataResult.success(t))
        }

        override fun onComplete() {
            Logger.debug("Completed")
        }

    }

    override fun onCleared() {
        super.onCleared()
        disposable.dispose()
    }
}