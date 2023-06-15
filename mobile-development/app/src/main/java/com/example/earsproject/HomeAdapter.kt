package com.example.earsproject

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.imageview.ShapeableImageView
import com.squareup.picasso.Picasso
import org.w3c.dom.Text

class HomeAdapter(private val hospitals: List<Hospital>) : RecyclerView.Adapter<HomeAdapter.HomeViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.card_home, parent, false)
        return HomeViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        val hospital = hospitals[position]
        holder.bind(hospital)
    }

    override fun getItemCount(): Int {
        return hospitals.size
    }

    inner class HomeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(hospital: Hospital) {
            val tvNamaRs : TextView = itemView.findViewById(R.id.txtNamaRumahSakit)
            val tvsSpesialis : TextView = itemView.findViewById(R.id.txtSpesialis)
            val tvAnsuransi: TextView = itemView.findViewById(R.id.txtAsuransi)
            val tvAlamat : TextView = itemView.findViewById(R.id.txtAlamat)
            val ImgView : ShapeableImageView = itemView.findViewById(R.id.imageHome)
        }
    }
}