package com.tech.todolist.util

import android.util.Log
import com.tech.todolist.BuildConfig

class Logger private constructor() {

    init {
        throw IllegalStateException("Logger class")
    }

    companion object {

        private const val APP_ID = "todo"
        private const val LOG_DIR = "/" + BuildConfig.APPLICATION_ID
        private const val LOG_FILE_NAME = "/" + "todo.txt"
        private const val WRITE_LOGS_TO_FILE = false
        private const val LOG_LEVEL_VERBOSE = 4
        private const val LOG_LEVEL_DEBUG = 3
        private const val LOG_LEVEL_INFO = 2
        private const val LOG_LEVEL_ERROR = 1
        private const val LOG_LEVEL_OFF = 0
        private const val CURRENT_LOG_LEVEL = LOG_LEVEL_VERBOSE

        private fun log(message: String, logLevel: Int) {
            if (logLevel > CURRENT_LOG_LEVEL) {
                return
            }
            Log.v(APP_ID, message)
        }


        fun logOff(message: String) {
            log(message, LOG_LEVEL_OFF)
        }

        fun verbose(message: String) {
            log(message, LOG_LEVEL_VERBOSE)
        }

        fun debug(message: String) {
            log(message, LOG_LEVEL_DEBUG)
        }

        fun error(message: String) {
            log(message, LOG_LEVEL_ERROR)
        }

        fun info(message: String) {
            log(message, LOG_LEVEL_INFO)
        }

    }


}
