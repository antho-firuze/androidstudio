package com.example.simpione

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import com.r0adkll.slidr.Slidr
import kotlinx.android.synthetic.main.activity_position_2.*
import kotlinx.android.synthetic.main.list_position.view.*

class Position2 : AppCompatActivity() {

    data class Portfolio(val name: String, val type: String, val value: String)

    // for array
    fun getData(datas: ArrayList<Portfolio>) {
        datas.clear()
        datas.add(Portfolio("PT Jasa Marga Indonesia", "Jalan Raya dan Trasportasi", "1.250.000"))
        datas.add(Portfolio("PT Telekomunikasi Indonesia", "Telekomunikasi", "1.125.000"))
        datas.add(Portfolio("PT Aneka Tambang", "Pertambangan", "750.000"))
        datas.add(Portfolio("PT Sumatera Plantation", "Perkebungan", "725.000"))
        Handler().postDelayed({
            if (::adapter.isInitialized) {
                adapter.notifyDataSetChanged()
            } else {
                recycleView.adapter = Position2.ListAdapter(portfolios)
            }

            isLoading = false
            progressbar.visibility = View.GONE
        }, 1000)
    }

    fun getData2(datas: ArrayList<Portfolio>) {
        datas.clear()
        datas.add(Portfolio("PT Jasa Marga Indonesia", "Jalan Raya dan Trasportasi", "1.30 %"))
        datas.add(Portfolio("PT Telekomunikasi Indonesia", "Telekomunikasi", "1.25 %"))
        datas.add(Portfolio("PT Aneka Tambang", "Pertambangan", "0.57 %"))
        datas.add(Portfolio("PT Sumatera Plantation", "Perkebungan", "0.75 %"))
        Handler().postDelayed({
            if (::adapter.isInitialized) {
                adapter.notifyDataSetChanged()
            } else {
                recycleView.adapter = Position2.ListAdapter(portfolios)
            }

            isLoading = false
            progressbar.visibility = View.GONE
        }, 1000)
    }

    fun getData3(datas: ArrayList<Portfolio>) {
        datas.clear()
        datas.add(Portfolio("PT Jasa Marga Indonesia", "Jalan Raya dan Trasportasi", "100"))
        datas.add(Portfolio("PT Telekomunikasi Indonesia", "Telekomunikasi", "90"))
        datas.add(Portfolio("PT Aneka Tambang", "Pertambangan", "70"))
        datas.add(Portfolio("PT Sumatera Plantation", "Perkebungan", "65"))
        Handler().postDelayed({
            if (::adapter.isInitialized) {
                adapter.notifyDataSetChanged()
            } else {
                recycleView.adapter = Position2.ListAdapter(portfolios)
            }

            isLoading = false
            progressbar.visibility = View.GONE
        }, 1000)
    }

    val portfolios: ArrayList<Portfolio> = ArrayList()

    var page = 1
    var isLoading = false
    var limit = 5

    lateinit var adapter: Position2.ListAdapter
    lateinit var layoutManager: LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_position_2)

        // for toolbar settings
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        Slidr.attach(this)

        layoutManager = LinearLayoutManager(this)
        recycleView.layoutManager = layoutManager

        getData(portfolios)

        segmentedButtonGroup.setPosition(0, 0)
        segmentedButtonGroup.setOnClickedButtonListener { position: Int ->
            when(position) {
                0 -> getData(portfolios)
                1 -> getData2(portfolios)
                2 -> getData3(portfolios)
            }
        }

//        getPage()
//        recycleView.addOnScrollListener(object: RecyclerView.OnScrollListener(){
//            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
//
//                val visibleItemCount = layoutManager.childCount
//                val pastVisibleItem  = layoutManager.findFirstCompletelyVisibleItemPosition()
//                val total = adapter.itemCount
//
//                if (! isLoading){
//                    if ((visibleItemCount + pastVisibleItem) >= total){
//                        page++
//                        getPage()
//                    }
//                }
//
//                super.onScrolled(recyclerView, dx, dy)
//            }
//        })

    }

    // function for back button on toolbar ( <- )
    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == android.R.id.home)
            this.finish()

        return super.onOptionsItemSelected(item)
    }

//    fun getPage(){
//        isLoading = true
//        progressbar.visibility = View.VISIBLE
//        val start = ((page - 1) * limit) + 1
//        val end = page * limit
//
//        for (i in start..end){
//            numberList.add("Item ${i.toString()}")
//        }
//
//        Handler().postDelayed({
//            if(::adapter.isInitialized) {
//                adapter.notifyDataSetChanged()
//            } else {
//                adapter = ListAdapter(this)
//                recycleView.adapter = adapter
//            }
//
//            isLoading = false
//            progressbar.visibility = View.GONE
//        }, 2000)
//    }

    class ListAdapter(val rows: ArrayList<Portfolio>) : RecyclerView.Adapter<ListAdapter.ListViewHolder>() {
        override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ListViewHolder {
            return ListViewHolder(LayoutInflater.from(p0.context).inflate(R.layout.list_position, p0, false))
        }

        override fun getItemCount(): Int {
            return rows.size
        }

        override fun onBindViewHolder(p0: ListViewHolder, p1: Int) {
            val r = rows[p1]
            if (p1 % 2 != 0){
                p0.itemView.apply {
                    tv_name.background = ContextCompat.getDrawable(context, R.drawable.transparent_to_white)
                    tv_type.background = ContextCompat.getDrawable(context, R.drawable.transparent_to_white)
                    tv_value.background = ContextCompat.getDrawable(context, R.drawable.transparent_to_white)
                }
            }

            p0.itemView.apply {
                tv_name.text = r.name
                tv_type.text = r.type
                tv_value.text = r.value.toString()
            }
        }

        class ListViewHolder(v: View) : RecyclerView.ViewHolder(v) {}
    }

}
