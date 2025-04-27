package com.actividad3.ecohabitsrewards.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.actividad3.ecohabitsrewards.databinding.FragmentRewardsBinding
import com.actividad3.ecohabitsrewards.model.Reward
import com.actividad3.ecohabitsrewards.view.adapters.RewardAdapter
import com.actividad3.ecohabitsrewards.viewmodel.RewardsViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class RewardsFragment : Fragment() {

    private var _binding: FragmentRewardsBinding? = null
    private val binding get() = _binding!!

    private val rewardsViewModel: RewardsViewModel by viewModels()

    private lateinit var adapter: RewardAdapter
    private val firestore = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRewardsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = RewardAdapter(emptyList()) { reward ->
            redeemReward(reward)
        }

        binding.recyclerRewards.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerRewards.adapter = adapter

        rewardsViewModel.rewardsList.observe(viewLifecycleOwner) { rewards ->
            adapter = RewardAdapter(rewards) { reward ->
                redeemReward(reward)
            }
            binding.recyclerRewards.adapter = adapter
        }

        rewardsViewModel.loadRewards()
    }

    private fun redeemReward(reward: Reward) {
        val userId = auth.currentUser?.uid ?: return
        val userRef = firestore.collection("users").document(userId)

        userRef.get().addOnSuccessListener { document ->
            val currentPoints = document.getLong("points") ?: 0L

            if (currentPoints >= reward.pointsRequired) {
                // Descuenta los puntos
                userRef.update("points", currentPoints - reward.pointsRequired)
                    .addOnSuccessListener {
                        Toast.makeText(requireContext(), "¡Recompensa canjeada!", Toast.LENGTH_SHORT).show()
                    }
                    .addOnFailureListener {
                        Toast.makeText(requireContext(), "Error al canjear recompensa.", Toast.LENGTH_SHORT).show()
                    }
            } else {
                Toast.makeText(requireContext(), "No tienes suficientes puntos.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
