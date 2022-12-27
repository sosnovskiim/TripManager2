package com.sosnowskydevelop.tripmanager2.fragments

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import com.sosnowskydevelop.tripmanager2.R
import com.sosnowskydevelop.tripmanager2.TripManagerApplication
import com.sosnowskydevelop.tripmanager2.adapters.RefuelingListAdapter
import com.sosnowskydevelop.tripmanager2.data.Trip
import com.sosnowskydevelop.tripmanager2.databinding.FragmentRefuelingListBinding
import com.sosnowskydevelop.tripmanager2.viewmodels.RefuelingViewModel
import com.sosnowskydevelop.tripmanager2.viewmodels.RefuelingViewModelFactory
import com.sosnowskydevelop.tripmanager2.viewmodels.TripViewModel
import com.sosnowskydevelop.tripmanager2.viewmodels.TripViewModelFactory

class RefuelingListFragment : Fragment() {
    private lateinit var binding: FragmentRefuelingListBinding
    private lateinit var currentTrip: Trip

    private val refuelingViewModel: RefuelingViewModel by viewModels {
        RefuelingViewModelFactory((activity?.application as TripManagerApplication).refuelingRepository)
    }

    private val tripViewModel: TripViewModel by viewModels {
        TripViewModelFactory((activity?.application as TripManagerApplication).tripRepository)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRefuelingListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val args: RefuelingListFragmentArgs by navArgs()
        val tripId = args.tripId

        val refuelingLayoutManager = GridLayoutManager(requireContext(), 1)
        binding.refuelingList.layoutManager = refuelingLayoutManager
        val refuelingListAdapter = RefuelingListAdapter()
        binding.refuelingList.adapter = refuelingListAdapter
        binding.refuelingList.addItemDecoration(
            DividerItemDecoration(
                requireContext(),
                refuelingLayoutManager.orientation
            )
        )

        refuelingViewModel.getRefuelingList(tripId = tripId)
            .observe(viewLifecycleOwner) { refuelingList ->
                refuelingList?.let { refuelingListAdapter.submitList(refuelingList) }

                binding.refuelingAverageFuelConsumptionValue.text =
                    refuelingViewModel.getAverageFuelConsumption(refuelingList = refuelingList)
            }

        tripViewModel.getTrip(id = tripId).observe(viewLifecycleOwner) {
            currentTrip = it
        }

        binding.refuelingAdd.setOnClickListener {
            findNavController().navigate(
                RefuelingListFragmentDirections.actionRefuelingListFragmentToRefuelingAddFragment(
                    tripId = tripId
                )
            )
        }

        binding.tripDelete.setOnClickListener {
            lateinit var dialog: AlertDialog
            val builder = AlertDialog.Builder(requireContext())
            builder.setTitle(getString(R.string.trip_delete_dialog_title))
            builder.setMessage(getString(R.string.trip_delete_dialog_message) + ' ' + currentTrip.name + '?')
            val dialogClickListener = DialogInterface.OnClickListener { _, i ->
                when (i) {
                    DialogInterface.BUTTON_POSITIVE -> {
                        tripViewModel.delete(trip = currentTrip)

                        Toast.makeText(
                            activity,
                            getString(R.string.trip_deleted_toast),
                            Toast.LENGTH_LONG
                        ).show()

                        findNavController().navigate(R.id.action_refuelingListFragment_to_tripListFragment)
                    }
                    DialogInterface.BUTTON_NEGATIVE -> dialog.dismiss()
                }
            }
            builder.setPositiveButton(
                getString(R.string.trip_delete_dialog_positive),
                dialogClickListener
            )
            builder.setNegativeButton(
                getString(R.string.trip_delete_dialog_negative),
                dialogClickListener
            )
            dialog = builder.create()
            dialog.show()
        }
    }
}