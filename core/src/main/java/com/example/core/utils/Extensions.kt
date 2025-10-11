package com.example.core.utils


sealed class NetworkResultWrapper<out T> {
    data class Success<T>(val data: T) : NetworkResultWrapper<T>()
    data class Error(val code: Int, val message: String?) : NetworkResultWrapper<Nothing>()
    data class Exception(val throwable: Throwable) : NetworkResultWrapper<Nothing>()
}

suspend fun <T> safeApiCall(apiCall: suspend () -> T): NetworkResultWrapper<T> {
    return try {
        val result = apiCall()
        NetworkResultWrapper.Success(result)
    } catch (e: retrofit2.HttpException) {
        NetworkResultWrapper.Error(e.code(), e.message())
    } catch (e: java.io.IOException) {
        NetworkResultWrapper.Exception(e)
    } catch (e: Exception) {
        NetworkResultWrapper.Exception(e)
    }
}

suspend fun <Input, Output> processResponse(
    result: NetworkResultWrapper<Input>,
    successBlock: suspend (Input) -> Output
): UiState<Output> {
    return when (result) {
        is NetworkResultWrapper.Error -> UiState.Error(result.message ?: "Unknown error")
        is NetworkResultWrapper.Exception -> UiState.Error(
            result.throwable.message ?: "Exception occurred"
        )

        is NetworkResultWrapper.Success -> UiState.Success(successBlock(result.data))
    }
}


