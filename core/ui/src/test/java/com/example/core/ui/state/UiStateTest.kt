package com.example.core.ui.state

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class UiStateTest {

    @Test
    fun `successful result maps to success state`() {
        assertEquals(UiState.Success("bulbasaur"), Result.success("bulbasaur").toUiState())
    }

    @Test
    fun `failed result maps to error state with the exception message`() {
        val state = Result.failure<String>(IllegalStateException("Not found")).toUiState()

        assertEquals(UiState.Error<String>(message = "Not found"), state)
    }

    @Test
    fun `isError treats empty as error only when requested`() {
        assertTrue(UiState.Empty.isError())
        assertFalse(UiState.Empty.isError(withEmpty = false))
        assertTrue(UiState.Error<Unit>("Oops").isError(withEmpty = false))
    }
}
