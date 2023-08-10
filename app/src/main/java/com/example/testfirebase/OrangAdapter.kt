package com.example.testfirebase

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.testfirebase.databinding.ItemTestBinding
import com.example.testfirebase.model.Orang

class OrangAdapter(private val orang: List<Orang>) :
    RecyclerView.Adapter<OrangAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: ItemTestBinding) : RecyclerView.ViewHolder(binding.root) {
            fun bind(orang: Orang) {
                binding.etNama.text = orang.nama
                binding.etUmur.text = orang.umur.toString()
                binding.etEmail.text = orang.email
            }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemTestBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(orang[position])
    }

    override fun getItemCount(): Int {
        return orang.size
    }

}