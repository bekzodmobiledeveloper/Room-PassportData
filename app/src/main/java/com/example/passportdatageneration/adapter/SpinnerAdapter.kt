package com.example.passportdatageneration.adapter

import android.content.Context
import android.graphics.Color

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.example.passportdatageneration.R
import com.example.passportdatageneration.databinding.SpinnerItemBinding


class SpinnerAdapter(var mContext: Context, var list: List<String>, var strHint: String) :
    BaseAdapter() {

    override fun getCount(): Int {
        return list.size + 1
    }

    override fun getItem(position: Int): Any {
        return list[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {


        val viewItem: View = convertView ?: LayoutInflater.from(parent!!.context)
            .inflate(R.layout.spinner_item, parent, false)
        val bind = SpinnerItemBinding.bind(viewItem)


        if (position == 0) {
            bind.tvSpiner.text = strHint

            bind.tvSpiner.setTextColor(Color.GRAY)
            bind.tvSpiner.isEnabled = false
        } else {
            bind.tvSpiner.text = list[position-1]
            bind.tvSpiner.setTextColor(Color.BLACK)
            bind.tvSpiner.isEnabled = true

        }

        return viewItem
    }
}