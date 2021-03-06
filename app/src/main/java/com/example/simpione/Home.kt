package com.example.simpione

import android.content.Intent
import android.graphics.Canvas
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.formatter.IFillFormatter
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import kotlinx.android.synthetic.main.activity_home.*
import java.util.*
import android.graphics.RectF
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.Toast
import co.ceryle.segmentedbutton.SegmentedButton
import co.ceryle.segmentedbutton.SegmentedButtonGroup
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet
import com.github.mikephil.charting.utils.ViewPortHandler
import com.github.mikephil.charting.animation.ChartAnimator
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.interfaces.dataprovider.BarDataProvider
import com.github.mikephil.charting.renderer.BarChartRenderer


class Home : AppCompatActivity(), View.OnClickListener {
    override fun onClick(v: View?) {
        Toast.makeText(this, v?.id.toString(), Toast.LENGTH_SHORT).show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        // for toolbar settings
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayShowTitleEnabled(false)

        val toggle = ActionBarDrawerToggle(this, drawer_layout, toolbar, R.string.drawer_open, R.string.drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        loadChart("month")
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.right_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    fun onItemSelected(p0: MenuItem): Boolean {
        when (p0.itemId) {
            R.id.menu_history -> {
                startActivity(Intent(this, History::class.java))
            }
            R.id.menu_position -> {
                startActivity(Intent(this, Position::class.java))
            }
            R.id.menu_c -> {
                Toast.makeText(this, p0.title.toString(), Toast.LENGTH_SHORT).show()
            }
            R.id.action_settings -> {
                Toast.makeText(this, p0.title.toString(), Toast.LENGTH_SHORT).show()
            }
        }
        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    fun onButtonClick(p0: View?) {
        when (p0?.id) {
            R.id.btn_month -> {
                loadChart("month")
            }
            R.id.btn_year -> {
                loadChart("year")
            }
            R.id.btn_frombeginning -> {
                loadChart("all")
            }
        }
    }

    fun loadChart(period: String) {
        val data = CombinedData()

        data.setData(getLineData(period))
        data.setData(getBarData(period))

        mChart.data = data
        mChart.xAxis.labelRotationAngle = 45F
        mChart.xAxis.position = XAxis.XAxisPosition.BOTTOM
        mChart.xAxis.axisLineColor = resources.getColor(R.color.colorPrimaryDark)//top line
        mChart.xAxis.textColor = resources.getColor(R.color.colorPrimaryDark)

        mChart.xAxis.setDrawGridLines(false)
        mChart.axisRight.setDrawGridLines(false)
        mChart.axisLeft.setDrawGridLines(false)
        mChart.axisLeft.axisLineColor = resources.getColor(R.color.colorPrimaryDark)//left line
        mChart.axisLeft.textColor = resources.getColor(R.color.colorPrimaryDark)
        mChart.axisRight.axisLineColor = resources.getColor(R.color.colorPrimaryDark)//right line
        mChart.axisRight.textColor = resources.getColor(R.color.colorPrimaryDark)
//        mChart.setDrawBorders(true)
//        mChart.setBorderColor(Color.rgb(240, 238, 70))
        mChart.setDrawGridBackground(false)
        mChart.isAutoScaleMinMaxEnabled = false
        mChart.setViewPortOffsets(0f, 0f, 0f, -40f)
//        mChart.setExtraOffsets(0f,0f,0f,-30f)

        mChart.isDoubleTapToZoomEnabled = false
        mChart.setPinchZoom(false)

//        mChart.renderer.drawData()
        mChart.extraBottomOffset = -8f

        mChart.legend.isEnabled = false
        mChart.legend.position = Legend.LegendPosition.ABOVE_CHART_RIGHT
        mChart.description.text = ""
        mChart.invalidate()
        mChart.animateXY(1500, 1500)

//        val labels = rows.map { DateFmt.format(it.PositionDate) }
//        mChart.xAxis.setValueFormatter(IndexAxisValueFormatter(labels))

        mChart.setOnChartValueSelectedListener(object : OnChartValueSelectedListener {
            override fun onNothingSelected() {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onValueSelected(e: Entry?, h: Highlight?) {
                if (e != null)
                    text_subsvalue.text = "IDR ${e.y},-"
//                    toast("${labels[e.x.toInt()]} : ${PercentFmt.format(e.y / 100)}")
            }
        })
    }

    fun getLineData(period: String): LineData {
        val entries:ArrayList<Entry> = ArrayList()
        if (period == "month")
            for (i in 1..30) entries.add(Entry(i.toFloat(), (100..150).random().toFloat()))
        else if(period == "year")
            for (i in 1..365) entries.add(Entry(i.toFloat(), (100..150).random().toFloat()))
        else
            for (i in 1..600) entries.add(Entry(i.toFloat(), (100..150).random().toFloat()))


//        for (i in 1..30) entries.add(Entry(i.toFloat(), (Math.random().toFloat() * 100)))

        val dataset = LineDataSet(entries, "")
        dataset.setDrawValues(false)
        dataset.setFillFormatter(IFillFormatter { dataSet, dataProvider -> return@IFillFormatter 0f })
        dataset.lineWidth = 2f
        dataset.circleRadius = 5f
        dataset.color = Color.parseColor("#FFFFFF")
        dataset.setDrawFilled(true)
        dataset.fillDrawable = getDrawable(R.drawable.gradient_to_white)
        dataset.setDrawCircleHole(false)
        dataset.setDrawCircles(false)
        dataset.highLightColor = Color.WHITE
        dataset.isHighlightEnabled = true
        dataset.setDrawHighlightIndicators(true)
        dataset.mode = LineDataSet.Mode.CUBIC_BEZIER    // draw smooth curves
        return LineData(dataset)
    }

    fun getBarData(period: String): BarData {
        val entries:ArrayList<BarEntry> = ArrayList()
        if (period == "month")
            for (i in 1..30) entries.add(BarEntry(i.toFloat(), (0..30).random().toFloat()))
        else if(period == "year")
            for (i in 1..365) entries.add(BarEntry(i.toFloat(), (0..30).random().toFloat()))
        else
            for (i in 1..600) entries.add(BarEntry(i.toFloat(), (0..30).random().toFloat()))

//        for (i in 1..30) entries.add(BarEntry(i.toFloat(), (Math.random().toFloat() * 10)))

        val dataset = BarDataSet(entries, "")
        dataset.setDrawValues(false)

        return BarData(dataset)
    }

}
