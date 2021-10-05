package com.capgemini.TestAppSangamithra

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase

class DatabseWrapper (context: Context) {
    val helper: Database = Database(context)
    val db: SQLiteDatabase = helper.writableDatabase

    fun addStudent(std: EmployeeDB): Long {
        //insert
        val rowData = ContentValues()
        rowData.put(Database.CLM_STD_ID, std.id)
        rowData.put(Database.CLM_STD_NAME, std.name)
        rowData.put(Database.CLM_STD_QUAL, std.qual)
        rowData.put(Database.CLM_STD_DEP, std.dep)

        return db.insert(Database.TABLE_NAME, null, rowData)
    }

    fun getStudents(): Cursor {
        //select query
        val clms = arrayOf(
            Database.CLM_STD_ID,
            Database.CLM_STD_NAME,
            Database.CLM_STD_QUAL,
            Database.CLM_STD_DEP
        )
        return db.query(
            Database.TABLE_NAME,
            clms,
            null,
            null,
            null,
            null,
            " ${Database.CLM_STD_ID} asc"
        )
    }

    fun editStudent(std: EmployeeDB): Int {
        //update
        val rowData = ContentValues()
        rowData.put(Database.CLM_STD_ID, std.id)
        rowData.put(Database.CLM_STD_NAME, std.name)
        rowData.put(Database.CLM_STD_QUAL, std.qual)
        rowData.put(Database.CLM_STD_DEP, std.dep)

        val args = arrayOf("${std.id}")
        return db.update(Database.TABLE_NAME, rowData, "${Database.CLM_STD_ID}=?", args)
    }
    fun deleteEmployee(emp: EmployeeDB):Int {
        //delete
        val args= arrayOf("${emp.name}")
        return db.delete(Database.TABLE_NAME,"${Database.CLM_STD_NAME}=?",args)
    }
}
