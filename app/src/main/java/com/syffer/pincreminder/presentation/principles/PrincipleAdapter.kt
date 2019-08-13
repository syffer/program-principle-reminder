package com.syffer.pincreminder.presentation.principles

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.syffer.pincreminder.data.entities.Principle
import com.syffer.pincreminder.databinding.PrincipleLayoutBinding


class PrincipleAdapter(
    principles: List<Principle>
) : RecyclerView.Adapter<PrincipleAdapter.ViewHolder>() {

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
        holder.bind(principles[position])
    }

    inner class ViewHolder(val binding: PrincipleLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(principle: Principle) {
            binding.principle = principle
        }
    }

    /*
    override fun getView(position: Int, view: View?, viewGroup: ViewGroup?): View {
        val inflater = LayoutInflater.from(viewGroup!!.context)
        val binding = PrincipleLayoutBinding.inflate(inflater, viewGroup, false).apply {
            principle = principles[position]
        }

        return binding.root
    }

    override fun getItem(position: Int): Any {
        return principles[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
        //principles[position].id!!.toLong()
    }

    override fun getCount(): Int {
        return principles.size
    }
    */
}