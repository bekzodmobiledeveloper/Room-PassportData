package com.example.passportdatageneration.fragment

import android.content.Context
import android.os.Bundle
import android.os.CountDownTimer
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.passportdatageneration.R
import com.example.passportdatageneration.databinding.FragmentSplashBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class MenuFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    lateinit var binding: FragmentSplashBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSplashBinding.inflate(inflater, container, false)
        startTimeCounter(binding.root.context)
        findNavController().popBackStack()
        return binding.root
    }

    override fun onPause() {
        super.onPause()
        findNavController().navigate(R.id.menuuFragment)

    }

    private fun startTimeCounter(view: Context) {
        object : CountDownTimer(2000, 1000) {
            override fun onTick(p0: Long) {

            }

            override fun onFinish() {
                findNavController().navigate(R.id.menuuFragment)
            }
        }.start()
    }


}