package com.easylife.easylifes.userside.activities.choosepackage

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.easylife.easylifes.databinding.ActivityAddNewCardBinding
import com.easylife.easylifes.model.signup.SignUpDataModel
import com.easylife.easylifes.model.signup.SignupResponseModel
import com.easylife.easylifes.utils.Utilities
import com.google.gson.Gson
import com.tabadol.tabadol.data.network.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class AddNewCardActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddNewCardBinding
    private lateinit var utilities: Utilities
    var cardName = ""
    var number = ""
    var cvv = ""
    var expiry = ""
    var userId = ""
    private var cardExpiry = ""
    private var cardHolderName = ""
    private var cardNumber = ""

    val apiClient = ApiClient()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddNewCardBinding.inflate(layoutInflater)
        setContentView(binding.root)


        initViews()
        onClicks()


    }

    private fun initViews() {
        utilities = Utilities(this@AddNewCardActivity)
        utilities.setGrayBar(this@AddNewCardActivity)
        val gsonn = Gson()
        val jsonn: String = utilities.getString(this@AddNewCardActivity, "loginResponse")
        if (jsonn.isNotEmpty()) {
            val obj: SignUpDataModel = gsonn.fromJson(jsonn, SignUpDataModel::class.java)
            userId = obj.id.toString()
            number = obj.card_number
            cardName = obj.name
            expiry = obj.expiry_month + obj.expiry_year
            cvv = obj.cvv

            cardNumber = obj.card_number
            cardHolderName = obj.name
            cardExpiry = obj.expiry_month+obj.expiry_year
            cvv  = obj.cvv
            userId = obj.id.toString()

            if (cardNumber.isEmpty())
            {
                cardNumber = "**** **** **** ****"
            }
            if (cardHolderName.isEmpty())
            {
                cardHolderName = "------"
            }
            if (cardExpiry.isEmpty())
            {
                cardExpiry = "--/--"
            }
            if (cvv.isEmpty())
            {
                cvv = "---"
            }

            binding.crediCardView.setCardData(cardHolderName,cardNumber,cvv,cardExpiry)
            binding.edCardNumber.text = Editable.Factory.getInstance().newEditable(cardNumber)
            binding.edCardName.text = Editable.Factory.getInstance().newEditable(cardName)
            binding.etExpiryDate.text = Editable.Factory.getInstance().newEditable(expiry)
            binding.etCvv.text = Editable.Factory.getInstance().newEditable(cvv)
        }
        binding.edCardName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                cardName = s.toString()
                binding.crediCardView.setCardData(cardName, number, cvv, expiry)
            }

            override fun afterTextChanged(s: Editable) {}
        })

        binding.edCardNumber.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (s.isNotEmpty()) {
                    binding.edCardNumber.hint = ""
                } else {
                    binding.edCardNumber.hint = "Card Number"
                }
                number = binding.edCardNumber.unMaskedText.toString()
                binding.crediCardView.setCardData(cardName, number, cvv, expiry)
            }

            override fun afterTextChanged(s: Editable) {}
        })

        binding.etExpiryDate.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (s.isNotEmpty()) {
                    binding.etExpiryDate.hint = ""
                } else {
                    binding.etExpiryDate.hint = "Expiry Date"
                }
                expiry = binding.etExpiryDate.text.toString()
                binding.crediCardView.setCardData(
                    cardName,
                    number,
                    cvv,
                    binding.etExpiryDate.unMaskedText.toString()
                )
            }

            override fun afterTextChanged(s: Editable) {}
        })

        binding.etCvv.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                cvv = binding.etCvv.unMaskedText.toString()
                binding.crediCardView.setCardData(cardName, number, cvv, expiry)
            }

            override fun afterTextChanged(s: Editable) {}
        })

    }

    private fun onClicks() {
        binding.layoutSend.setOnClickListener {
            val cardHolderName = binding.edCardName.text.toString()
            val cardHolderNumber = binding.edCardNumber.text.toString()
            val cardExpiry = binding.etExpiryDate.text.toString()
            val cardCvv = binding.etCvv.text.toString()
            if (cardHolderName == "") {
                utilities.showFailureToast(this@AddNewCardActivity, "Please Enter Card Name")
            } else if (cardHolderNumber == "") {
                utilities.showFailureToast(this@AddNewCardActivity, "Please Enter Card Number")
            } else if (cardExpiry == "") {
                utilities.showFailureToast(this@AddNewCardActivity, "Please Enter Expiry Date")
            } else if (cardCvv == "") {
                utilities.showFailureToast(this@AddNewCardActivity, "Please Enter Card CVV")
            } else {
                val separated = cardExpiry.split("/".toRegex()).toTypedArray()
                val month = separated[0]
                val year = separated[1]
                updateWithoutImageApi(cardHolderName,cardHolderNumber,month,year,cvv)

            }
        }
    }

    private fun updateWithoutImageApi(
        cardHolderName: String,
        cardNumber: String,
        expirymonth: String,
        expiryYear: String,
        cvv: String
    ) {
        if (utilities.isConnectingToInternet(this@AddNewCardActivity)) {
            binding.dotloader.visibility = View.VISIBLE
            apiClient.getApiService().updateProfile(
                userId, "", "",
                "",
                "", "", "", "",
                "", "", "",
                "", "", "",
                "", "", "",
                "", "", "", "", "", "",
                cardNumber, expirymonth, expiryYear, cvv, cardHolderName, ""
            )
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
                                    this@AddNewCardActivity,
                                    signupResponse.message
                                )
                                Handler(Looper.myLooper()!!).postDelayed({
                                    val gson = Gson()
                                    val json = gson.toJson(signupResponse.data)
                                    utilities.saveString(this@AddNewCardActivity, "loginResponse", json)
                                    finish()
                                },1000)

                            } else {
                                utilities.showFailureToast(
                                    this@AddNewCardActivity,
                                    signupResponse.message
                                )
                            }
                        } else {
                            utilities.showFailureToast(
                                this@AddNewCardActivity,
                                signupResponse!!.message
                            )
                        }

                    }

                    override fun onFailure(call: Call<SignupResponseModel>, t: Throwable) {
                        // Error logging in
                        binding.dotloader.visibility = View.GONE
                        utilities.showFailureToast(this@AddNewCardActivity, t.message)

                    }

                })
        } else {
            utilities.showNoInternetToast(this@AddNewCardActivity)
        }
    }
}