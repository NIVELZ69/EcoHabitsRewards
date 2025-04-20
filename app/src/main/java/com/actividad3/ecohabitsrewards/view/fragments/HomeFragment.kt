package com.actividad3.ecohabitsrewards.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.actividad3.ecohabitsrewards.R
import com.actividad3.ecohabitsrewards.databinding.FragmentHomeBinding
import com.actividad3.ecohabitsrewards.viewmodel.HomeViewModel

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeViewModel()
        viewModel.loadUserPoints()
        setupCardClicks()
    }

    private fun observeViewModel() {
        viewModel.points.observe(viewLifecycleOwner) { points ->
            binding.tvPoints.text = "$points"
        }
    }

    private fun setupCardClicks() {
        binding.cardAccepted.setOnClickListener {
            findNavController().navigate(R.id.nav_accepted_challenges)
        }
        binding.cardCompleted.setOnClickListener {
            findNavController().navigate(R.id.nav_completed_challenges)
        }
        binding.cardStats.setOnClickListener {
            findNavController().navigate(R.id.nav_statistics)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
