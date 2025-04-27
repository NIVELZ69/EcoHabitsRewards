package com.actividad3.ecohabitsrewards.view.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.actividad3.ecohabitsrewards.R
import com.actividad3.ecohabitsrewards.model.Reward
import com.bumptech.glide.Glide

class RewardAdapter(
    private val rewards: List<Reward>,
    private val onRewardClick: (Reward) -> Unit
) : RecyclerView.Adapter<RewardAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.findViewById(R.id.reward_name)
        val description: TextView = view.findViewById(R.id.reward_description)
        val points: TextView = view.findViewById(R.id.reward_points)
        val image: ImageView = view.findViewById(R.id.reward_image)
        val button: Button = view.findViewById(R.id.btn_redeem)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_reward, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val reward = rewards[position]
        holder.name.text = reward.name
        holder.description.text = reward.description
        holder.points.text = "${reward.pointsRequired} pts"
        Glide.with(holder.itemView.context)
            .load(reward.imageUrl)
            .into(holder.image)

        holder.button.setOnClickListener {
            onRewardClick(reward)
        }
    }

    override fun getItemCount(): Int = rewards.size
}
