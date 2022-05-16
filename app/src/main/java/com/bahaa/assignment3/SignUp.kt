package com.bahaa.assignment3

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignUp : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        tv_sign_in.setOnClickListener {
            val intent = Intent(this, SignIn::class.java)
            startActivity(intent)
            finish()
        }

        btn_sign_up.setOnClickListener {
            if (checkValue()) {
                signUpProcess()
            }
        }

    }

    fun checkValue(): Boolean {
        var canSignIn = true

        // check if any input is Empty or not
        val array =
            arrayOf(et_sign_up_name, et_sign_up_phone, et_sign_up_email, et_sign_up_password)
        for (i in array) {
            if (i.text.isEmpty()) {
                Toast.makeText(this, "${i.hint} must not be Empty", Toast.LENGTH_SHORT).show()
                canSignIn = false
                break
            }
        }

        if (!canSignIn) return canSignIn

        // check if Email is use before or not
        val db = MyDatabase(this)
        for (i in db.getAllEmails()) {
            if (et_sign_up_email.text.toString() == i) {
                Toast.makeText(this, "Email is used before", Toast.LENGTH_SHORT).show()
                canSignIn = false
            }
        }

        if (!canSignIn) return canSignIn

        // check if Password is more than 8 character or not
        if (et_sign_up_password.length() < 8) {
            Toast.makeText(this, "Password must be more than 8 character", Toast.LENGTH_SHORT)
                .show()
            canSignIn = false
        }

        return canSignIn
    }

    fun signUpProcess() {
        val name = et_sign_up_name.text.toString()
        val phone = et_sign_up_phone.text.toString()
        val email = et_sign_up_email.text.toString()
        val password = et_sign_up_password.text.toString()

        val accountData = User(name, email, password, phone)

        val db = MyDatabase(this)
        if (db.insertUser(accountData)) {
            val intent = Intent(this, SignIn::class.java)
            startActivity(intent)
            finish()
        } else {
            Toast.makeText(this, "Some thing was wrong", Toast.LENGTH_SHORT).show()
        }
    }

}