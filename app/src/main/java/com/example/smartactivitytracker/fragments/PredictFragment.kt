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

        binding.btnPredHeart.setOnClickListener {
            binding.tvPredType.text = "Пульс"
            updatePulse()
        }
        binding.btnPredSteps.setOnClickListener {
            binding.tvPredType.text = "Шаги"
            updateSteps()
        }
        binding.btnPredTemp.setOnClickListener {
            binding.tvPredType.text = "Температура"
            updateTemp()
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


    private fun updatePulse() {
        val entries1: ArrayList<Entry> = ArrayList()

        val entries2: ArrayList<Entry> = ArrayList()

        entries1.add(Entry(1f, 88f))
        entries1.add(Entry(2f, 92f))
        entries1.add(Entry(3f, 90f))
        entries1.add(Entry(4f, 94f))
        entries1.add(Entry(5f, 96f))
        entries1.add(Entry(6f, 90f))
        entries1.add(Entry(7f, 93f))
        entries1.add(Entry(8f, 98f))

        entries2.add(Entry(1f, 89f))
        entries2.add(Entry(2f, 90f))
        entries2.add(Entry(3f, 91f))
        entries2.add(Entry(4f, 92f))
        entries2.add(Entry(5f, 93f))
        entries2.add(Entry(6f, 94f))
        entries2.add(Entry(7f, 95f))
        entries2.add(Entry(8f, 96f))
        entries2.add(Entry(9f, 97f))
        entries2.add(Entry(10f, 98f))
        entries2.add(Entry(11f, 99f))
        entries2.add(Entry(12f, 100f))
        entries2.add(Entry(13f, 101f))
        entries2.add(Entry(14f, 102f))
        entries2.add(Entry(15f, 103f))
        entries2.add(Entry(16f, 104f))


        val lineDataSet1 = LineDataSet(entries1, "статистика")
        val lineDataSet2 = LineDataSet(entries2, "прогноз")

        lineDataSet2.color = Color.RED

        val dataList: ArrayList<LineDataSet> = ArrayList()
        dataList.add(lineDataSet1)
        dataList.add(lineDataSet2)

        val data = LineData(dataList as List<ILineDataSet>?)
        lineChart.data = data

        lineChart.invalidate()
    }



    private fun updateTemp() {
        val entries1: ArrayList<Entry> = ArrayList()

        val entries2: ArrayList<Entry> = ArrayList()

        entries1.add(Entry(1f, 36.4f))
        entries1.add(Entry(2f, 36.9f))
        entries1.add(Entry(3f, 35.8f))
        entries1.add(Entry(4f, 36.3f))
        entries1.add(Entry(5f, 36.8f))
        entries1.add(Entry(6f, 36.9f))
        entries1.add(Entry(7f, 36.8f))
        entries1.add(Entry(8f, 36.9f))

        entries2.add(Entry(1f, 36.71f))
        entries2.add(Entry(2f, 36.72f))
        entries2.add(Entry(3f, 36.73f))
        entries2.add(Entry(4f, 36.74f))
        entries2.add(Entry(5f, 36.75f))
        entries2.add(Entry(6f, 36.76f))
        entries2.add(Entry(7f, 36.77f))
        entries2.add(Entry(8f, 36.78f))
        entries2.add(Entry(9f, 36.79f))
        entries2.add(Entry(10f, 36.80f))
        entries2.add(Entry(11f, 36.81f))
        entries2.add(Entry(12f, 36.82f))
        entries2.add(Entry(13f, 36.83f))
        entries2.add(Entry(14f, 36.84f))
        entries2.add(Entry(15f, 36.85f))
        entries2.add(Entry(16f, 36.86f))



        val lineDataSet1 = LineDataSet(entries1, "статистика")
        val lineDataSet2 = LineDataSet(entries2, "прогноз")

        lineDataSet2.color = Color.RED

        val dataList: ArrayList<LineDataSet> = ArrayList()
        dataList.add(lineDataSet1)
        dataList.add(lineDataSet2)

        val data = LineData(dataList as List<ILineDataSet>?)
        lineChart.data = data

        lineChart.invalidate()
    }

    private fun updateSteps() {
        val entries1: ArrayList<Entry> = ArrayList()

        val entries2: ArrayList<Entry> = ArrayList()

        entries1.add(Entry(1f, 689f))
        entries1.add(Entry(2f, 938f))
        entries1.add(Entry(3f, 1097f))
        entries1.add(Entry(4f, 542f))
        entries1.add(Entry(5f, 487f))
        entries1.add(Entry(6f, 695f))
        entries1.add(Entry(7f, 878f))
        entries1.add(Entry(8f, 410f))
        val temp = 800f

        entries2.add(Entry(1f, temp))
        entries2.add(Entry(2f, temp-10))
        entries2.add(Entry(3f, temp-20))
        entries2.add(Entry(4f, temp-30))
        entries2.add(Entry(5f, temp-30))
        entries2.add(Entry(6f, temp-40))
        entries2.add(Entry(7f, temp-50))
        entries2.add(Entry(8f, temp-60))
        entries2.add(Entry(9f, temp-70))
        entries2.add(Entry(10f, temp-80))
        entries2.add(Entry(11f, temp-90))
        entries2.add(Entry(12f, temp-100))
        entries2.add(Entry(13f, temp-110))
        entries2.add(Entry(14f, temp-120))
        entries2.add(Entry(15f, temp-130))
        entries2.add(Entry(16f, temp-140))


        val lineDataSet1 = LineDataSet(entries1, "статистика")
        val lineDataSet2 = LineDataSet(entries2, "прогноз")

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