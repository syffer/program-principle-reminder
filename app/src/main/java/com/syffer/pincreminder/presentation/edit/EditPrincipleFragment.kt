package com.syffer.pincreminder.presentation.edit

import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.syffer.pincreminder.MainActivity
import com.syffer.pincreminder.R
import com.syffer.pincreminder.databinding.FragmentEditPrincipleBinding
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

        binding.lifecycleOwner = this

        return binding.root
    }
}
