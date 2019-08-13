package com.syffer.pincreminder.presentation.principles

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.syffer.pincreminder.data.entities.Principle
import com.syffer.pincreminder.databinding.PrincipleLayoutBinding


class PrinciplesAdapter(
    principles: List<Principle>,
    private val callback: OnClickListener
) : RecyclerView.Adapter<PrinciplesAdapter.ViewHolder>() {

    interface OnClickListener {
        fun onPrincipleClick(principle: Principle)
    }

    inner class ViewHolder(val binding: PrincipleLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(principle: Principle, callback: OnClickListener) {
            binding.principle = principle
            binding.listener = callback
        }
    }

    var principles = principles
        set(value) {
            field = value
            this.notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = PrincipleLayoutBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return principles.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(principles[position], callback)
    }

}