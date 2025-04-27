package com.actividad3.ecohabitsrewards.model

data class Reward(
    val id: String = "",
    val name: String = "",
    val description: String = "",
    val pointsRequired: Int = 0,
    val imageUrl: String = ""
)