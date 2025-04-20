package com.actividad3.ecohabitsrewards.controller

import android.util.Log
import com.actividad3.ecohabitsrewards.model.Challenge
import com.google.firebase.firestore.FirebaseFirestore

class ChallengeController {
    private val db = FirebaseFirestore.getInstance()

    fun getChallenges(callback: (List<Challenge>) -> Unit) {
        db.collection("challenges").get()
            .addOnSuccessListener { result ->
                val challenges = result.documents.mapNotNull { it.toObject(Challenge::class.java) }
                callback(challenges)
            }
            .addOnFailureListener { exception ->
                Log.e("Firebase", "Error obteniendo retos", exception)
            }
    }
}