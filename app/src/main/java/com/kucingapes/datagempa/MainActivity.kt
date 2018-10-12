/*
 * MainActivity.kt on DataGempa
 * Developed by Muhammad Utsman on 10/12/18 7:46 AM
 * Last modified 10/12/18 7:46 AM
 * Copyright (c) 2018 kucingapes
 */

package com.kucingapes.datagempa

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.kucingapes.datagempa.adapter.GempaAdapter
import com.kucingapes.datagempa.api.ApiInstance
import com.kucingapes.datagempa.model.DataGempa
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private val itemClick = object : ItemClick {
        override fun OnItemClickRecycler(gempa: DataGempa.Gempa) {
            val snackbar = Snackbar.make(main_layout, "Dirasakan (skala MMI): ${gempa.dirasakan}", Snackbar.LENGTH_INDEFINITE)
            val snackView = snackbar.view
            val textMsg = snackView.findViewById<TextView>(android.support.design.R.id.snackbar_text)
            textMsg.maxLines = 20
            snackbar.setAction("Tutup") {
                snackbar.dismiss()
            }
            snackbar.show()
        }
    }

    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initToolbar()

        val getData = ApiInstance.create().getData()
        getData.enqueue(object : Callback<DataGempa.Infogempa> {
            override fun onFailure(call: Call<DataGempa.Infogempa>, t: Throwable) {
                onError(t)
            }

            override fun onResponse(call: Call<DataGempa.Infogempa>, response: Response<DataGempa.Infogempa>) {
                val layoutManager = LinearLayoutManager(this@MainActivity)
                val offside = ItemOffsetDecoration(20)
                val adapter = GempaAdapter(response.body()!!.gempa!!, itemClick)
                list_gempa.apply {
                    setLayoutManager(layoutManager)
                    addItemDecoration(offside)
                    setAdapter(adapter)
                }
                progress_loader.visibility = View.GONE
            }
        })

    }

    private fun initToolbar() {
        toolbar.inflateMenu(R.menu.main_menu)
        val menu = toolbar.menu.findItem(R.id.github_menu)
        menu.setOnMenuItemClickListener {
            val intent = Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse("https://github.com")
            }
            startActivity(intent)
            true
        }
    }

    private fun onError(it: Throwable?) {
        Log.d("anjir", it!!.message)
    }
}
