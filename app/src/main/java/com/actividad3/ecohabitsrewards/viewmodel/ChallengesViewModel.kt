package com.actividad3.ecohabitsrewards.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.actividad3.ecohabitsrewards.model.Challenge
import com.google.firebase.firestore.FirebaseFirestore

class ChallengesViewModel : ViewModel() {

    private val _challenges = MutableLiveData<List<Challenge>>()
    val challenges: LiveData<List<Challenge>> get() = _challenges

    private val db = FirebaseFirestore.getInstance()

    fun loadChallenges() {
        db.collection("challenges")
            .get()
            .addOnSuccessListener { result ->
                val list = result.mapNotNull { it.toObject(Challenge::class.java) }
                _challenges.value = list
            }
            .addOnFailureListener {
                _challenges.value = emptyList()
            }
    }

    fun loadChallengesFilteredByUser(uid: String) {
        val db = FirebaseFirestore.getInstance()
        val acceptedRef = db.collection("users").document(uid).collection("acceptedChallenges")

        acceptedRef.get().addOnSuccessListener { acceptedSnapshot ->
            val acceptedIds = acceptedSnapshot.map { it.id }.toSet()

            db.collection("challenges")
                .get()
                .addOnSuccessListener { result ->
                    val allChallenges = result.mapNotNull { doc ->
                        val challenge = doc.toObject(Challenge::class.java)
                        challenge.copy(id = doc.id) // sobrescribe el ID con el ID real del documento
                    }

                    val filtered = allChallenges.filter { it.id !in acceptedIds }
                    _challenges.value = filtered
                }
        }
    }



}
