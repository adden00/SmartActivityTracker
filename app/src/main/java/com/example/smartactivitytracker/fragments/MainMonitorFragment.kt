package com.example.smartactivitytracker.fragments

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.smartactivitytracker.activities.MainActivity
import com.example.smartactivitytracker.activities.MainApp
import com.example.smartactivitytracker.databinding.FragmentMainMonitorBinding
import com.example.smartactivitytracker.db.MainViewModel
import com.example.smartactivitytracker.entities.Datas


class MainMonitorFragment : Fragment() {
    lateinit var binding: FragmentMainMonitorBinding
    var measuring = false



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentMainMonitorBinding.inflate(inflater)
        setButtons()

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



    }


    private fun setButtons() {
        binding.btnStartMeasure.setOnClickListener {

            if (!measuring) {
                if ((activity as MainActivity).startMeasure()) {

                binding.btnStartMeasure.text = "стоп"
                binding.btnStartMeasure.setBackgroundColor(Color.parseColor("#FF0000"))
                measuring = true}
            }
            else {
                (activity as MainActivity).stopMeasure()
                binding.btnStartMeasure.text = "старт"
                binding.btnStartMeasure.setBackgroundColor(Color.parseColor("#3498DB"))
                measuring = false
                binding.tvHeartRate.text = "-"
                binding.tvSteps.text = "-"
                binding.tvTemperature.text = "-"
            }


        }
    }

    fun unlockStartBtn() {
        binding.btnStartMeasure.isEnabled = true
    }

    fun newDataItem(newData: Datas) { // при получении нового значения

        when (newData.dataType) { // TODO проверку корректности данных
            "t" -> {
                binding.tvTemperature.text = newData.value
            }

            "h" -> {
                binding.tvHeartRate.text = newData.value
            }

            "s" -> {
                binding.tvSteps.text = newData.value
            }
        }


//        binding.tvTemperature.text = newVal
    }

    companion object {

        @JvmStatic
        fun newInstance() =  MainMonitorFragment()


    }



}