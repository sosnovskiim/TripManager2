package com.sosnowskydevelop.tripmanager2.fragments

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.sosnowskydevelop.tripmanager2.R
import com.sosnowskydevelop.tripmanager2.TripManagerApplication
import com.sosnowskydevelop.tripmanager2.data.Trip
import com.sosnowskydevelop.tripmanager2.databinding.FragmentTripAddBinding
import com.sosnowskydevelop.tripmanager2.viewmodels.TripViewModelFactory
import com.sosnowskydevelop.tripmanager2.viewmodels.TripViewModel
import java.util.*

class TripAddFragment : Fragment() {
    private lateinit var binding: FragmentTripAddBinding

    private val viewModel: TripViewModel by viewModels {
        TripViewModelFactory((activity?.application as TripManagerApplication).tripRepository)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTripAddBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tripName.requestFocus()
        val inputMethodManager: InputMethodManager? =
            ContextCompat.getSystemService(requireContext(), InputMethodManager::class.java)
        inputMethodManager?.showSoftInput(binding.tripName, InputMethodManager.SHOW_IMPLICIT)

        binding.tripAdd.setOnClickListener {
            if (TextUtils.isEmpty(binding.tripName.text)) {
                Toast.makeText(
                    activity,
                    getString(R.string.trip_name_empty_toast),
                    Toast.LENGTH_LONG
                ).show()
            } else {
                viewModel.insert(
                    trip = Trip(
                        name = binding.tripName.text.toString(),
                        beginDate = Calendar.getInstance().time,
                    )
                )

                Toast.makeText(activity, getString(R.string.trip_added_toast), Toast.LENGTH_LONG)
                    .show()

                findNavController().navigate(R.id.action_tripAddFragment_to_tripListFragment)
            }
        }
    }
}