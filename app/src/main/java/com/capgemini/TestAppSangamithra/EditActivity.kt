package com.capgemini.TestAppSangamithra

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast

class EditActivity : AppCompatActivity() {
    lateinit var qualEditText: EditText
    lateinit var depEditText: EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.edit_activity)
        qualEditText=findViewById(R.id.qualE)
        depEditText=findViewById(R.id.depE)

    }
    fun addClick(view: View){
        when(view.id){
            R.id.button->{
                val qual = qualEditText.text.toString()
                val dep = depEditText.text.toString()
                    Toast.makeText(this,"Changes are saved",Toast.LENGTH_LONG).show()

            }
            R.id.button2->{
                qualEditText.setText("")
                depEditText.setText("")
            }
        }
    }
}