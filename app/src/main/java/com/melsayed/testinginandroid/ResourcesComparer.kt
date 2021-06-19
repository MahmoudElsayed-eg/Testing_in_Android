package com.melsayed.testinginandroid

import android.content.Context

class ResourcesComparer {
    fun isEqual(
        context: Context,
        resID: Int, string: String
    ): Boolean {
        return context.getString(resID) == string
    }
}