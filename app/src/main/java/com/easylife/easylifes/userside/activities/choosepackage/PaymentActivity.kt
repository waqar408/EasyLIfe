package com.easylife.easylifes.userside.activities.choosepackage

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.easylife.easylifes.databinding.ActivityPaymentBinding
import com.easylife.easylifes.model.signup.SignUpDataModel
import com.easylife.easylifes.utils.Utilities
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_settings.view.*

class PaymentActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPaymentBinding
    private lateinit var utilities: Utilities
    var cvv = ""
    var cardExpiry = ""
    var cardHolderName = ""
    var cardNumber = ""
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
        val gsonn = Gson()
        val jsonn: String = utilities.getString(this@PaymentActivity, "loginResponse")
        if (!jsonn.isEmpty()) {
            val obj: SignUpDataModel = gsonn.fromJson(jsonn, SignUpDataModel::class.java)
            var cardNumber = obj.card_number
            var cardName = obj.name
            var cardExpiry = obj.expiry_month+obj.expiry_year
            var cvv  = obj.cvv

            if (cardNumber.isEmpty())
            {
                cardNumber = "**** **** **** ****"
            }
            if (cardName.isEmpty())
            {
                cardName = "------"
            }
            if (cardExpiry.isEmpty())
            {
                cardExpiry = "--/--"
            }
            if (cvv.isEmpty())
            {
                cvv = "---"
            }

            binding.crediCardView.setCardData(cardName,cardNumber,cvv,cardExpiry)
        }
    }


    private fun onClicks() {
        binding.tvUsePaymentMethod.setOnClickListener {
            startActivity(Intent(this@PaymentActivity, AddNewCardActivity::class.java))
        }
    }
}