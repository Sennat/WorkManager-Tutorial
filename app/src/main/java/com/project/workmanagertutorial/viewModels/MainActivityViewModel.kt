package com.project.workmanagertutorial.viewModels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.workmanagertutorial.network.RandomUserRepository
import com.project.workmanagertutorial.states.ResultState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val randUserRepository: RandomUserRepository,
    private val dispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _userData: MutableLiveData<ResultState> = MutableLiveData(ResultState.LOADING)
    val userData: LiveData<ResultState> get() = _userData

    init {
        getRandomUser()
    }

   private fun getRandomUser() {
        viewModelScope.launch(dispatcher) {
            randUserRepository.getRandomUsersResponse().collect {
                Log.i("VIEWMODEL", "Random user: $it")
                _userData.postValue(it)
            }
        }
    }
}