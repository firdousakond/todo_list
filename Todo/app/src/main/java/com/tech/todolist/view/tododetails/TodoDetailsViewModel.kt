package com.tech.todolist.view.tododetails

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tech.todolist.db.TodoDao
import com.tech.todolist.model.TodoModel
import com.tech.todolist.util.LiveDataResult
import io.reactivex.CompletableObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class TodoDetailsViewModel @Inject constructor(private var todoDao: TodoDao) : ViewModel() {

    var todoStatusLiveData = MutableLiveData<LiveDataResult<Long>>()

    fun updateTodo(todoModel: TodoModel) {
        todoDao.update(todoModel)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(UpdateTodoResult())
    }

    inner class UpdateTodoResult : CompletableObserver {

        override fun onComplete() {
            this@TodoDetailsViewModel.todoStatusLiveData.postValue(LiveDataResult.success(1))
        }

        override fun onSubscribe(d: Disposable) {
            this@TodoDetailsViewModel.todoStatusLiveData.postValue(LiveDataResult.loading())
        }

        override fun onError(e: Throwable) {
            this@TodoDetailsViewModel.todoStatusLiveData.postValue(LiveDataResult.error(e))
        }

    }
}