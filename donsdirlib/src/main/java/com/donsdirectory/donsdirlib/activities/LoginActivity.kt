package com.donsdirectory.donsdirlib.activities

import android.content.Intent
import android.os.Bundle
import android.os.SystemClock
import android.text.Editable
import android.util.Base64.DEFAULT
import android.util.Base64.encode
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.donsdirectory.donsdirlib.R
//import androidx.lifecycle.MutableLiveData
import kotlinx.android.synthetic.main.activity_login.*
import org.json.JSONException
import java.util.*
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
            Log.d("ShowMeOnly",loginResult.toString())

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

    private fun checkPassword(token: String, password: String, requestQueue: RequestQueue): Int? {
        val url = "https://dev.donsdirectory.com/api_auth.php?t=$token&p=$password"
        var authenticated: Int? = null
        Log.d("URL", url)



        val stringRequest = StringRequest(Request.Method.GET, url,
                { response ->
                    if (response == null) {
//                    textView.text = "Failed"
                    } else{
                        authenticated = response.toInt()
                        Log.d("Authentication","$authenticated")
                        if (authenticated == 1) {
                            makeToast("Log-in Successful!")
                            finish()
                            //TODO: change main activity using logged-in state (maybe pass a success/fail variable?)
                        } else if (authenticated == 0) {
                            makeToast("Invalid username or password")
                        } else if (authenticated == -1) {
                            makeToast("Connection failed!")
                        }
                    }
                },
                { error -> Log.d("error: ", error.toString()) })

        requestQueue.add(stringRequest)





        return authenticated

    }

    private fun checkUsername(username: String, requestQueue: RequestQueue): Int? {
        var finish: Int? = null

        val pwd = password.text.toString()
        Log.d("ShowMeOnly","Password: $pwd")
        val encodedPassword: String = Base64.getEncoder().encodeToString(password.text.toString().toByteArray())

        val url = "https://dev.donsdirectory.com/api_auth.php?u=$username"
        val stringRequest = StringRequest(Request.Method.GET, url,
                { response ->
                    if (response == null) {
                        Log.d("Houston","We have a problem")
                    } else {
                        Log.d("LoginActivity", "Token: $response")
                        finish = checkPassword(response, encodedPassword, requestQueue)

                    }
                },
                { error -> Log.d("error: ", error.toString()) }
        )
        requestQueue.add(stringRequest)

        return finish
    }


    private fun authenticateLogin(username: Editable, password: Editable): Int {

        if (username.isBlank()) {
            makeToast("Username cannot be blank")
            return 0
        }
        if (password.isBlank()) {
            makeToast("Password cannot be blank")
            return 0
        }
        makeToast("Authenticating...")
        Log.d("button: ", "clicked")
        val requestQueue = Volley.newRequestQueue(this)

        return checkUsername(username.toString(), requestQueue) ?: 0
    }
}

//        timeout = calendar.get(Calendar.SECOND)
//
//        while(token == null){
//            if (calendar.get(Calendar.SECOND) > timeout + 1){
//                break
//            }
//        }


//    }
//}
//        makeToast("Authenticating...")
//        var token: String = ""
//        var loginResult: Int? = null
//        var errorStr: String = ""
//
//        val queue = Volley.newRequestQueue(this)
//        var request = "https://dev.donsdirectory.com/api_auth.php?u=$username"
//        var stringRequest = StringRequest(Request.Method.GET, request,
//                { response -> while(response == null){}
//                token = response
//                },
//                { error ->
//                    errorStr = error.toString()
//                    Log.d("ShowMeOnly", "Error is $errorStr")
//                })
//
//        queue.add(stringRequest)
//        Log.d("ShowMeOnly","Token: $token")
//
//        request = "https://dev.donsdirectory.com/api_auth.php?t=$token&p=$password"
//        stringRequest = StringRequest(Request.Method.GET, request,
//                { response2 -> while(response2 == null){}
//                    loginResult = response2.toInt() },
//                {  })
//        queue.add(stringRequest)
//        Log.d("ShowMeOnly","Login Result: $loginResult")
//        return loginResult ?: -1
//    }
//}


