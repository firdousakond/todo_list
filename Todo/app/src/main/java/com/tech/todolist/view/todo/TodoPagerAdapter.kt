package com.tech.todolist.view.todo

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.tech.todolist.R


class TodoPagerAdapter(fm: FragmentManager, private val context: Context) :
    FragmentStatePagerAdapter(fm) {

    private lateinit var todoFragment: TodoFragment

    override fun getItem(adapterPosition: Int): Fragment {
        return when (adapterPosition) {
            0 -> {
                todoFragment = TodoFragment()
                return todoFragment
            }
            else -> todoFragment
        }
    }

    override fun getPageTitle(adapterPosition: Int): CharSequence? {

        if (adapterPosition == 0) {

            return context.getString(R.string.todo_list)

        }

        return ""
    }

    override fun getCount(): Int {
        return 1
    }


}
