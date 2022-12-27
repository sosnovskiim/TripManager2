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
import com.sosnowskydevelop.tripmanager2.NOT_TO_FULL_LOWER
import com.sosnowskydevelop.tripmanager2.R
import com.sosnowskydevelop.tripmanager2.TO_FULL_LOWER
import com.sosnowskydevelop.tripmanager2.TripManagerApplication
import com.sosnowskydevelop.tripmanager2.databinding.FragmentRefuelingDetailBinding
import com.sosnowskydevelop.tripmanager2.viewmodels.RefuelingViewModel
import com.sosnowskydevelop.tripmanager2.viewmodels.RefuelingViewModelFactory

class RefuelingDetailFragment : Fragment() {
    private lateinit var binding: FragmentRefuelingDetailBinding

    private val refuelingViewModel: RefuelingViewModel by viewModels {
        RefuelingViewModelFactory((activity?.application as TripManagerApplication).refuelingRepository)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRefuelingDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val args: RefuelingDetailFragmentArgs by navArgs()
        val currentRefueling = args.refueling

        binding.refuelingOdometerValue.text =
            currentRefueling.odometer
        binding.refuelingLitersFilledValue.text =
            String.format("%.2f", currentRefueling.litersFilled.toFloat())
        binding.refuelingPricePerLiterValue.text =
            String.format("%.2f", currentRefueling.pricePerLiter.toFloat())
        binding.refuelingIsToFullValue.text =
            if (currentRefueling.isToFull) {
                TO_FULL_LOWER
            } else {
                NOT_TO_FULL_LOWER
            }

        binding.refuelingDelete.setOnClickListener {
            lateinit var dialog: AlertDialog
            val builder = AlertDialog.Builder(requireContext())
            builder.setTitle(getString(R.string.refueling_delete_dialog_title))
            builder.setMessage(getString(R.string.refueling_delete_dialog_message) + ' ' + currentRefueling.odometer + '?')
            val dialogClickListener = DialogInterface.OnClickListener { _, i ->
                when (i) {
                    DialogInterface.BUTTON_POSITIVE -> {
                        refuelingViewModel.delete(refueling = currentRefueling)

                        Toast.makeText(
                            activity,
                            getString(R.string.refueling_deleted_toast),
                            Toast.LENGTH_LONG
                        ).show()

                        findNavController().navigate(
                            RefuelingDetailFragmentDirections.actionRefuelingDetailFragmentToRefuelingListFragment(
                                tripId = currentRefueling.tripId
                            )
                        )
                    }
                    DialogInterface.BUTTON_NEGATIVE -> dialog.dismiss()
                }
            }
            builder.setPositiveButton(
                getString(R.string.refueling_delete_dialog_positive),
                dialogClickListener
            )
            builder.setNegativeButton(
                getString(R.string.refueling_delete_dialog_negative),
                dialogClickListener
            )
            dialog = builder.create()
            dialog.show()
        }
    }
}