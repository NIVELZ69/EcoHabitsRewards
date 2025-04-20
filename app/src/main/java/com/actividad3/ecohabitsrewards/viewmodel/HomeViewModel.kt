package com.actividad3.ecohabitsrewards.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class HomeViewModel : ViewModel() {

    private val _points = MutableLiveData<Long>()
    val points: LiveData<Long> get() = _points

    private val db = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    fun loadUserPoints() {
        val uid = auth.currentUser?.uid ?: return

        db.collection("users").document(uid).get()
            .addOnSuccessListener { doc ->
                val value = doc.getLong("points") ?: 0L
                _points.value = value
            }
            .addOnFailureListener {
                _points.value = 0L
            }
    }
}
