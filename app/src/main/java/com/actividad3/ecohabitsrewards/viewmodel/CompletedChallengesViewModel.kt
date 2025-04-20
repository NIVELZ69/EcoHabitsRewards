package com.actividad3.ecohabitsrewards.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.actividad3.ecohabitsrewards.model.Challenge
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class CompletedChallengesViewModel : ViewModel() {

    private val _completedChallenges = MutableLiveData<List<Challenge>>()
    val completedChallenges: LiveData<List<Challenge>> get() = _completedChallenges

    private val db = FirebaseFirestore.getInstance()

    fun loadCompletedChallenges() {
        val uid = FirebaseAuth.getInstance().currentUser?.uid ?: return

        db.collection("users").document(uid)
            .collection("acceptedChallenges")
            .whereEqualTo("status", true)
            .get()
            .addOnSuccessListener { result ->
                val list = result.mapNotNull { it.toObject(Challenge::class.java) }
                _completedChallenges.value = list
            }
    }
}
