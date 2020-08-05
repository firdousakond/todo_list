package com.tech.todolist.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.core.content.ContextCompat

class NavigationManager {

    companion object {


        fun openClass(context: Context?, aClass: Class<*>, bundle: Bundle? = null) {
            if (context == null) return
            val intent = Intent(context, aClass).apply {
                if (bundle != null) {
                    putExtras(bundle)
                }
            }
            ContextCompat.startActivity(context, intent, null)
        }
    }


}