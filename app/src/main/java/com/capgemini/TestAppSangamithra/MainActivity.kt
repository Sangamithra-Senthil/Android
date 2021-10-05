package com.capgemini.TestAppSangamithra

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*


data class EmployeeDB(val id:Int, var name:String, var qual :String, var dep:String )

class MainActivity : AppCompatActivity() {
    lateinit var idEditText:  EditText
    lateinit var nameEditText: EditText
    lateinit var qualEditText: EditText
    lateinit var depEditText: EditText

    val studentList= mutableListOf<EmployeeDB>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        idEditText=findViewById(R.id.empid)
        nameEditText=findViewById(R.id.nameeE)
        qualEditText=findViewById(R.id.qualE)
        depEditText=findViewById(R.id.depE)

    }




    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menu?.add("ADD")
        menu?.add("EDIT")
        menu?.add("VIEW ALL")
        menu?.add("QUIT")
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.title) {
                     "ADD" -> { val i = Intent(this,MainActivity::class.java)
                         startActivity(i)
            }
            "EDIT"->{
                val i = Intent(this, EditActivity::class.java)
                startActivity(i)

            }
            "VIEW ALL"->{
                val i = Intent(this, DisplayDetailsActivity::class.java)
                startActivity(i)
            }
            "QUIT"->{
                finish()
            }
        }

        return super.onOptionsItemSelected(item)
    }

    fun submitClick(view: View) {
        when (view.id) {
            R.id.addB -> {
                val id = idEditText.text.toString()
                val name = nameEditText.text.toString()
                val qual = qualEditText.text.toString()
                val dep = depEditText.text.toString()

                val wrapper = DatabseWrapper(this)

                if (id.isNotEmpty() && name.isNotEmpty() && qual.isNotEmpty() && dep.isNotEmpty()) {
                    val std = EmployeeDB(id.toInt(), name, qual, dep)
                    //add to database
                    val rowid = wrapper.addStudent(std)
                    if (rowid.toInt() != -1) {
                        Toast.makeText(this, "Details saved: $rowid", Toast.LENGTH_LONG).show()
                        displayNotification(this, "Reminder", "Record added", DisplayDetailsActivity::class.java)

                    } else
                        Toast.makeText(this, "Error saving details...", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(this, "please provide all info", Toast.LENGTH_LONG).show()
                }
            }
            R.id.cancelB->{
                idEditText.setText("")
                nameEditText.setText("")
                qualEditText.setText("")
                depEditText.setText("")
            }

        }

    }

        fun displayNotification(ctx: Context, title:String, descr:String, cls:Class<*>) {

            //1.get NotificationManager reference
            val nManager = ctx.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            //2. create notification object
            val builder: Notification.Builder

            //Above Oreo devices-Notification Channel
            if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.O) {
                val channel = NotificationChannel("Reminder", "Reminder", NotificationManager.IMPORTANCE_DEFAULT)
                channel.setSound(Settings.System.DEFAULT_RINGTONE_URI,null)
                nManager.createNotificationChannel(channel)
                builder = Notification.Builder(ctx,"Reminder")
            }
            else {
                builder = Notification.Builder(ctx)
            }

            builder.setSmallIcon(android.R.drawable.btn_star_big_on)
            builder.setContentTitle("NOTIFICATIONS")
            builder.setContentText("Record added")

            /*
            Action-launch activity(general)
                -launch service
                -broadcast sent
             */

            val i = Intent(ctx,cls)
            val pi = PendingIntent.getActivity(ctx, 0,i,0)
            builder.setContentIntent(pi)
            val myNotification = builder.build()
            myNotification.flags= Notification.FLAG_AUTO_CANCEL or Notification.FLAG_NO_CLEAR

            //3.display
            nManager.notify(1,myNotification)

        }
}




