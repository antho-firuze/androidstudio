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
import kotlinx.android.synthetic.main.activity_position.*
import kotlinx.android.synthetic.main.list_position.view.*
import org.json.JSONObject

class Position : AppCompatActivity() {

    data class Portfolio(val name: String, val type: String, val value: Float)

    // for array
    fun getData(datas: ArrayList<Portfolio>) {
        datas.add(Portfolio("PT Jasa Marga Indonesia", "Jalan Raya dan Trasportasi", 1250000f))
        datas.add(Portfolio("PT Telekomunikasi Indonesia", "Telekomunikasi", 1125000F))
        datas.add(Portfolio("PT Aneka Tambang", "Pertambangan", 750000F))
        datas.add(Portfolio("PT Sumatera Plantation", "Perkebungan", 725000F))
        Handler().postDelayed({
            if (::adapter.isInitialized) {
                adapter.notifyDataSetChanged()
            } else {
                recycleView.adapter = Position.ListAdapter(portfolios)
            }

            isLoading = false
            progressbar.visibility = View.GONE
        }, 2000)
    }

    // for map
//    fun getData2(datas: MutableMap<Int, Portfolio>) {
//        datas[0] = Portfolio("PT Jasa Marga Indonesia", "Jalan Raya dan Trasportasi", 1250000f)
//        datas[1] = Portfolio("PT Telekomunikasi Indonesia", "Telekomunikasi", 1125000F)
//        datas[2] = Portfolio("PT Aneka Tambang", "Pertambangan", 750000F)
//        datas[3] = Portfolio("PT Sumatera Plantation", "Perkebungan", 725000F)
//        Handler().postDelayed({
//            if (::adapter.isInitialized) {
//                adapter.notifyDataSetChanged()
//            } else {
//                recycleView.adapter = Position.ListAdapter2(portfolios2)
//            }
//
//            isLoading = false
//            progressbar.visibility = View.GONE
//        }, 2000)
//    }

    // for JSONObject
//    fun getData3(datas: JSONObject) {
//        datas.put("0", Portfolio("PT Jasa Marga Indonesia", "Jalan Raya dan Trasportasi", 1250000f))
//        datas.put("1", Portfolio("PT Telekomunikasi Indonesia", "Telekomunikasi", 1125000F))
//        datas.put("2", Portfolio("PT Aneka Tambang", "Pertambangan", 750000F))
//        datas.put("3", Portfolio("PT Sumatera Plantation", "Perkebungan", 725000F))
//
//        Handler().postDelayed({
//            if (::adapter.isInitialized) {
//                adapter.notifyDataSetChanged()
//            } else {
//                recycleView.adapter = Position.ListAdapter2(portfolios2)
//            }
//
//            isLoading = false
//            progressbar.visibility = View.GONE
//        }, 2000)
//    }

    //    val numberList: MutableList<String> = ArrayList()
    val portfolios: ArrayList<Portfolio> = ArrayList()
//    val portfolios2: MutableMap<Int, Portfolio> = mutableMapOf()
//    val portfolios3 = JSONObject()

    var page = 1
    var isLoading = false
    var limit = 5

    lateinit var adapter: Position.ListAdapter
    lateinit var layoutManager: LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_position)

        // for toolbar settings
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        Slidr.attach(this)

        layoutManager = LinearLayoutManager(this)
        recycleView.layoutManager = layoutManager

        getData(portfolios)
//        getData2(portfolios2)

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
            if (p1 % 2 == 0){
//                p0.itemView.layout_border.background = ContextCompat.getDrawable(p0.itemView.context, R.drawable.border_list)
            } else {
                p0.itemView.tv_name.background = ContextCompat.getDrawable(p0.itemView.context, R.drawable.transparent_to_white)
                p0.itemView.tv_type.background = ContextCompat.getDrawable(p0.itemView.context, R.drawable.transparent_to_white)
                p0.itemView.tv_value.background = ContextCompat.getDrawable(p0.itemView.context, R.drawable.transparent_to_white)
//                p0.itemView.tv_name.background = ContextCompat.getDrawable(p0.itemView.context, R.color.colorPrimaryDark)
//                p0.itemView.tv_type.background = ContextCompat.getDrawable(p0.itemView.context, R.color.colorPrimaryDark)
//                p0.itemView.tv_value.background = ContextCompat.getDrawable(p0.itemView.context, R.color.colorPrimaryDark)
            }

            p0.itemView.tv_name.text = r.name
            p0.itemView.tv_type.text = r.type
            p0.itemView.tv_value.text = r.value.toString()
        }

        class ListViewHolder(v: View) : RecyclerView.ViewHolder(v) {}
    }

    class ListAdapter2(val rows: MutableMap<Int, Portfolio>) : RecyclerView.Adapter<ListAdapter2.ListViewHolder>() {
        override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ListViewHolder {
            return ListViewHolder(LayoutInflater.from(p0.context).inflate(R.layout.list_position, p0, false))
        }

        override fun getItemCount(): Int {
            return rows.size
        }

        override fun onBindViewHolder(p0: ListViewHolder, p1: Int) {
            val r = rows[p1]
            p0.itemView.tv_name.text = r?.name
            p0.itemView.tv_type.text = r?.type
            p0.itemView.tv_value.text = r?.value.toString()
        }

        class ListViewHolder(v: View) : RecyclerView.ViewHolder(v) {}
    }

}
