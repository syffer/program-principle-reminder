package com.syffer.pincreminder.presentation.principles

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.syffer.pincreminder.MainActivity
import com.syffer.pincreminder.data.entities.Principle
import com.syffer.pincreminder.databinding.FragmentPrinciplesBinding
import com.syffer.pincreminder.presentation.EventObserver
import com.syffer.pincreminder.presentation.getViewModel
import kotlinx.android.synthetic.main.fragment_principles.*

class PrinciplesFragment : Fragment() {

    private val viewmodel : PrinciplesViewModel by lazy {
        getViewModel {
            val activity = (this.activity as MainActivity)
            PrinciplesViewModel(activity.repository)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_principles, container, false)

        // when xxxBinding class are not generated
        // @see https://stackoverflow.com/questions/39483094/data-binding-class-not-generated
        val binding = FragmentPrinciplesBinding.inflate(inflater, container, false)
        binding.viewmodel = viewmodel
        binding.lifecycleOwner = this.viewLifecycleOwner

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setupListAdapter()
        setupNavigation()
    }

    private fun setupListAdapter() {
        val items = viewmodel.principles.value.orEmpty()
        val adapter = PrinciplesAdapter(items, object : PrinciplesAdapter.OnClickListener {
            override fun onPrincipleClick(principle: Principle) {
                openPrincipleDetails(principle)
            }
        })

        viewmodel.principles.observeForever { principles ->
            adapter.principles = principles
        }

        val layoutManager = LinearLayoutManager(context)
        recyclerView.layoutManager = layoutManager
        recyclerView.hasFixedSize()
        recyclerView.adapter = adapter
        recyclerView.addItemDecoration(DividerItemDecoration(context, layoutManager.orientation))
    }

    private fun openPrincipleDetails(principle: Principle) {
        val action = PrinciplesFragmentDirections.actionEdit(principle.id!!)
        findNavController().navigate(action)
    }

    private fun setupNavigation() {
        viewmodel.eventAddPrinciple.observe(this, EventObserver {
            val action = PrinciplesFragmentDirections.actionAdd()
            findNavController().navigate(action)
        })

        // @TODO handle eventEditPrinciple properly
    }

    override fun onDetach() {
        super.onDetach()
    }

}
