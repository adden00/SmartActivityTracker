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


        binding.btnUpdateTemper.setOnClickListener {
            binding.tvStatType.text = "Температура"
            setTemperatureToLineChart()
        }

        binding.btnUpdateHeart.setOnClickListener {
            binding.tvStatType.text = "Пульс"
            setHeartToLineChart()
        }

        binding.btnUpdateStreps.setOnClickListener{
            binding.tvStatType.text = "Шаги"
            setStepsToLineChart()
        }
    }




    companion object {

        @JvmStatic
        fun newInstance() = StatisticFragment()

    }




    // функции для графика

    private fun initLineChart() {
        // скрыть сетку
        lineChart.axisLeft.setDrawGridLines(false)
        val xAxis: XAxis = lineChart.xAxis
        xAxis.setDrawGridLines(false)
        xAxis.setDrawAxisLine(false)

        // убрать правую ось
        lineChart.axisRight.isEnabled = false

        // убрать легенду
        lineChart.legend.isEnabled = false

        // убрать описание графика
        lineChart.description.isEnabled = false

        //анимация при показе
        lineChart.animateX(1000, Easing.EaseInSine)

        // подписи осей
        xAxis.position = XAxis.XAxisPosition.BOTTOM_INSIDE
        xAxis.valueFormatter = MyAxisFormatter()
        xAxis.setDrawLabels(true)
        xAxis.granularity = 1f

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

    private fun setTemperatureToLineChart() {               // функция вывода на график данных о температуре
        val entries: ArrayList<Entry> = ArrayList()

        mainViewModel.dayData.observe(viewLifecycleOwner) {  // получения списка из liveData базы данных
            scoreList = ArrayList(it)
        }

        for (i in scoreList.indices) {                       // создание списка с координатами точек
            val score = scoreList[i]
            if (score.dataType == "t")
                entries.add(Entry(i.toFloat(), score.value.toFloat()))
        }
        val data = LineData(LineDataSet(entries, ""))  // преобразование в тип LineData для отображение на графике
        lineChart.data = data
        lineChart.invalidate()
    }



    private fun setHeartToLineChart() {
        val entries: ArrayList<Entry> = ArrayList()

        mainViewModel.dayData.observe(viewLifecycleOwner) { // достать список из liveData
            scoreList = ArrayList(it)
        }

        //you can replace this data object with  your custom object
        for (i in scoreList.indices) {
            val score = scoreList[i]
            if (score.dataType == "h") // TODO можно добавить проверку корректности данных
                entries.add(Entry(i.toFloat(), score.value.toFloat()))
        }

        val lineDataSet = LineDataSet(entries, "")

        val data = LineData(lineDataSet)
        lineChart.data = data

        lineChart.invalidate()
    }

    private fun setStepsToLineChart() {
        val entries: ArrayList<Entry> = ArrayList()

        mainViewModel.dayData.observe(viewLifecycleOwner) { // достать список из liveData
            scoreList = ArrayList(it)
        }

        //you can replace this data object with  your custom object
        for (i in scoreList.indices) {
            val score = scoreList[i]
            if (score.dataType == "s")
                entries.add(Entry(i.toFloat(), score.value.toFloat()))
        }

        val lineDataSet = LineDataSet(entries, "")

        val data = LineData(lineDataSet)
        lineChart.data = data

        lineChart.invalidate()
    }

//    private fun setDataToLineChart() {
//        //now draw bar chart with dynamic data
//        val entries: ArrayList<Entry> = ArrayList()
//
//        mainViewModel.dayData.observe(viewLifecycleOwner) { // достать список из liveData
//            scoreList = ArrayList(it)
//        }
//
//        //you can replace this data object with  your custom object
//        for (i in scoreList.indices) {
//            val score = scoreList[i]
//            entries.add(Entry(i.toFloat(), score.value.toFloat()))
//        }
//
//        val lineDataSet = LineDataSet(entries, "")
//
//        val data = LineData(lineDataSet)
//        lineChart.data = data
//
//        lineChart.invalidate()
//    } // вывод всез данных без категорий


}