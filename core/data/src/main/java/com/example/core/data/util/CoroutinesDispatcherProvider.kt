package com.example.core.data.util

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

internal data class CoroutinesDispatcherProvider(
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
