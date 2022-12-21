package com.project.workmanagertutorial.network

import android.util.Log
import com.project.workmanagertutorial.states.ResultState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RandomUserRepositoryImpl @Inject constructor(private val randomUserApi: RandomUserApi) : RandomUserRepository {

    override suspend fun getRandomUsersResponse(): Flow<ResultState> =
        flow {
            emit(ResultState.LOADING)
            delay(300)
            try {
                val res = randomUserApi.fetchUsersResponse()
                if (res.isSuccessful) {
                    emit(res.body()?.let {
                        ResultState.SUCCESS(it)
                    } ?: throw Exception("Data not available"))
                } else {
                    throw Exception(res.errorBody()?.string())
                }
            } catch (e: Exception) {
                Log.d("Exception", "Exception error : $e")
            }
        }
}