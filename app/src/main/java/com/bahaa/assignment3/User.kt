package com.bahaa.assignment3

class User(val name: String, val email: String, val password: String, val phone: String ) {
    companion object{
        const val TABLE_NAME = "UserDatabase"
        const val NAME = "Name"
        const val EMAIL = "Email"
        const val PASSWORD = "Password"
        const val PHONE = "Phone"
        const val CREATE_TABLE = "CREATE TABLE $TABLE_NAME(" +
                "$NAME TEXT," +
                "$PHONE TEXT," +
                "$EMAIL TEXT," +
                "$PASSWORD TEXT)"
    }
}