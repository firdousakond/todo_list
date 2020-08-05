package com.tech.todolist.view.todo

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.tech.todolist.R
import com.tech.todolist.model.TodoModel
import com.tech.todolist.util.*
import kotlinx.android.synthetic.main.item_todo.view.*

class TodoAdapter(private val context: Context, private val todoClickListener: TodoClickListener) : RecyclerView.Adapter<TodoAdapter.TodoViewHolder>() {

    private var todoList : ArrayList<TodoModel> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        return TodoViewHolder(LayoutInflater.from(context).inflate(R.layout.item_todo,parent,false))
    }

    override fun getItemCount(): Int {
        return todoList.size
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        val todoModel = todoList[position]
        holder.setView(todoModel)
    }

    fun addAll(todoList: List<TodoModel>) {
        for (todoModel in todoList) {
            add(todoModel)
        }
    }

    private fun add(todoModel: TodoModel){
        todoList.add(todoModel)
       notifyDataSetChanged()
    }

    fun delete(todoModel: TodoModel){
        todoList.remove(todoModel)
        notifyDataSetChanged()
    }

    fun deleteAll(){
        todoList.clear()
        notifyDataSetChanged()
    }
    inner class TodoViewHolder (itemView: View): RecyclerView.ViewHolder(itemView) {
        private val tvTitle: TextView = itemView.tvTitle
        private val tvStatus: TextView = itemView.tvStatus
        private val tvDate: TextView = itemView.tvDate
        private val ivEdit: ImageView = itemView.ivEdit
        private val ivDelete: ImageView = itemView.ivDelete

        init {
            itemView.setOnClickListener { todoClickListener.onTodoClick(todoList[adapterPosition], TYPE_VIEW) }
            ivEdit.setOnClickListener { todoClickListener.onTodoClick(todoList[adapterPosition], TYPE_EDIT) }
            ivDelete.setOnClickListener { todoClickListener.onTodoClick(todoList[adapterPosition], TYPE_DELETE)  }
        }
        fun setView(todoModel: TodoModel) {

            tvTitle.text = todoModel.title
            val date = DateUtils.getDateTimeMonthFromMillis(todoModel.taskTime)
            tvDate.text = date
            if(todoModel.status == STATUS_COMPLETED){
                tvStatus.setTextColor(ContextCompat.getColor(context, R.color.primary_color))
                ivEdit.hide()

            }else{
                ivEdit.show()
                tvStatus.setTextColor(ContextCompat.getColor(context, R.color.red_color))
            }
            tvStatus.text = todoModel.status
        }

    }

    interface TodoClickListener{
        fun onTodoClick(todoModel: TodoModel, type: String)
    }
}