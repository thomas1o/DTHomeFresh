package com.example.dthomefresh.utils

import com.example.dthomefresh.MainActivity

class FragmentHandler(private val activity: MainActivity) {
    fun openDrawer() {
        activity.openDrawer()
    }

    fun closeDrawer() {
        activity.closeDrawer()
    }

}