package com.tech.todolist

import android.database.sqlite.SQLiteException
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.tech.todolist.db.TodoDao
import com.tech.todolist.model.TodoModel
import com.tech.todolist.util.LiveDataResult
import com.tech.todolist.view.todo.TodoViewModel
import io.reactivex.Completable
import io.reactivex.Maybe
import junit.framework.TestCase.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.ClassRule
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.MockitoAnnotations


/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(JUnit4::class)
class TodoViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    lateinit var todoDao: TodoDao
    @Mock
    lateinit var todoModel: TodoModel
    lateinit var todoViewModel: TodoViewModel

    companion object {
        @ClassRule
        @JvmField
        val schedulers = RxSchedulerRule()
    }

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        this.todoViewModel = TodoViewModel(this.todoDao)
    }

    @Test
    fun fetchTodo_positiveResponse() {
        Mockito.`when`(this.todoDao.loadAll()).thenAnswer {
            return@thenAnswer Maybe.just(ArgumentMatchers.anyList<TodoModel>())
        }

        val observer = mock(Observer::class.java)
                as Observer<LiveDataResult<List<TodoModel>>>
        this.todoViewModel.todoListLiveData.observeForever(observer)

        this.todoViewModel.fetchTodo()

        assertNotNull(this.todoViewModel.todoListLiveData.value)
        assertEquals(LiveDataResult.Status.SUCCESS, this.todoViewModel.todoListLiveData.value?.status)
    }

    @Test
    fun fetchTodo_error() {
        Mockito.`when`(this.todoDao.loadAll()).thenAnswer {
            return@thenAnswer Maybe.error<SQLiteException>(SQLiteException("SQL Exception"))
        }

        val observer = mock(Observer::class.java) as Observer<LiveDataResult<List<TodoModel>>>
        this.todoViewModel.todoListLiveData.observeForever(observer)

        this.todoViewModel.fetchTodo()

        assertNotNull(this.todoViewModel.todoListLiveData.value)
        assertEquals(LiveDataResult.Status.ERROR, this.todoViewModel.todoListLiveData.value?.status)
        assert(this.todoViewModel.todoListLiveData.value?.err is Throwable)
    }


    @Test
    fun saveTodo_positiveResponse() {
        Mockito.`when`(this.todoDao.insert(todoModel)).thenAnswer {
            return@thenAnswer Completable.complete()
        }
        val observer = mock(Observer::class.java)
                as Observer<LiveDataResult<Long>>
        this.todoViewModel.createTodoLiveData.observeForever(observer)

        this.todoViewModel.createTodo(todoModel)

        assertNotNull(this.todoViewModel.createTodoLiveData.value)
        assertEquals(LiveDataResult.Status.SUCCESS, this.todoViewModel.createTodoLiveData.value?.status)
    }


}
