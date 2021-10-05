package com.capgemini.TestAppSangamithra

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

class Adapter(context: Context,studentData:MutableList<EmployeeDB>):
    ArrayAdapter<EmployeeDB>(context,R.layout.display_data,studentData) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        //layout rendering only if it is not done
        var view: View
        if (convertView == null) {
            view = LayoutInflater.from(context).inflate(R.layout.display_data, null)
        } else
            view = convertView
        //data binding
        val nameTextView = view.findViewById<TextView>(R.id.nameeE)
        val idTextView = view.findViewById<TextView>(R.id.idE)
        val quaTextView = view.findViewById<TextView>(R.id.qualificationE)
        val depTextView = view.findViewById<TextView>(R.id.departmentE)
        val std = getItem(position)
        nameTextView.text = "${std?.name}"
        idTextView.text = "${std?.id}"
        quaTextView.text = "${std?.qual}"
        depTextView.text = "${std?.dep}"


        return view
    }
}