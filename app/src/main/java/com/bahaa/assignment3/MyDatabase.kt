package com.bahaa.assignment3

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class MyDatabase(val context: Context): SQLiteOpenHelper(context, "UserDatabase", null, 2) {
    private val db: SQLiteDatabase = this.writableDatabase

    override fun onCreate(db: SQLiteDatabase?) {
        db!!.execSQL(User.CREATE_TABLE)
        // hello word
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS " + User.TABLE_NAME)
        onCreate(db)
    }

    fun insertUser(user: User): Boolean {
        val cv = ContentValues()
        cv.put(User.NAME, user.name)
        cv.put(User.PHONE, user.phone)
        cv.put(User.EMAIL, user.email)
        cv.put(User.PASSWORD, user.password)
        val insert = db.insert(User.TABLE_NAME, null, cv)
        return insert > 0
    }

    @SuppressLint("Range")
    fun getAllEmails(): ArrayList<String> {
        val emails = ArrayList<String>()
        val data = db.rawQuery("select ${User.EMAIL} from ${User.TABLE_NAME}", null)
        data.moveToFirst()

        while (!data.isAfterLast) {
            emails.add(data.getString(data.getColumnIndex(User.EMAIL)))
            data.moveToNext()
        }

        data.close()
        return emails
    }

    fun signIn(email: String, password: String): Boolean {
        val data = db.rawQuery("SELECT ${User.EMAIL},${User.PASSWORD} FROM ${User.TABLE_NAME} " +
                "WHERE ${User.EMAIL} = ? AND ${User.PASSWORD} = ?", arrayOf(email, password))
        val dataFound = data.count > 0 && data != null
        data.close()
        return dataFound
    }
}