package com.example.smartactivitytracker.fragments

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.smartactivitytracker.R
import com.example.smartactivitytracker.databinding.FragmentPredictBinding
import com.example.smartactivitytracker.databinding.FragmentStatisticBinding
import com.example.smartactivitytracker.entities.Days
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet


class PredictFragment : Fragment() {

    private lateinit var lineChart: LineChart
    private lateinit var scoreList: ArrayList<Days>


    private lateinit var binding: FragmentPredictBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentPredictBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lineChart = binding.lineChartPredict
        initLineChart()

        binding.button.setOnClickListener {
            updateGraph()
        }


    }





    private fun initLineChart() {

//        hide grid lines
        lineChart.axisLeft.setDrawGridLines(false)
        val xAxis: XAxis = lineChart.xAxis
        xAxis.setDrawGridLines(false)
        xAxis.setDrawAxisLine(false)

        //remove right y-axis
        lineChart.axisRight.isEnabled = false

        //remove legend
        lineChart.legend.isEnabled = false


        //remove description label
        lineChart.description.isEnabled = false


        //add animation
        lineChart.animateX(1000, Easing.EaseInSine)

        // to draw label on xAxis
        xAxis.position = XAxis.XAxisPosition.BOTTOM_INSIDE
        xAxis.valueFormatter = MyAxisFormatter()
        xAxis.setDrawLabels(true)
        xAxis.granularity = 1f
//        xAxis.labelRotationAngle = +90f

    }


    inner class MyAxisFormatter : IndexAxisValueFormatter() {

        override fun getAxisLabel(value: Float, axis: AxisBase?): String {
            val index = value.toInt()
            return ""
//            if (index < scoreList.size) {
//                scoreList[index].id.toString()
//            } else {
//                ""
//            }
        }
    }


    private fun updateGraph() {
        val entries1: ArrayList<Entry> = ArrayList()

        entries1.add(Entry(1f, 26.7f))
        entries1.add(Entry(2f, 23.7f))
        entries1.add(Entry(3f, 24.7f))
        entries1.add(Entry(4f, 24.45f))


        val entries2: ArrayList<Entry> = ArrayList()

        entries2.add(Entry(1f, 364f))
        entries2.add(Entry(2f, 574f))
        entries2.add(Entry(3f, 324f))
        entries2.add(Entry(4f, 64f))



        //you can replace this data object with  your custom object


        val lineDataSet1 = LineDataSet(entries1, "температура")
        val lineDataSet2 = LineDataSet(entries2, "пульс")

        lineDataSet2.color = Color.RED

        val dataList: ArrayList<LineDataSet> = ArrayList()
        dataList.add(lineDataSet1)
        dataList.add(lineDataSet2)

        val data = LineData(dataList as List<ILineDataSet>?)
        lineChart.data = data

        lineChart.invalidate()
    }

    companion object {

        @JvmStatic
        fun newInstance() = PredictFragment()

    }
}