package com.easylife.easylifes.userside.activities.auth

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.view.View
import android.widget.Toast
import com.easylife.easylifes.R
import com.easylife.easylifes.userside.activities.MainActivity
import com.easylife.easylifes.databinding.ActivityLoginBinding
import com.easylife.easylifes.model.signup.SignupResponseModel
import com.easylife.easylifes.trainerside.activities.TrainerMainActivity
import com.easylife.easylifes.utils.Utilities
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.gson.Gson
import com.tabadol.tabadol.data.network.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var utilities: Utilities
    private var passwordVisibile = false
    var email = ""
    var password = ""
    var apiClient = ApiClient()
    private var userType = ""
    var firebaseToken = ""
    private lateinit var mGoogleSignInClient: GoogleSignInClient
    private var RC_SIGN_IN: Int = 100

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)


        initViews()
        onClicks()


    }

    private fun initViews() {
        utilities = Utilities(this@LoginActivity)
        utilities.setWhiteBars(this@LoginActivity)
        userType = utilities.getString(this@LoginActivity,"usertype")
        //firebase token to send and recieve notification
        val sharedPref = getSharedPreferences("TOKEN", Context.MODE_PRIVATE)
        firebaseToken = sharedPref?.getString("FCM_TOKEN", "").toString()
        Log.d("token", firebaseToken)
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()
        mGoogleSignInClient = GoogleSignIn.getClient(this@LoginActivity, gso)

    }

    private fun onClicks() {
        binding.rlGoogle.setOnClickListener {
            signIn()
        }
        binding.layoutBackArrow.setOnClickListener {
            finish()
        }
        binding.layoutSignIn.setOnClickListener {
            email = binding.editEmail.text.toString()
            password = binding.editPassword.text.toString()
            val isValidEmail: Boolean = utilities.isValidEmail(email)

            if (email == "") {
                utilities.showFailureToast(this@LoginActivity, "Required!", "Please Enter Email")
            } else if (!isValidEmail) {
                utilities.showFailureToast(
                    this@LoginActivity,
                    "Required!",
                    "Please Enter Valid Email"
                )
            } else if (password == "") {
                utilities.showFailureToast(this@LoginActivity, "Required!", "Please Enter Password")
            } else if (password.length < 6) {
                utilities.showFailureToast(
                    this@LoginActivity,
                    "Required!",
                    "Please 6 Digit Passowrd"
                )
            } else {
                loginApi(email, password)
            }
        }
        binding.layoutShowHide.setOnClickListener {
            passwordVisibile = !passwordVisibile
            showPassword(passwordVisibile)
        }
        binding.layoutAlreadyAccount.setOnClickListener {
            startActivity(Intent(this@LoginActivity, SignUpActivity::class.java))
        }
        binding.layoutForgotPassword.setOnClickListener {
            startActivity(Intent(this@LoginActivity, ForgotPassword::class.java))
        }
    }

    private fun signIn() {
        val signInIntent = mGoogleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(ApiException::class.java)

            // Signed in successfully, show authenticated UI.
            //  startActivity(Intent(this@LoginActivity, SubscriptionActivity::class.java))
            val id: String? = account.id
            val name: String? = account.displayName
            val email: String? = account.email
            // Toast.makeText(this@LoginActivity, id + name + email, Toast.LENGTH_SHORT).show()
            loginWithGoogle(id.toString(), name.toString(), email.toString(), "firebaseToken")
            Log.w("mio", "signInResult:failed code=")
            Toast.makeText(this@LoginActivity, name, Toast.LENGTH_LONG).show()
            Log.d("checkj", id.toString() + "name" + name.toString() + "email" + email.toString())

        } catch (e: ApiException) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w("moto", "signInResult:failed code=" + e.statusCode)
            //  updateUI(null)
        }
    }

    private fun loginWithGoogle(googleId: String, name: String, email: String, token: String) {

        binding.dotloader.visibility = View.VISIBLE
        apiClient.getApiService().loginWithGoogle(googleId, name, email, "android", token,userType)
            .enqueue(object : Callback<SignupResponseModel> {

                override fun onFailure(call: Call<SignupResponseModel>, t: Throwable) {

                    binding.dotloader.visibility = View.GONE
                    utilities.showFailureToast(this@LoginActivity, t.message.toString())

                }

                override fun onResponse(
                    call: Call<SignupResponseModel>,
                    response: Response<SignupResponseModel>
                ) {
                    val signupResponse = response.body()
                    binding.dotloader.visibility = View.GONE
                    if (signupResponse!!.status) {
                        if (signupResponse.message == "You'll be Login after Admin Approval") {
                            utilities.showSuccessToast(
                                this@LoginActivity,
                                signupResponse.message
                            )
                        } else {

                            if (signupResponse.data.type == "1") {
                                if (signupResponse.data.is_profile_complete) {

                                    val gson = Gson()
                                    val json = gson.toJson(signupResponse.data)
                                    utilities.saveString(this@LoginActivity, "loginResponse", json)
                                    utilities.saveString(this@LoginActivity, "loggedin", "loggedin")
                                    utilities.saveString(this@LoginActivity, "loginAs", "google")
                                    val intent = Intent(this@LoginActivity, MainActivity::class.java)
                                    startActivity(intent)
                                    finishAffinity()
                                } else {
                                    val gson = Gson()
                                    val json = gson.toJson(signupResponse.data)
                                    utilities.saveString(this@LoginActivity, "loginResponse", json)
                                    utilities.saveString(this@LoginActivity, "loginAs", "google")
                                    val intent = Intent(this@LoginActivity, GenderSelectionActivity::class.java)
                                    startActivity(intent)
                                }
                            } else {
                                val gson = Gson()
                                val json = gson.toJson(signupResponse.data)
                                utilities.saveString(this@LoginActivity, "loginResponse", json)
                                utilities.saveString(
                                    this@LoginActivity,
                                    "loggedin",
                                    "loggedin"
                                )
                                val intent = Intent(
                                    this@LoginActivity,
                                    TrainerMainActivity::class.java
                                )
                                startActivity(intent)
                                finishAffinity()
                            }


                        }
                    } else {
                        utilities.showFailureToast(this@LoginActivity, signupResponse.message)
                    }
                }
            })

    }

    private fun loginApi(email: String, password: String) {
        if (utilities.isConnectingToInternet(this@LoginActivity)) {
            binding.dotloader.visibility = View.VISIBLE
            apiClient.getApiService().login(email, password)
                .enqueue(object : Callback<SignupResponseModel> {

                    override fun onResponse(
                        call: Call<SignupResponseModel>,
                        response: Response<SignupResponseModel>
                    ) {
                        val signupResponse = response.body()
                        binding.dotloader.visibility = View.GONE
                        if (response.isSuccessful) {
                            if (signupResponse?.status!!) {
                                utilities.showSuccessToast(
                                    this@LoginActivity,
                                    signupResponse.message
                                )
                                if (signupResponse.message == "You'll be Login after Admin Approval") {
                                    utilities.showSuccessToast(
                                        this@LoginActivity,
                                        signupResponse.message
                                    )
                                } else {

                                    if (signupResponse.data.type == "1") {
                                        if (signupResponse.data.is_profile_complete) {

                                            val gson = Gson()
                                            val json = gson.toJson(signupResponse.data)
                                            utilities.saveString(this@LoginActivity, "loginResponse", json)
                                            utilities.saveString(this@LoginActivity, "loggedin", "loggedin")
                                            utilities.saveString(this@LoginActivity, "loginAs", "apilogin")
                                            val intent = Intent(this@LoginActivity, MainActivity::class.java)
                                            startActivity(intent)
                                            finishAffinity()
                                        } else {
                                            val gson = Gson()
                                            val json = gson.toJson(signupResponse.data)
                                            utilities.saveString(this@LoginActivity, "loginResponse", json)
                                            utilities.saveString(this@LoginActivity, "loggedin", "loggedin")
                                            utilities.saveString(this@LoginActivity, "loginAs", "apilogin")
                                            val intent = Intent(this@LoginActivity, GenderSelectionActivity::class.java)
                                            startActivity(intent)
                                        }
                                    } else {
                                        val gson = Gson()
                                        val json = gson.toJson(signupResponse.data)
                                        utilities.saveString(this@LoginActivity, "loginResponse", json)
                                        utilities.saveString(
                                            this@LoginActivity,
                                            "loggedin",
                                            "loggedin"
                                        )
                                        val intent = Intent(
                                            this@LoginActivity,
                                            TrainerMainActivity::class.java
                                        )
                                        startActivity(intent)
                                        finishAffinity()
                                    }


                                }

                            } else {
                                utilities.showFailureToast(
                                    this@LoginActivity,
                                    signupResponse.message
                                )
                            }
                        } else {
                            utilities.showFailureToast(this@LoginActivity, signupResponse!!.message)
                        }

                    }

                    override fun onFailure(call: Call<SignupResponseModel>, t: Throwable) {
                        // Error logging in
                        binding.dotloader.visibility = View.GONE
                        utilities.showFailureToast(this@LoginActivity, t.message)

                    }

                })
        } else {
            utilities.showNoInternetToast(this@LoginActivity)
        }
    }

    private fun showPassword(isShow: Boolean) {
        if (isShow) {
            binding.editPassword.transformationMethod =
                HideReturnsTransformationMethod.getInstance()
            binding.eyesHidePassword.setImageResource(R.drawable.visible_icon)
        } else {
            binding.editPassword.transformationMethod = PasswordTransformationMethod.getInstance()
            binding.eyesHidePassword.setImageResource(R.drawable.ic_eye_icn)
        }
        binding.editPassword.setSelection(binding.editPassword.text.toString().length)
    }
}