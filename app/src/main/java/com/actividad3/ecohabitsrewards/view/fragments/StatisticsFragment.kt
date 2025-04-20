package com.actividad3.ecohabitsrewards.view.fragments

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.actividad3.ecohabitsrewards.databinding.FragmentStatisticsBinding
import com.actividad3.ecohabitsrewards.viewmodel.StatisticsViewModel

class StatisticsFragment : Fragment() {

    private var _binding: FragmentStatisticsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: StatisticsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStatisticsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.statistics.observe(viewLifecycleOwner) { stats ->
            binding.tvStatsCompleted.text = "Retos completados: ${stats.completed}"
            binding.tvStatsAccepted.text = "Retos aceptados: ${stats.accepted}"
            binding.tvStatsPoints.text = "Puntos: ${stats.points}"
            binding.tvStatsRatio.text = "Porcentaje completados: ${stats.percentageCompleted}%"
        }

        viewModel.loadStatistics()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
