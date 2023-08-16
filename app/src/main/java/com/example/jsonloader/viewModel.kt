package com.example.jsonloader

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

open class viewModel:ViewModel() {


    private var userRepository = Loader()
    var users: MutableLiveData<MutableList<User>> = MutableLiveData()

    fun getUserData () {
        viewModelScope.launch {
            var result: MutableList<User>?  = null
            withContext(Dispatchers.IO){
                result = userRepository.jsonParsing(userRepository.jsonLoader())
            }
            users.value = result
        }
    }
}
