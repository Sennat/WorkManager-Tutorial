package com.project.workmanagertutorial

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.project.workmanagertutorial.databinding.ActivityMainBinding
import com.project.workmanagertutorial.model.User
import com.project.workmanagertutorial.states.ResultState
import com.project.workmanagertutorial.viewModels.MainActivityViewModel
import com.project.workmanagertutorial.workers.WorkScheduler
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val binding by lazy(LazyThreadSafetyMode.NONE) {
        ActivityMainBinding.inflate(
            layoutInflater
        )
    }
    private val viewModel: MainActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initViewModel()
        lifecycleScope.launch(Dispatchers.Main){
            startAppWorkManager()
        }

    }

    private fun initViewModel() {
        viewModel.userData.observe(this) {
            when (it) {
                is ResultState.LOADING -> {
                    //Toast.makeText(this,"TEST", Toast.LENGTH_LONG).show()
                    binding.apply {
                        txtStatus.text = "loading . . ."
                        txtStatus.setTextColor(Color.BLUE)
                        txtStatus.visibility = View.VISIBLE
                    }
                }
                is ResultState.SUCCESS<*> -> {
                    val user = it.response as User
                    //val user: User = userList.first()
                    Log.i("ACTIVITY", "Random user: $user")
                    binding.apply {
                        txtTitle.text = "${user.results[0].name.title}."
                        txtFname.text = user.results[0].name.first
                        txtLname.text = user.results[0].name.last
                        txtDob.text = "Age: ${user.results[0].dob.age} years old"
                        txtGender.text = "Gender: ${user.results[0].gender.capitalize(Locale.ROOT)}"
                        txtStreet.text = "Street: ${user.results[0].location.street.name.capitalize(Locale.ROOT)}, ${user.results[0].location.street.number}"
                        txtCity.text = "City: ${user.results[0].location.city.capitalize(Locale.ROOT)}"
                        txtState.text = "State: ${user.results[0].location.state.capitalize(Locale.ROOT)}"
                        txtCountry.text = "Country: ${user.results[0].location.country.capitalize(Locale.ROOT)}"

                        Glide.with(profileImg)
                            .load(user.results[0].picture.large)
                            .into(profileImg)

                        txtStatus.visibility = View.GONE
                    }
                }
                is ResultState.UPDATED -> {
                    binding.apply {
                        txtStatus.text = "Updating . . ."
                        txtStatus.setTextColor(Color.GRAY)
                        txtStatus.visibility = View.VISIBLE
                    }
                }
                is ResultState.FAILURE -> {
                    binding.apply {
                        txtStatus.text = "Data not found"
                        txtStatus.setTextColor(Color.RED)
                        txtStatus.visibility = View.VISIBLE
                    }
                }
            }
        }
    }

    private suspend fun startAppWorkManager() {
        initViewModel()
        WorkScheduler.doPeriodicWork(this)
    }
}