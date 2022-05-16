package com.bahaa.assignment3

import android.app.ProgressDialog
import android.content.Intent
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_sign_in.*

class SignIn : AppCompatActivity() {
    lateinit var pd: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        tv_sign_up.setOnClickListener {
            val intent = Intent(this, SignUp::class.java)
            startActivity(intent)
        }

        btn_sign_in.setOnClickListener {
            MyAsyncTask().execute(
                et_sign_in_email.text.toString(),
                et_sign_in_password.text.toString()
            )
        }

    }

    fun checkValue(): Boolean {
        var canSignIn = true

        val array = arrayOf(et_sign_in_email, et_sign_in_password)
        for (i in array) {
            if (i.text.isEmpty()) {
                Toast.makeText(this, "${i.hint} most not be Empty", Toast.LENGTH_SHORT).show()
                canSignIn = false
                break
            }
        }
        return canSignIn
    }

    fun signInProcess(email: String, password: String) {
        val db = MyDatabase(this)
        if (db.signIn(email, password)) {
            MyNotification(this).showNotification(
                1,
                "SignIn successfully",
                "SignIn successfully SignIn successfully",
                Intent(this, SignIn::class.java)
            )
        } else {
            MyNotification(this).showNotification(
                1,
                "SignIn failed",
                "SignIn failed SignIn failed",
                Intent(this, SignIn::class.java)
            )
        }
    }


    inner class MyAsyncTask : AsyncTask<String?, String?, Void>() {
        override fun onPreExecute() {
            super.onPreExecute()
            pd = ProgressDialog(this@SignIn)
            pd.setMessage("Processing...")
            pd.setCancelable(false)
            pd.show()
        }

        override fun doInBackground(vararg params: String?): Void? {
            for (i in 0..15) {
                Thread.sleep(1000)
            }
            if (checkValue()) {
                signInProcess(params[0]!!, params[1]!!)
            }
            return null
        }

        override fun onPostExecute(result: Void?) {
            super.onPostExecute(result)
            pd.dismiss()
        }
    }
}