package com.example.iletsufigastore.webservice

sealed class ResponseResult<out T: Any> {
    class Success<out T: Any>(val data: T): ResponseResult<T>()
    object Error: ResponseResult<Nothing>()
}