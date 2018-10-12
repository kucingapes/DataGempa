/*
 * GempaAdapter.kt on DataGempa
 * Developed by Muhammad Utsman on 10/12/18 4:54 PM
 * Last modified 10/12/18 4:52 PM
 * Copyright (c) 2018 kucingapes
 */

package com.kucingapes.datagempa.adapter

import android.annotation.SuppressLint
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.kucingapes.datagempa.ItemClick
import com.kucingapes.datagempa.R
import com.kucingapes.datagempa.model.DataGempa
import kotlinx.android.synthetic.main.item_gempa.view.*
import java.text.SimpleDateFormat
import java.util.*

class GempaAdapter(var dataList: ArrayList<DataGempa.Gempa>,
                   var itemClick: ItemClick) : RecyclerView.Adapter<GempaAdapter.Holder>() {

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): Holder {
        val view = LayoutInflater.from(p0.context).inflate(R.layout.item_gempa, p0, false)
        return Holder(view)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    @SuppressLint("SetTextI18n", "SimpleDateFormat")
    override fun onBindViewHolder(p0: Holder, p1: Int) {
        val dataGempa = dataList[p1]

        //07/10/2018-14:39:27 WIB

        var patternDate = ""
        var patternFormat = ""

        when {
            dataGempa.tanggal!!.contains("WIB") -> {
                patternDate = "dd/MM/yyyy-HH:mm:ss 'WIB'"
                patternFormat = "EEE dd MMM yyyy / HH:mm:ss 'WIB'"
            }
            dataGempa.tanggal!!.contains("WIT") -> {
                patternDate = "dd/MM/yyyy-HH:mm:ss 'WIT'"
                patternFormat = "EEE dd MMM yyyy / HH:mm:ss 'WIT'"
            }
            dataGempa.tanggal!!.contains("WITA") -> {
                patternDate = "dd/MM/yyyy-HH:mm:ss 'WITA'"
                patternFormat = "EEE dd MMM yyyy / HH:mm:ss 'WITA'"
            }
        }

        val parseDateFormat = SimpleDateFormat(patternDate)
        val dateFormat = SimpleDateFormat(patternFormat, Locale("id"))
        val date = parseDateFormat.parse(dataGempa.tanggal)

        p0.itemView.tanggal.text = dateFormat.format(date)
        p0.itemView.posisi.text = "Koordinat ${dataGempa.posisi}"
        p0.itemView.magnitude.text = "M ${dataGempa.magnitude}"
        p0.itemView.kedalaman.text = "Kedalaman ${dataGempa.kedalaman}"
        p0.itemView.keterangan.text = dataGempa.keterangan
        /*p0.itemView.dirasakan.text = dataGempa.dirasakan*/

        p0.setLocation(dataGempa)
        p0.itemView.setOnClickListener {
            itemClick.OnItemClickRecycler(dataGempa)
        }
    }

    class Holder(itemView: View) : RecyclerView.ViewHolder(itemView), OnMapReadyCallback {
        private var mapView: MapView = itemView.findViewById(R.id.map_item)
        var gMap: GoogleMap? = null

        lateinit var mapData: DataGempa.Gempa

        init {
            mapView.onCreate(null)
            mapView.getMapAsync(this)
        }

        override fun onMapReady(p0: GoogleMap?) {
            MapsInitializer.initialize(itemView.context)
            gMap = p0!!
            gMap!!.uiSettings.isMapToolbarEnabled = false
            updateLocation()
        }

        fun setLocation(mapLoc: DataGempa.Gempa) {
            mapData = mapLoc

            if (gMap != null) {
                updateLocation()
            }
        }

        private fun updateLocation() {
            gMap!!.clear()
            val stringLatlng = mapData.point?.coordinates?.split(",")
            val lat = stringLatlng?.get(0)?.toDouble()
            val lng = stringLatlng?.get(1)?.toDouble()
            val marker = LatLng(lat!!, lng!!)
            gMap!!.addMarker(MarkerOptions().position(marker).flat(true))
            gMap!!.moveCamera(CameraUpdateFactory.newLatLngZoom(marker, 5f))
        }
    }

}