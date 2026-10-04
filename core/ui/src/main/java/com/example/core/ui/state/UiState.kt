package com.example.core.ui.state

sealed class UiState<out T> {
    data object Uninitialized : UiState<Nothing>() // stateless or default state
    data object Loading : UiState<Nothing>()
    data object Empty: UiState<Nothing>()
    data class Error<T>(val message: String? = "", val errorCode: Int = 0, val data: T? = null) : UiState<T>()
    data class Success<out T>(val data: T) : UiState<T>()
}

fun <T> UiState<T>.isError(withEmpty: Boolean = true): Boolean =
    this is UiState.Error ||
            (this is UiState.Empty && withEmpty)

fun <T> UiState<T>.onSuccess(
    execute: (data: T) -> Unit
): UiState<T> = apply {
    if (this is UiState.Success) {
        execute(data)
    }
}

fun <T> UiState<T>.onError(
    execute: () -> Unit
): UiState<T> = apply {
    if (this is UiState.Error) {
        execute()
    }
}

val <T> UiState<T>.isLoading get() = this is UiState.Loading


fun <T> Result<T>.toUiState(): UiState<T> = fold(
    onSuccess = { UiState.Success(it) },
    onFailure = { UiState.Error(it.message) }
)
