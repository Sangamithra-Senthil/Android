package com.capgemini.TestAppSangamithra
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog


class DisplayDetailsActivity : AppCompatActivity() {

    var studentList = mutableListOf<EmployeeDB>()
    lateinit var studentListView : ListView
    lateinit var stdAdapter:Adapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_display_details)

        studentListView=findViewById(R.id.rv)
        registerForContextMenu(studentListView)

    }

    override fun onResume() {
        super.onResume()
        populateList()
        stdAdapter = Adapter(this,studentList)
        studentListView.adapter=stdAdapter
    }
    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        val info = menuInfo as AdapterView.AdapterContextMenuInfo
        val Name = studentList[info.position].name
        menu?.setHeaderTitle(Name)
        menu?.add("Edit")
        menu?.add("Delete")
        menu?.add("View")

    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val info = item.menuInfo as AdapterView.AdapterContextMenuInfo
        val rem = studentList[info.position]
        val wrapper=DatabseWrapper(this)
        when (item.title) {
            "Edit" -> {
                val i = Intent(this,EditActivity::class.java)
                startActivity(i)
            }
            "Delete" -> {
                //delete city
                wrapper.deleteEmployee(rem)
                studentList.remove(rem)
                stdAdapter.notifyDataSetChanged()
            }

            "View"->{
                val i = Intent(this, MainActivity::class.java)
                startActivity(i)
            }
        }

        return super.onContextItemSelected(item)
    }
    private fun populateList() {
        val wrapper=DatabseWrapper(this)
        val resultC=wrapper.getStudents()

        studentList.clear()
        if (resultC.count>0){
            resultC.moveToFirst()

            val idx_id=resultC.getColumnIndex(Database.CLM_STD_ID)
            val idx_name=resultC.getColumnIndex(Database.CLM_STD_NAME)
            val idx_qual=resultC.getColumnIndex(Database.CLM_STD_QUAL)
            val idx_dep=resultC.getColumnIndex(Database.CLM_STD_DEP)

            do {
                val id=resultC.getInt(idx_id)
                val name=resultC.getString(idx_name)
                val qual= resultC.getString(idx_qual)
                val dep=resultC.getString(idx_dep)
                val std=EmployeeDB(id,name,qual,dep)

                studentList.add(std)

            }while (resultC.moveToNext())
            Log.d("MainActivity","Employee list: $studentList")
            Toast.makeText(this,"No of Employee: ${studentList.size}", Toast.LENGTH_LONG).show()
        }
        else
            Toast.makeText(this,"NO Employees added yet", Toast.LENGTH_LONG).show()

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menu?.add("Add")
        menu?.add("Quit")
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.title){
            "Add"->{
                val i = Intent(this,MainActivity::class.java)
                startActivity(i)
            }
            "Quit"->{
                alertDialog()
            }
        }

        return super.onOptionsItemSelected(item)
    }

    private fun alertDialog() {
        val dialogBuilder = AlertDialog.Builder(this)
        dialogBuilder.setMessage("Do you want to quit?")
            .setNegativeButton("NO", DialogInterface.OnClickListener{
                    dialog,id->dialog.cancel() })
            .setPositiveButton("YES", DialogInterface.OnClickListener {
                    dialog, id -> finish()})

    }
}
