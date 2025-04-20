package com.actividad3.ecohabitsrewards.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.actividad3.ecohabitsrewards.model.StatisticsData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class StatisticsViewModel : ViewModel() {

    private val _statistics = MutableLiveData<StatisticsData>()
    val statistics: LiveData<StatisticsData> get() = _statistics

    private val db = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    fun loadStatistics() {
        val uid = auth.currentUser?.uid ?: return

        val userRef = db.collection("users").document(uid)
        val challengesRef = userRef.collection("acceptedChallenges")

        userRef.get().addOnSuccessListener { userDoc ->
            val points = userDoc.getLong("points") ?: 0L

            challengesRef.get().addOnSuccessListener { snapshot ->
                val all = snapshot.size()
                val completed = snapshot.count { it.getBoolean("status") == true }
                val accepted = all - completed
                val percent = if (all > 0) (completed * 100 / all) else 0

                _statistics.value = StatisticsData(
                    completed = completed,
                    accepted = accepted,
                    points = points,
                    percentageCompleted = percent
                )
            }
        }
    }
}
