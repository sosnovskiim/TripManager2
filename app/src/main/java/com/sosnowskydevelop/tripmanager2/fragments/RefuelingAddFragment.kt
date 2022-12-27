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
import androidx.navigation.fragment.navArgs
import com.sosnowskydevelop.tripmanager2.R
import com.sosnowskydevelop.tripmanager2.TripManagerApplication
import com.sosnowskydevelop.tripmanager2.data.Refueling
import com.sosnowskydevelop.tripmanager2.databinding.FragmentRefuelingAddBinding
import com.sosnowskydevelop.tripmanager2.viewmodels.RefuelingViewModel
import com.sosnowskydevelop.tripmanager2.viewmodels.RefuelingViewModelFactory

class RefuelingAddFragment : Fragment() {
    private lateinit var binding: FragmentRefuelingAddBinding

    private val viewModel: RefuelingViewModel by viewModels {
        RefuelingViewModelFactory((activity?.application as TripManagerApplication).refuelingRepository)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRefuelingAddBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val args: RefuelingAddFragmentArgs by navArgs()
        val tripId = args.tripId

        binding.refuelingOdometer.requestFocus()
        val inputMethodManager: InputMethodManager? =
            ContextCompat.getSystemService(requireContext(), InputMethodManager::class.java)
        inputMethodManager?.showSoftInput(
            binding.refuelingOdometer,
            InputMethodManager.SHOW_IMPLICIT
        )

        binding.refuelingAdd.setOnClickListener {
            if (TextUtils.isEmpty(binding.refuelingOdometer.text)) {
                Toast.makeText(
                    activity,
                    getString(R.string.refueling_odometer_empty_toast),
                    Toast.LENGTH_LONG
                ).show()
            } else if (TextUtils.isEmpty(binding.refuelingLitersFilled.text)) {
                Toast.makeText(
                    activity,
                    getString(R.string.refueling_liters_filled_empty_toast),
                    Toast.LENGTH_LONG
                ).show()
            } else {
                val refueling = Refueling(
                    tripId = tripId,
                    odometer = binding.refuelingOdometer.text.toString(),
                    litersFilled = binding.refuelingLitersFilled.text.toString(),
                    pricePerLiter = binding.refuelingPricePerLiter.text.toString(),
                    isToFull = binding.refuelingIsToFull.isChecked
                )

                viewModel.insert(refueling = refueling)

                Toast.makeText(
                    activity,
                    getString(R.string.refueling_added_toast),
                    Toast.LENGTH_LONG
                ).show()

                findNavController().navigate(
                    RefuelingAddFragmentDirections.actionRefuelingAddFragmentToRefuelingListFragment(
                        tripId = tripId
                    )
                )
            }
        }
    }
}