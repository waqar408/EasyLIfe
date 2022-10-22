package com.easylife.easylifes.userside.activities.Support

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.easylife.easylifes.databinding.ActivitySupport2Binding
import com.easylife.easylifes.utils.Utilities

class Support2Activity : AppCompatActivity() {
    private lateinit var binding: ActivitySupport2Binding
    private lateinit var utilities: Utilities
    var question = ""
    var answer = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySupport2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        initViews()


    }

    private fun initViews() {
        utilities = Utilities(this@Support2Activity)
        utilities.setGrayBar(this@Support2Activity)

        binding.layoutBackArrow.setOnClickListener {
            finish()
        }
        val intent = intent
        question = intent.getStringExtra("question").toString()
        answer = intent.getStringExtra("answer").toString()
        binding.tvName.text = question
        binding.idAnswer.text = answer
    }
}