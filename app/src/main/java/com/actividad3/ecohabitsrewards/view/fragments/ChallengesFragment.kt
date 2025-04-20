package com.actividad3.ecohabitsrewards.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.actividad3.ecohabitsrewards.databinding.FragmentChallengesBinding
import com.actividad3.ecohabitsrewards.model.Challenge
import com.actividad3.ecohabitsrewards.view.adapters.ChallengeAdapter
import com.actividad3.ecohabitsrewards.viewmodel.ChallengesViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore

class ChallengesFragment : Fragment() {

    private var _binding: FragmentChallengesBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: ChallengeAdapter
    private val viewModel: ChallengesViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChallengesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = ChallengeAdapter(emptyList()) { challenge ->
            showAcceptDialog(challenge)
        }
        binding.recyclerViewChallenges.layoutManager = LinearLayoutManager(context)
        binding.recyclerViewChallenges.adapter = adapter

        observeViewModel()
        viewModel.loadChallenges()

        val user = FirebaseAuth.getInstance().currentUser
        if (user != null) {
            viewModel.loadChallengesFilteredByUser(user.uid)
        }

    }

    private fun observeViewModel() {
        viewModel.challenges.observe(viewLifecycleOwner) { challenges ->
            adapter.updateList(challenges)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showAcceptDialog(challenge: Challenge) {
        AlertDialog.Builder(requireContext())
            .setTitle("¿Aceptar reto?")
            .setMessage("¿Quieres aceptar el reto: \"${challenge.name}\"?")
            .setPositiveButton("Aceptar") { _, _ ->
                val uid = FirebaseAuth.getInstance().currentUser?.uid ?: return@setPositiveButton

                val challengeData = mapOf(
                    "id" to challenge.id,
                    "name" to challenge.name,
                    "description" to challenge.description,
                    "points" to challenge.points,
                    "acceptedAt" to FieldValue.serverTimestamp(),
                    "status" to false
                )

                FirebaseFirestore.getInstance()
                    .collection("users").document(uid)
                    .collection("acceptedChallenges").document(challenge.id)
                    .set(challengeData)
                    .addOnSuccessListener {
                        Toast.makeText(context, "Reto aceptado", Toast.LENGTH_SHORT).show()
                        viewModel.loadChallengesFilteredByUser(uid)
                    }

            }
            .setNegativeButton("Cancelar", null)
            .show()
    }

}
