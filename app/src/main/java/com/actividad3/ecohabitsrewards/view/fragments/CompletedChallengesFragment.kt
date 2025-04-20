package com.actividad3.ecohabitsrewards.view.fragments

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.actividad3.ecohabitsrewards.databinding.FragmentCompletedChallengesBinding
import com.actividad3.ecohabitsrewards.model.Challenge
import com.actividad3.ecohabitsrewards.view.adapters.ChallengeAdapter
import com.actividad3.ecohabitsrewards.viewmodel.CompletedChallengesViewModel

class CompletedChallengesFragment : Fragment() {

    private var _binding: FragmentCompletedChallengesBinding? = null
    private val binding get() = _binding!!
    private val viewModel: CompletedChallengesViewModel by viewModels()
    private lateinit var adapter: ChallengeAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCompletedChallengesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = ChallengeAdapter(emptyList()) { /* Click opcional */ }
        binding.recyclerViewCompleted.layoutManager = LinearLayoutManager(context)
        binding.recyclerViewCompleted.adapter = adapter

        viewModel.completedChallenges.observe(viewLifecycleOwner) {
            adapter.updateList(it)
        }

        viewModel.loadCompletedChallenges()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
