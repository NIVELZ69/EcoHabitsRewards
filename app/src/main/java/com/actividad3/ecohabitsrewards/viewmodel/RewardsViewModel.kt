package com.actividad3.ecohabitsrewards.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.actividad3.ecohabitsrewards.model.Reward
import com.google.firebase.firestore.FirebaseFirestore

class RewardsViewModel : ViewModel() {

    private val _rewardsList = MutableLiveData<List<Reward>>()
    val rewardsList: LiveData<List<Reward>> get() = _rewardsList

    private val db = FirebaseFirestore.getInstance()

    fun loadRewards() {
        db.collection("rewards")
            .get()
            .addOnSuccessListener { result ->
                val rewards = result.map { document ->
                    Reward(
                        id = document.id,
                        name = document.getString("name") ?: "",
                        description = document.getString("description") ?: "",
                        pointsRequired = document.getLong("pointsRequired")?.toInt() ?: 0,
                        imageUrl = document.getString("imageUrl") ?: ""
                    )
                }
                _rewardsList.value = rewards
            }
            .addOnFailureListener { exception ->
                _rewardsList.value = emptyList() // Devuelve lista vacía en caso de fallo
            }
    }
}
