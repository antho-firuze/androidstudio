package com.example.simpione

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
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet
import com.github.mikephil.charting.utils.ViewPortHandler
import com.github.mikephil.charting.animation.ChartAnimator
import com.github.mikephil.charting.interfaces.dataprovider.BarDataProvider
import com.github.mikephil.charting.renderer.BarChartRenderer



class Home : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        loadChart()
    }

    fun loadChart() {
        val data = CombinedData()

        data.setData(getLineData())
        data.setData(getBarData())

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
        mChart.setViewPortOffsets(0f, 0f, 0f, 0f)
//        mChart.setExtraOffsets(0f,0f,0f,-30f)

        mChart.setPinchZoom(false)

//        mChart.renderer.drawData()

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

    fun getLineData(): LineData {
        val entries:ArrayList<Entry> = ArrayList()
        for (i in 1..30) entries.add(Entry(i.toFloat(), (100..150).random().toFloat()))
//        for (i in 1..30) entries.add(Entry(i.toFloat(), (Math.random().toFloat() * 100)))

        val dataset = LineDataSet(entries, "")
        dataset.setDrawValues(false)
        dataset.setFillFormatter(IFillFormatter { dataSet, dataProvider -> return@IFillFormatter 0f })
        dataset.lineWidth = .7f
        dataset.circleRadius = 5f
        dataset.color = Color.parseColor("#FFFFFF")
        dataset.setDrawFilled(true)
        dataset.fillDrawable = getDrawable(R.drawable.gradient_white)
        dataset.setDrawCircleHole(false)
        dataset.setDrawCircles(false)
        dataset.highLightColor = Color.BLUE
        dataset.isHighlightEnabled = true
        dataset.setDrawHighlightIndicators(true)
        return LineData(dataset)
    }

    fun getBarData(): BarData {
        val entries:ArrayList<BarEntry> = ArrayList()
        for (i in 1..30) entries.add(BarEntry(i.toFloat(), (0..30).random().toFloat()))
//        for (i in 1..30) entries.add(BarEntry(i.toFloat(), (Math.random().toFloat() * 10)))

        val dataset = BarDataSet(entries, "")
        dataset.setDrawValues(false);

        return BarData(dataset)
    }

    inner class RoundedBarChartRenderer(
        chart: BarDataProvider,
        animator: ChartAnimator,
        viewPortHandler: ViewPortHandler
    ) : BarChartRenderer(chart, animator, viewPortHandler) {

        private var mRadius = 5f

        fun setmRadius(mRadius: Float) {
            this.mRadius = mRadius
        }

        protected override fun drawDataSet(c: Canvas, dataSet: IBarDataSet, index: Int) {

            val trans = mChart.getTransformer(dataSet.axisDependency)

            mShadowPaint.color = dataSet.barShadowColor

            val phaseX = mAnimator.phaseX
            val phaseY = mAnimator.phaseY


            // initialize the buffer
            val buffer = mBarBuffers[index]
            buffer.setPhases(phaseX, phaseY)
            buffer.setDataSet(index)
            buffer.setBarWidth(mChart.barData.barWidth)
            buffer.setInverted(mChart.isInverted(dataSet.axisDependency))

            buffer.feed(dataSet)

            trans.pointValuesToPixel(buffer.buffer)

            // if multiple colors
            if (dataSet.colors.size > 1) {

                var j = 0
                while (j < buffer.size()) {

                    if (!mViewPortHandler.isInBoundsLeft(buffer.buffer[j + 2])) {
                        j += 4
                        continue
                    }

                    if (!mViewPortHandler.isInBoundsRight(buffer.buffer[j]))
                        break

                    if (mChart.isDrawBarShadowEnabled) {
                        if (mRadius > 0)
                            c.drawRoundRect(
                                RectF(
                                    buffer.buffer[j], mViewPortHandler.contentTop(),
                                    buffer.buffer[j + 2],
                                    mViewPortHandler.contentBottom()
                                ), mRadius, mRadius, mShadowPaint
                            )
                        else
                            c.drawRect(
                                buffer.buffer[j], mViewPortHandler.contentTop(),
                                buffer.buffer[j + 2],
                                mViewPortHandler.contentBottom(), mShadowPaint
                            )
                    }

                    // Set the color for the currently drawn value. If the index
                    // is
                    // out of bounds, reuse colors.
                    mRenderPaint.color = dataSet.getColor(j / 4)
                    if (mRadius > 0)
                        c.drawRoundRect(
                            RectF(
                                buffer.buffer[j], buffer.buffer[j + 1], buffer.buffer[j + 2],
                                buffer.buffer[j + 3]
                            ), mRadius, mRadius, mRenderPaint
                        )
                    else
                        c.drawRect(
                            buffer.buffer[j], buffer.buffer[j + 1], buffer.buffer[j + 2],
                            buffer.buffer[j + 3], mRenderPaint
                        )
                    j += 4
                }
            } else {

                mRenderPaint.color = dataSet.color

                var j = 0
                while (j < buffer.size()) {

                    if (!mViewPortHandler.isInBoundsLeft(buffer.buffer[j + 2])) {
                        j += 4
                        continue
                    }

                    if (!mViewPortHandler.isInBoundsRight(buffer.buffer[j]))
                        break

                    if (mChart.isDrawBarShadowEnabled) {
                        if (mRadius > 0)
                            c.drawRoundRect(
                                RectF(
                                    buffer.buffer[j], mViewPortHandler.contentTop(),
                                    buffer.buffer[j + 2],
                                    mViewPortHandler.contentBottom()
                                ), mRadius, mRadius, mShadowPaint
                            )
                        else
                            c.drawRect(
                                buffer.buffer[j], buffer.buffer[j + 1], buffer.buffer[j + 2],
                                buffer.buffer[j + 3], mRenderPaint
                            )
                    }

                    if (mRadius > 0)
                        c.drawRoundRect(
                            RectF(
                                buffer.buffer[j], buffer.buffer[j + 1], buffer.buffer[j + 2],
                                buffer.buffer[j + 3]
                            ), mRadius, mRadius, mRenderPaint
                        )
                    else
                        c.drawRect(
                            buffer.buffer[j], buffer.buffer[j + 1], buffer.buffer[j + 2],
                            buffer.buffer[j + 3], mRenderPaint
                        )
                    j += 4
                }
            }
        }
    }
}
