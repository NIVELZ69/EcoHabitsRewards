package com.actividad3.ecohabitsrewards.view.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.actividad3.ecohabitsrewards.R
import com.actividad3.ecohabitsrewards.model.Challenge

class ChallengeAdapter(
    private var challenges: List<Challenge>,
    private val onChallengeClicked: (Challenge) -> Unit
) : RecyclerView.Adapter<ChallengeAdapter.ViewHolder>() {


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.findViewById(R.id.challenge_name)
        val description: TextView = view.findViewById(R.id.challenge_description)
        val points: TextView = view.findViewById(R.id.challenge_points)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_challenge, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val challenge = challenges[position]
        holder.name.text = challenge.name
        holder.description.text = challenge.description
        holder.points.text = "${challenge.points} pts"
        holder.itemView.setOnClickListener {
            onChallengeClicked(challenge)
        }
    }

    override fun getItemCount(): Int = challenges.size

    fun updateList(newList: List<Challenge>) {
        challenges = newList
        notifyDataSetChanged()
    }

}


