package com.actividad3.ecohabitsrewards.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class RewardsViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Fragmento de Recompensas"
    }
    val text: LiveData<String> = _text
}