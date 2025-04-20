package com.actividad3.ecohabitsrewards.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.actividad3.ecohabitsrewards.model.Challenge
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class AcceptedChallengesViewModel : ViewModel() {

    private val _acceptedChallenges = MutableLiveData<List<Challenge>>()
    val acceptedChallenges: LiveData<List<Challenge>> get() = _acceptedChallenges

    private val db = FirebaseFirestore.getInstance()

    fun loadAcceptedChallenges() {
        val uid = FirebaseAuth.getInstance().currentUser?.uid ?: return

        db.collection("users").document(uid)
            .collection("acceptedChallenges")
            .whereEqualTo("status", false) // Solo los activos
            .get()
            .addOnSuccessListener { result ->
                val list = result.mapNotNull { it.toObject(Challenge::class.java) }
                _acceptedChallenges.value = list
            }
    }
}
