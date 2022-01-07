package com.example.sportshub.login
import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.example.sportshub.R
import com.example.sportshub.SingletonRequestQueueProvider
import com.example.sportshub.login.model.LoginResponseModel
import org.json.JSONException
import org.json.JSONObject

class LoginViewModel: ViewModel(){

    private val QUERY_FOR_LOGIN = "${R.string.localBackend}/api/auth/login/"

    fun tryLogin(context: Context, username: String, password: String, loginListener: LoginListener) {
        //TODO if valid user then save credential for subsequent logins
        val url = QUERY_FOR_LOGIN
        val loginResponseModel = LoginResponseModel()
        val loginData = JSONObject()
        try {
            loginData.put("username", username)
            loginData.put("password", password)
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        val request = JsonObjectRequest(Request.Method.POST, url, loginData,
            {
                try {
                    loginResponseModel.refresh = it.getString("refresh")
                    loginResponseModel.access = it.getString("access")
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
                loginListener.onResponse(loginResponseModel)
            },
            {
                val statusCode: Int? = it.networkResponse?.statusCode

                if(statusCode == 401){
                    Toast.makeText(context, "Invalid username or password. Try again!", Toast.LENGTH_SHORT).show()
                    if(it.networkResponse?.data != null){
                        loginResponseModel.detail = JSONObject(String(it.networkResponse.data)).getString("detail")
                    }
                } else{
                    Toast.makeText(context,"Error occurred: "+ statusCode.toString(), Toast.LENGTH_SHORT).show()
                }
                loginListener.onError(loginResponseModel)
            }
        )
        SingletonRequestQueueProvider.getQueue().add(request)
    }
}