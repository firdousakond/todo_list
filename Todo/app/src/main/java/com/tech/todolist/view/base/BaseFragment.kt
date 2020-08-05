package com.tech.todolist.view.base

import android.app.Activity
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.tech.todolist.R
import com.tech.todolist.dagger.Injectable
import com.tech.todolist.util.MessageType
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject


abstract class BaseFragment : Fragment(),
    HasAndroidInjector, Injectable {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Any>

    override fun androidInjector(): AndroidInjector<Any> = dispatchingAndroidInjector

    @Inject
    lateinit var mViewModelFactory: ViewModelProvider.Factory

    fun displaySnackbar(
        activity: Activity?,
        message: String,
        messageType: MessageType
    ) {

        if (activity != null) {

            var view: View? = activity.window.decorView.rootView ?: return

            try {
                view =
                    (activity.findViewById<View>(android.R.id.content) as ViewGroup).getChildAt(
                        0
                    )
            } catch (e: Exception) {
                Log.d("Error", e.toString())
            }

            val snackbar = Snackbar.make(view!!, message, Snackbar.LENGTH_SHORT)

            val tvSnackBar =
                snackbar.view.findViewById<TextView>(com.google.android.material.R.id.snackbar_text)

            tvSnackBar.textAlignment = View.TEXT_ALIGNMENT_CENTER

            when (messageType) {

                MessageType.ERROR -> tvSnackBar.setBackgroundColor(
                    activity.resources.getColor(
                        R.color.red_color
                    )
                )
                MessageType.SUCCESS -> tvSnackBar.setBackgroundColor(
                    activity.resources.getColor(
                        R.color.green_color
                    )
                )
                else -> tvSnackBar.setBackgroundColor(activity.resources.getColor(R.color.accent_color))
            }

            snackbar.view.setPadding(0, 0, 0, 0)

            snackbar.show()

        }
    }

}