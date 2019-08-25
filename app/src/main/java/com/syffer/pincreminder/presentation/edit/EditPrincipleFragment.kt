package com.syffer.pincreminder.presentation.edit

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.syffer.pincreminder.MainActivity
import com.syffer.pincreminder.databinding.FragmentEditPrincipleBinding
import com.syffer.pincreminder.presentation.EventObserver
import com.syffer.pincreminder.presentation.getViewModel


class EditPrincipleFragment : Fragment() {

    private val args : EditPrincipleFragmentArgs by navArgs()

    private val vm by lazy {
        getViewModel {
            val activity = (this.activity as MainActivity)
            EditPrincipleViewModel(activity.repository, args.principleId)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = FragmentEditPrincipleBinding.inflate(inflater, container, false).apply {
            viewmodel = vm
        }

        binding.lifecycleOwner = this.viewLifecycleOwner

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupNavigation()
        vm.updateData()
    }

    private fun setupNavigation() {
        vm.eventSavePrinciple.observe(this, EventObserver {
            // @TODO close keyboard when navigating
            // @see https://stackoverflow.com/questions/26911469/hide-keyboard-when-navigating-from-a-fragment-to-another

            findNavController().navigateUp()
        })
    }
}
