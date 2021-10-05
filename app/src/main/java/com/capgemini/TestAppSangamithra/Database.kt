package com.capgemini.TestAppSangamithra

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class Database(context: Context):SQLiteOpenHelper(context,"employee.db",null,1) {
    companion object{
        val TABLE_NAME="emp_table"
        val CLM_STD_ID="emp_id"
        val CLM_STD_NAME="emp_name"
        val CLM_STD_QUAL="emp_qual"
        val CLM_STD_DEP="emp_dep"
    }
    val QUERY_CREATE_TABLE="create table $TABLE_NAME ($CLM_STD_ID number, "+
            "$CLM_STD_NAME text,$CLM_STD_QUAL text,$CLM_STD_DEP text)"
    override fun onCreate(db: SQLiteDatabase?) {
        //executed first time when db is created
        db?.execSQL(QUERY_CREATE_TABLE)
    }

    override fun onUpgrade(p0: SQLiteDatabase?, oldV: Int, newV: Int) {
        //upgrade db

    }
}