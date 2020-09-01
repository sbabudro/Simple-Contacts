package com.simplemobiletools.contacts.pro.databases

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DonsDatabaseHandler(context: Context): SQLiteOpenHelper(context, "donsDirectory.db", null, 1) {
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(    "create table if not exists favourites(" +
                            "id integer primary key autoincrement, personcode integer," +
                            "firstname text, middlename text, lastname text, title text," +
                            "companyname text, officephone integer, ext integer," +
                            "homephone integer, cellphone integer, fax integer," +
                            "email text, city text, address text, state text, zipcode integer," +
                            "createddate integer, updated integer, callhistory text, comment text," +
                            "groups text)")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }

}
