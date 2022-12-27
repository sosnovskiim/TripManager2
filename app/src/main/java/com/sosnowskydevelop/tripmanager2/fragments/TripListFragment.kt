package com.sosnowskydevelop.tripmanager2.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.sosnowskydevelop.tripmanager2.R
import com.sosnowskydevelop.tripmanager2.TripManagerApplication
import com.sosnowskydevelop.tripmanager2.adapters.TripListAdapter
import com.sosnowskydevelop.tripmanager2.databinding.FragmentTripListBinding
import com.sosnowskydevelop.tripmanager2.viewmodels.TripViewModel
import com.sosnowskydevelop.tripmanager2.viewmodels.TripViewModelFactory

class TripListFragment : Fragment() {
    private lateinit var binding: FragmentTripListBinding

    private val viewModel: TripViewModel by viewModels {
        TripViewModelFactory((activity?.application as TripManagerApplication).tripRepository)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTripListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tripList.layoutManager = LinearLayoutManager(requireContext())
        val tripListAdapter = TripListAdapter()
        binding.tripList.adapter = tripListAdapter

        viewModel.tripList.observe(viewLifecycleOwner) { tripList ->
            tripList?.let { tripListAdapter.submitList(tripList) }
        }

        binding.tripAdd.setOnClickListener {
            findNavController().navigate(R.id.action_tripListFragment_to_tripAddFragment)
        }
    }
}