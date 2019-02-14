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

    val numberList: MutableList<String> = ArrayList()

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

        getPage()

        recycleView.addOnScrollListener(object: RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {

                val visibleItemCount = layoutManager.childCount
                val pastVisibleItem  = layoutManager.findFirstCompletelyVisibleItemPosition()
                val total = adapter.itemCount

                if (! isLoading){
                    if ((visibleItemCount + pastVisibleItem) >= total){
                        page++
                        getPage()
                    }
                }

                super.onScrolled(recyclerView, dx, dy)
            }
        })

    }

    // function for back button on toolbar ( <- )
    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == android.R.id.home)
            this.finish()

        return super.onOptionsItemSelected(item)
    }

    fun getPage(){
        isLoading = true
        progressbar.visibility = View.VISIBLE
        val start = ((page - 1) * limit) + 1
        val end = page * limit

        for (i in start..end){
            numberList.add("Item ${i.toString()}")
        }

        Handler().postDelayed({
            if(::adapter.isInitialized) {
                adapter.notifyDataSetChanged()
            } else {
                adapter = ListAdapter(this)
                recycleView.adapter = adapter
            }

            isLoading = false
            progressbar.visibility = View.GONE
        }, 2000)
    }

    class ListAdapter(val activity: History) : RecyclerView.Adapter<ListAdapter.ListViewHolder>() {
        override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ListViewHolder {
            return ListViewHolder(LayoutInflater.from(activity).inflate(R.layout.list_history, p0, false))
        }

        override fun getItemCount(): Int {
            return activity.numberList.size
        }

        override fun onBindViewHolder(p0: ListViewHolder, p1: Int) {
            p0.itemView.tv_value.text = activity.numberList[p1]
        }


        class ListViewHolder(v: View) : RecyclerView.ViewHolder(v) {

        }
    }
}
