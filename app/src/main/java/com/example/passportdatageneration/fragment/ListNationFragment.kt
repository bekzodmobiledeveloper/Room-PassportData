package com.example.passportdatageneration.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.example.passportdatageneration.R
import com.example.passportdatageneration.adapter.PassportAdapter
import com.example.passportdatageneration.databinding.FragmentListNationBinding
import com.example.passportdatageneration.room.AppDatabase
import com.example.passportdatageneration.room.entity.Nations

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class ListNationFragment : Fragment(), PassportAdapter.OnMyClickListener {
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

    lateinit var list: ArrayList<Nations>
    lateinit var passportAdapter: PassportAdapter
    lateinit var appDatabase: AppDatabase
    lateinit var binding: FragmentListNationBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentListNationBinding.inflate(inflater, container, false)
        binding.backMv.setOnClickListener {
            findNavController().popBackStack()
        }

        appDatabase = AppDatabase.getInstance(requireContext())


        val allList = appDatabase.nationsDao().getAllNations() as ArrayList<Nations>
        allList[0].name
        passportAdapter = PassportAdapter(allList as ArrayList<Nations>, this)
        binding.rvTv.adapter = passportAdapter


        return binding.root
    }

    companion object {

        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ListNationFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onMyClick(position: Int, nation: Nations) {

        val bundle = bundleOf("boot" to nation)
        findNavController().navigate(R.id.nationAllFragment, bundle)
    }


}