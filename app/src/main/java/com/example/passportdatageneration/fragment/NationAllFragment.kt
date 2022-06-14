package com.example.passportdatageneration.fragment

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.passportdatageneration.R
import com.example.passportdatageneration.databinding.FragmentNationAllBinding
import com.example.passportdatageneration.room.entity.Nations
import java.io.File

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class NationAllFragment : Fragment() {
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

    lateinit var binding: FragmentNationAllBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNationAllBinding.inflate(inflater, container, false)
        binding.backMv.setOnClickListener {
            findNavController().popBackStack()
        }

        val bundle = this.arguments
        val nations: Nations = bundle?.getSerializable("boot") as Nations

        binding.toolbar.title = "${nations.lastName} ${nations.name}"

        //  bind.circleImage.setImageURI(Uri.fromFile(File(user.imagePath)))
        binding.imageView.setImageURI(Uri.fromFile(File(nations.image)))
        binding.fatherName.text = nations.fatherName
        binding.region.text = nations.region
        binding.country.text = nations.country
        binding.address.text = nations.addressHouse
        binding.gettimepass.text = nations.getTimePassport
        binding.passtime.text = nations.passportTime
        binding.gender.text = nations.gender



        return binding.root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment NationAllFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            NationAllFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}