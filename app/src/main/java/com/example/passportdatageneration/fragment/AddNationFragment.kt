package com.example.passportdatageneration.fragment

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.FileProvider
import androidx.navigation.fragment.findNavController
import com.example.passportdatageneration.BuildConfig
import com.example.passportdatageneration.R
import com.example.passportdatageneration.adapter.PassportAdapter
import com.example.passportdatageneration.adapter.SpinnerAdapter
import com.example.passportdatageneration.databinding.AddDialogBinding
import com.example.passportdatageneration.databinding.FragmentAddNationBinding
import com.example.passportdatageneration.room.AppDatabase
import com.example.passportdatageneration.room.entity.Nations
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class AddNationFragment : Fragment() {
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

    lateinit var passportAdapter: PassportAdapter
    lateinit var appDatabase: AppDatabase
    lateinit var spinnerAdapter: SpinnerAdapter
    lateinit var binding: FragmentAddNationBinding
    lateinit var list: ArrayList<Nations>
    var REQUEST_CODE = 1
    var filepath: String? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddNationBinding.inflate(inflater, container, false)
        binding.backMv.setOnClickListener {
            findNavController().popBackStack()
        }
        val regionList = arrayListOf(
            "Tashkent",
            "Samarqand",
            "Qashqadaryo",
            "Farg'ona",
            "Andijon",
            "Namangan",
            "Andijon",
            "Sirdaryo",
            "Jizzax",
            "Surxandaryo",
            "Navoiy",
            "Buxoro",
            "Xorazm",
            "Qoraqalpog'iston"
        )
        binding.spinnerRegion.adapter = SpinnerAdapter(requireContext(), regionList, "Viloyati")

        val genderList = arrayListOf("Erkak", "Ayol")
        binding.spinnerGender.adapter = SpinnerAdapter(requireContext(), genderList, "Jinsi")

        appDatabase = AppDatabase.getInstance(requireContext())


        binding.saveNation.setOnClickListener {

            val alertDialog = AlertDialog.Builder(binding.root.context)
            alertDialog.setCancelable(false)
            alertDialog.setTitle("Ma'lumotlariningiz to'g'ri ekanligiga ishonchingiz komilmi?")
            alertDialog.setPositiveButton("Ha", object : DialogInterface.OnClickListener {
                override fun onClick(p0: DialogInterface?, p1: Int) {

                    val name = binding.nationName.text.toString()
                    val lastName = binding.nationLastname.text.toString()
                    val fatherName = binding.fatherName.text.toString()
                    val region1 = binding.spinnerRegion.selectedItemPosition
                    val country = binding.country.text.toString()
                    val houseAddress = binding.houseAddress.text.toString()
                    val passportGetTime = binding.getTimePass.text.toString()
                    val passTime = binding.passportTime.text.toString()
                    val gender1 = binding.spinnerGender.selectedItemPosition





                    if (name.isEmpty() || lastName.isEmpty() || fatherName.isEmpty() || country
                            .isEmpty() || houseAddress.isEmpty() || passportGetTime.isEmpty() || passTime
                            .isEmpty() || filepath == null || region1 == 0 || gender1 == 0
                    ) {
                        Toast.makeText(requireContext(), "Ma'lumotlar yetarli emas!!", Toast.LENGTH_SHORT).show()
                    } else {
                        val region = regionList[binding.spinnerRegion.selectedItemPosition - 1]
                        val gender = genderList[binding.spinnerGender.selectedItemPosition - 1]
                        val nations = Nations()
                        nations.name = name
                        nations.lastName = lastName
                        nations.fatherName = fatherName
                        nations.region = region
                        nations.country = country
                        nations.addressHouse = houseAddress
                        nations.getTimePassport = passportGetTime
                        nations.passportTime = passTime
                        nations.gender = gender
                        nations.image = filepath

                        appDatabase.nationsDao().addNations(nations)
                        findNavController().popBackStack()
                        Toast.makeText(requireContext(), "Ma'lumotlar jo'natildi", Toast.LENGTH_SHORT).show()

                    }


                }
            })
            alertDialog.setNegativeButton("Yo'q", object : DialogInterface.OnClickListener {
                override fun onClick(p0: DialogInterface?, p1: Int) {

                }

            })
            alertDialog.show()

        }

        Dexter.withContext(requireContext())
            .withPermissions(
                Manifest.permission.CAMERA,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ).withListener(object : MultiplePermissionsListener {
                override fun onPermissionsChecked(p0: MultiplePermissionsReport?) {
                }

                override fun onPermissionRationaleShouldBeShown(p0: MutableList<PermissionRequest>?, p1: PermissionToken?) {
                }
            }).check()

        binding.imageView.setOnClickListener {

            pickImageOldFromGallery()

        }






        return binding.root
    }

    private fun createImageFile(): File {
        val format = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val externalFilesDir = requireContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile("JPEG_$format", ".jpg", externalFilesDir).apply {
            filepath = absolutePath
        }

    }

    private fun pickImageOldFromGallery() {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
        intent.addCategory(Intent.CATEGORY_OPENABLE)
        intent.type = "image/*"
        startActivityForResult(intent, REQUEST_CODE)
    }

    private fun pickImageOldFromCamera() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takeIntentFoto ->
            takeIntentFoto.resolveActivity(requireContext().packageManager)?.also {
                val photoFile: File? = try {
                    createImageFile()
                } catch (ex: IOException) {
                    null
                }
                photoFile?.also {
                    val photoUri: Uri = FileProvider.getUriForFile(requireContext(), BuildConfig.APPLICATION_ID, it)
                    takeIntentFoto.putExtra(MediaStore.EXTRA_OUTPUT, photoUri)
                    startActivityForResult(takeIntentFoto, REQUEST_CODE)
                }
            }
        }
    }


    @RequiresApi(Build.VERSION_CODES.R)
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val fragment = fragmentManager?.findFragmentByTag(AddNationFragment.toString())
        fragment?.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            val uri: Uri = data?.data ?: return
            binding.imageView.setImageURI(uri)
            val openInputStream = context?.contentResolver?.openInputStream(uri)
            val file = File(requireContext().filesDir, "image.jpg")
            val fileOutPutStream = FileOutputStream(file)
            openInputStream?.copyTo(fileOutPutStream)
            openInputStream?.close()
            fileOutPutStream.close()
            filepath = file.absolutePath
        }
    }


    companion object {

        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AddNationFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }


}