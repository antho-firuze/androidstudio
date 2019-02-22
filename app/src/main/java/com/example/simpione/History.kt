package com.example.simpione

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import com.r0adkll.slidr.Slidr
import kotlinx.android.synthetic.main.activity_history.*
import kotlinx.android.synthetic.main.list_history.view.*

class History : AppCompatActivity() {

    val histories: ArrayList<Histori> = ArrayList()
    data class Histori(val date: String, val type: String, val value: String)

    // for array
    fun getData(datas: ArrayList<Histori>) {
        datas.add(Histori("Hari ini", "Subscription", "IDR 125.000"))
        datas.add(Histori("Kemarin", "Devidend", "IDR 11.250"))
        datas.add(Histori("2 hari yang lalu", "Redemption", "IDR 1.550.000"))
        datas.add(Histori("3 hari yang lalu", "Subscription", "IDR 100.000"))
        datas.add(Histori("17 Feb 2019", "Devidend", "IDR 10.000"))
        Handler().postDelayed({
            if (::adapter.isInitialized) {
                adapter.notifyDataSetChanged()
            } else {
                recycleView.adapter = History.ListAdapter(histories)
            }

            isLoading = false
            progressbar.visibility = View.GONE
        }, 2000)
    }

//    val numberList: MutableList<String> = ArrayList()

    var page = 1
    var isLoading = false
    var limit = 5

    lateinit var adapter: ListAdapter
    lateinit var layoutManager: LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)

        // for toolbar settings
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        Slidr.attach(this)

        layoutManager = LinearLayoutManager(this)
        recycleView.layoutManager = layoutManager

        getData(histories)
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

    class ListAdapter(val rows: ArrayList<Histori>) : RecyclerView.Adapter<ListAdapter.ListViewHolder>() {
        override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ListViewHolder {
            return ListViewHolder(LayoutInflater.from(p0.context).inflate(R.layout.list_history, p0, false))
        }

        override fun getItemCount(): Int {
            return rows.size
        }

        override fun onBindViewHolder(p0: ListViewHolder, p1: Int) {
            val r = rows[p1]
            p0.itemView.apply {
                tv_date.text = r.date
                tv_type.text = r.type
                tv_value.text = r.value
            }
        }


        class ListViewHolder(v: View) : RecyclerView.ViewHolder(v) {}
    }
}
