package com.syffer.pincreminder.presentation.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.syffer.pincreminder.MainActivity
import com.syffer.pincreminder.databinding.FragmentPrincipleDetailBinding
import com.syffer.pincreminder.presentation.EventObserver
import com.syffer.pincreminder.presentation.getViewModel
import java.util.logging.Logger

class PrincipleDetailFragment : Fragment() {

    private val logger  = Logger.getLogger(this::class.java.name)

    private val args: PrincipleDetailFragmentArgs by navArgs()

    private val vm by lazy {

        logger.info("$args")

        getViewModel {
            val activity = (this.activity as MainActivity)
            PrincipleDetailViewModel(activity.repository, args.principleId)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = FragmentPrincipleDetailBinding.inflate(inflater, container, false).apply {
            viewmodel = vm
        }
        binding.lifecycleOwner = this.viewLifecycleOwner

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupNavigation()
    }

    private fun setupNavigation() {
        vm.eventEditPrinciple.observe(this, EventObserver {
            val action = PrincipleDetailFragmentDirections.actionEdit(args.principleId)
            findNavController().navigate(action)
        })
    }
}
