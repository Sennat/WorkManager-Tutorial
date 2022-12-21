package com.project.workmanagertutorial.network

import com.project.workmanagertutorial.states.ResultState
import kotlinx.coroutines.flow.Flow

interface RandomUserRepository {
    suspend fun getRandomUsersResponse(): Flow<ResultState>
}