package com.example.core.model

import androidx.annotation.Keep
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

@Keep
data class CoroutinesDispatcherProvider(
    val main: CoroutineDispatcher = Dispatchers.Main,
    val computation: CoroutineDispatcher = Dispatchers.Default,
    val io: CoroutineDispatcher = Dispatchers.IO
) {
    constructor(testing: Boolean) : this(
        main = Dispatchers.Unconfined,
        computation = Dispatchers.Unconfined,
        io = Dispatchers.Unconfined
    )
}
