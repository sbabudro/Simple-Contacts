package com.donsdirectory.donsdirlib.activities

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.donsdirectory.donsdirlib.R
//import androidx.lifecycle.MutableLiveData
import kotlinx.android.synthetic.main.activity_login.*
import kotlin.properties.Delegates

const val SUCCESS = true
const val FAILURE = false

class LoginActivity : AppCompatActivity() {

//    private val debugModeCheck = MutableLiveData<Boolean>()
//    private val imageModeGroup = MutableLiveData<Int>()
//    private val selectionTickViewGroup = MutableLiveData<Int>()
//    private val selectionModeGroup = MutableLiveData<Int>()
//    private var colorDefault: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        Log.d("ShowMeOnly", "Login page started")

        LoginButton.setOnClickListener {
            Log.d("ShowMeOnly", "clicked")
            val loginResult = authenticateLogin(username.text, password.text)
            if(loginResult == 1) {
                makeToast("Log-in Successful!")
                finish()
                //TODO: change main activity using logged-in state (maybe pass a success/fail variable?)
            } else if(loginResult == 0) {
                makeToast("Invalid username or password")
            } else if(loginResult == -1) {
                makeToast("Connection failed!")
            }
        }
    }

    //Toasts are little cheery text boxes or speech bubbles that appear on the screen for a few
    //seconds and then disappear.
    private fun makeToast(toastText: String) {
        val toastDuration = Toast.LENGTH_SHORT
        val toast = Toast.makeText(applicationContext, toastText, toastDuration)
        toast.show()
    }

    //This function receives the username and password entered and performs a server call to check
    //if the login was successful before bringing the user to the main screen
    private fun authenticateLogin(username: Editable, password: Editable): Int {

        if(username.isBlank()) {
            makeToast("Username cannot be blank")
            return 0
        }
        if(password.isBlank()) {
            makeToast("Password cannot be blank")
            return 0
        }

        Log.d("ShowMeOnly","Authentication Started")
        makeToast("Authenticating...")
        Log.d("ShowMeOnly","Toast shown")
        var token: String = ""
        var loginResult: Int = 999
        var errorStr: String = ""

        val queue = Volley.newRequestQueue(this)
        var request = "http://dict.org/"
        var stringRequest = StringRequest(Request.Method.GET, request,
                { response -> token = response },
                { error ->
                    errorStr = error.toString()
                    Log.d("ShowMeOnly", "Error is $errorStr")
                })
        queue.add(stringRequest)
        Log.d("ShowMeOnly", "Token is $token")
        request = "https://dev.donsdirectory.com/api_auth.php?t=$token&p=$password"
        stringRequest = StringRequest(Request.Method.GET, request,
                { response -> loginResult = response.toInt() },
                {  })
        queue.add(stringRequest)
        Log.d("ShowMeOnly","$loginResult")
        return loginResult ?: -1
    }
}
