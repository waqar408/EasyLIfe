package com.easylife.easylifes.userside.activities.choosepackage

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.easylife.easylifes.databinding.ActivityPaymentBinding
import com.easylife.easylifes.model.BaseResponse
import com.easylife.easylifes.model.signup.SignUpDataModel
import com.easylife.easylifes.utils.Utilities
import com.google.gson.Gson
import com.tabadol.tabadol.data.network.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PaymentActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPaymentBinding
    private lateinit var utilities: Utilities
    private var cvv = ""
    private var cardExpiry = ""
    private var cardHolderName = ""
    private var cardNumber = ""
    private var packageId : String? = null
    var userId = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPaymentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViews()
        onClicks()


    }

    private fun initViews() {
        utilities = Utilities(this@PaymentActivity)
        utilities.setGrayBar(this@PaymentActivity)

        val intent = intent
        packageId = intent.getStringExtra("id").toString()
        if (!packageId.equals("null"))
        {
            binding.tvUsePaymentMethod.visibility = View.VISIBLE
        }

    }

    override fun onResume() {
        super.onResume()
        val gsonn = Gson()
        val jsonn: String = utilities.getString(this@PaymentActivity, "loginResponse")
        if (jsonn.isNotEmpty()) {
            val obj: SignUpDataModel = gsonn.fromJson(jsonn, SignUpDataModel::class.java)
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
        }
    }

    private fun onClicks() {
        binding.tvUsePaymentMethod.setOnClickListener {
            val cardName = binding.crediCardView.cardData.holder
            val cardNumber = binding.crediCardView.cardData.number
            val cardExpiry = binding.crediCardView.expiry
            val cardCvv = binding.crediCardView.cardData.cvv
            if(cardName == "")
            {
                utilities.showFailureToast(this@PaymentActivity,"Please Enter CardHolder Name")
            }else if (cardNumber== "")
            {
                utilities.showFailureToast(this@PaymentActivity,"Please Enter Card Number")
            }else if (cardExpiry== "")
            {
                utilities.showFailureToast(this@PaymentActivity,"Please Enter Card Expiry Date")
            }else if(cardCvv == "")
            {
                utilities.showFailureToast(this@PaymentActivity,"Please Enter CVV")
            }else{

                val month = cardExpiry.dropLast(2)
                val year = cardExpiry.drop(2)
                subcribeTrainer(cardNumber, month, year, cardCvv)
            }
        }
        binding.lnUpdateCard.setOnClickListener{
            startActivity(Intent(this@PaymentActivity,AddNewCardActivity::class.java))
        }
    }

    private fun subcribeTrainer(
        cardNumber: String,
        month: String,
        year: String,
        cardCvv: String
    ) {
        val apiClient = ApiClient()
        if (utilities.isConnectingToInternet(this@PaymentActivity)) {
            binding.dotloader.visibility = View.VISIBLE
            apiClient.getApiService().subscribeTrainer(
                userId, packageId!!,
                cardNumber,month,year,cardCvv
            )
                .enqueue(object : Callback<BaseResponse> {

                    override fun onResponse(
                        call: Call<BaseResponse>,
                        response: Response<BaseResponse>
                    ) {
                        binding.dotloader.visibility = View.GONE
                        val signupResponse = response.body()
                        if (response.isSuccessful) {
                            if (signupResponse?.status!!) {
                                startActivity(Intent(this@PaymentActivity,PaymentSuccessActivity::class.java))
                                finish()
                            } else {
                                utilities.showFailureToast(
                                    this@PaymentActivity,
                                    signupResponse.message
                                )
                            }
                        } else {
                            utilities.showFailureToast(
                                this@PaymentActivity,
                                signupResponse!!.message
                            )
                        }

                    }

                    override fun onFailure(call: Call<BaseResponse>, t: Throwable) {
                        // Error logging in
                        binding.dotloader.visibility = View.GONE
                        utilities.showFailureToast(this@PaymentActivity, t.message)
                    }
                })
        } else {
            utilities.showNoInternetToast(this@PaymentActivity)
        }
    }

}