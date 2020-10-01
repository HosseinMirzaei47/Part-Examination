package com.example.myapplication.core.utils

import android.view.View
import com.example.myapplication.core.resource.Resource
import retrofit2.HttpException

suspend inline fun <T> safeApiCall(responseFunction: suspend () -> T): Resource<T> {
    return try {
        Resource.success(responseFunction.invoke())
    } catch (e: HttpException) {
        Resource.error(e.code().toString(), null)
    } catch (e: Exception) {
        println("jalil $e")
        Resource.error("vpn disconnected", null)
    }
}

fun View.setOnSingleClickListener(l: View.OnClickListener) {
    setOnClickListener(OnSingleClickListener(l))
}

fun View.setOnSingleClickListener(l: (View) -> Unit) {
    setOnClickListener(OnSingleClickListener(l))
}