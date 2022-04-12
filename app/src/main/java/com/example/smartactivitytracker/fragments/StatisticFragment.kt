package com.example.smartactivitytracker.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.smartactivitytracker.activities.MainApp
import com.example.smartactivitytracker.databinding.FragmentStatisticBinding
import com.example.smartactivitytracker.db.MainViewModel
import com.example.smartactivitytracker.entities.Datas
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter


class StatisticFragment : Fragment() {
    private lateinit var binding: FragmentStatisticBinding

    private lateinit var lineChart: LineChart
    private var scoreList = ArrayList<Datas>()


    private val mainViewModel: MainViewModel by activityViewModels {
        MainViewModel.MainViewModelFactory((context?.applicationContext as MainApp).database)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentStatisticBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lineChart = binding.lineChart
        initAll()

    }

    private fun initAll() {
        initLineChart()
        setDataToLineChart()

        binding.btnUpdateTemper.setOnClickListener {
            setTemperatureToLineChart()
        }

        binding.btnUpdateHeart.setOnClickListener {
            setHeartToLineChart()
        }
    }




    companion object {

        @JvmStatic
        fun newInstance() = StatisticFragment()

    }




    // функции для графика

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
            return if (index < scoreList.size) {
                scoreList[index].id.toString()
            } else {
                ""
            }
        }
    }

    private fun setTemperatureToLineChart() {
        //now draw bar chart with dynamic data
        val entries: ArrayList<Entry> = ArrayList()

        mainViewModel.dayData.observe(viewLifecycleOwner) { // достать список из liveData
            scoreList = ArrayList(it)
        }

        //you can replace this data object with  your custom object
        for (i in scoreList.indices) {
            val score = scoreList[i]
            if (score.dataType == "t") // TODO проверку корректности данных
                entries.add(Entry(i.toFloat(), score.value.toFloat()))
        }

        val lineDataSet = LineDataSet(entries, "")

        val data = LineData(lineDataSet)
        lineChart.data = data

        lineChart.invalidate()
    }

    private fun setHeartToLineChart() {
        //now draw bar chart with dynamic data
        val entries: ArrayList<Entry> = ArrayList()

        mainViewModel.dayData.observe(viewLifecycleOwner) { // достать список из liveData
            scoreList = ArrayList(it)
        }

        //you can replace this data object with  your custom object
        for (i in scoreList.indices) {
            val score = scoreList[i]
            if (score.dataType == "h") // TODO проверку корректности данных
                entries.add(Entry(i.toFloat(), score.value.toFloat()))
        }

        val lineDataSet = LineDataSet(entries, "")

        val data = LineData(lineDataSet)
        lineChart.data = data

        lineChart.invalidate()
    }

    private fun setDataToLineChart() {
        //now draw bar chart with dynamic data
        val entries: ArrayList<Entry> = ArrayList()

        mainViewModel.dayData.observe(viewLifecycleOwner) { // достать список из liveData
            scoreList = ArrayList(it)
        }

        //you can replace this data object with  your custom object
        for (i in scoreList.indices) {
            val score = scoreList[i]
            entries.add(Entry(i.toFloat(), score.value.toFloat()))
        }

        val lineDataSet = LineDataSet(entries, "")

        val data = LineData(lineDataSet)
        lineChart.data = data

        lineChart.invalidate()
    }

    // simulate api call
    // we are initialising it directly

}