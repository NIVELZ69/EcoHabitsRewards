package com.actividad3.ecohabitsrewards.view.fragments

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.actividad3.ecohabitsrewards.databinding.FragmentAcceptedChallengesBinding
import com.actividad3.ecohabitsrewards.view.adapters.ChallengeAdapter
import com.actividad3.ecohabitsrewards.viewmodel.AcceptedChallengesViewModel

class AcceptedChallengesFragment : Fragment() {

    private var _binding: FragmentAcceptedChallengesBinding? = null
    private val binding get() = _binding!!
    private val viewModel: AcceptedChallengesViewModel by viewModels()
    private lateinit var adapter: ChallengeAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAcceptedChallengesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = ChallengeAdapter(emptyList()) { /* puedes añadir click aquí */ }
        binding.recyclerViewAccepted.layoutManager = LinearLayoutManager(context)
        binding.recyclerViewAccepted.adapter = adapter

        viewModel.acceptedChallenges.observe(viewLifecycleOwner) {
            adapter.updateList(it)
        }

        viewModel.loadAcceptedChallenges()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
