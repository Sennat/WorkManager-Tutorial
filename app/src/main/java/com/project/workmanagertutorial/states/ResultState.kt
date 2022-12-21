package com.project.workmanagertutorial.states

sealed class ResultState {
    object LOADING : ResultState()
    data class SUCCESS<T>(val response: T) : ResultState()
    data class FAILURE(val error: Exception) : ResultState()
    object UPDATED : ResultState()
}