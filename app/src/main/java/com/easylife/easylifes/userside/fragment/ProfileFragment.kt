package com.easylife.easylifes.userside.fragment

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.view.WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
import android.widget.ImageView
import android.widget.RelativeLayout
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.easylife.easylifes.BuildConfig
import com.easylife.easylifes.R
import com.easylife.easylifes.userside.activities.setting.SettingsActivity
import com.easylife.easylifes.databinding.FragmentProfileBinding
import com.easylife.easylifes.model.signup.SignUpDataModel
import com.easylife.easylifes.userside.activities.Support.SupportActivity
import com.easylife.easylifes.userside.activities.address.MyAddressActivity
import com.easylife.easylifes.userside.activities.auth.LoginActivity
import com.easylife.easylifes.userside.activities.bodymeasurement.BodyMeasurementsActivity
import com.easylife.easylifes.userside.activities.choosepackage.MyPayments2Activity
import com.easylife.easylifes.userside.activities.choosepackage.MyPaymentsActivity
import com.easylife.easylifes.utils.Utilities
import com.google.gson.Gson


class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding
    private lateinit var utilities: Utilities
    var profileImage = ""
    var userName = ""
    var location = ""
    var type = ""
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentProfileBinding.inflate(layoutInflater, container, false)

        initViews()
        statusbarColor()
        onClicks()

        return binding.root
    }

    private fun initViews() {
        utilities = Utilities(requireContext())
        val gsonn = Gson()
        val jsonn: String = utilities.getString(requireContext(), "loginResponse")
        if (jsonn.isNotEmpty()) {
            val obj: SignUpDataModel = gsonn.fromJson(jsonn, SignUpDataModel::class.java)
            profileImage = obj.profile_image
            userName = obj.username
            location = obj.location
            Glide.with(this@ProfileFragment).load(profileImage).into(binding.profileImage)
            binding.userName.text = userName
            binding.tvLocation.text = location
            type = obj.type
        }
    }

    private fun statusbarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requireActivity().window.statusBarColor = requireActivity().getColor(R.color.haiti)
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            requireActivity().window.decorView.windowInsetsController!!
                .setSystemBarsAppearance(0, APPEARANCE_LIGHT_STATUS_BARS)
        }
    }

    private fun onClicks() {
        binding.cardGoals.setOnClickListener {
            if (type == "3") {
                guestDialog()
            } else {
                binding.rlMyGoals.setBackgroundResource(R.color.appColor)
                binding.rlMyGoals2.setBackgroundResource(R.drawable.round_blue)
                binding.imgGoalsIcon.setColorFilter(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.white
                    )
                )
                binding.tvGoals.setTextColor(resources.getColor(R.color.white))
                binding.rlBodyMeasurement.setBackgroundResource(R.color.white)
                binding.rlBodyMeasurement2.setBackgroundResource(R.drawable.round_white)
                binding.imgBodyMeasurements.setColorFilter(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.statusbarcolor
                    )
                )
                binding.tvBodyMeasurements.setTextColor(resources.getColor(R.color.haiti))
                binding.rlMyPayments.setBackgroundResource(R.color.white)
                binding.rlMyPayments2.setBackgroundResource(R.drawable.round_white)
                binding.imgMyPayments.setColorFilter(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.statusbarcolor
                    )
                )
                binding.tvMyPayments.setTextColor(resources.getColor(R.color.haiti))
                binding.rlInviteFriend.setBackgroundResource(R.color.white)
                binding.rlInviteFriend2.setBackgroundResource(R.drawable.round_white)
                binding.imgInviteFriend.setColorFilter(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.statusbarcolor
                    )
                )
                binding.tvInviteFriend.setTextColor(resources.getColor(R.color.haiti))
                binding.rlEditAccount.setBackgroundResource(R.color.white)
                binding.rlEditAccount2.setBackgroundResource(R.drawable.round_white)
                binding.imgEditAccount.setColorFilter(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.statusbarcolor
                    )
                )
                binding.tvEditAccount.setTextColor(resources.getColor(R.color.haiti))
                binding.rlMyAddress.setBackgroundResource(R.color.white)
                binding.rlMyAddress2.setBackgroundResource(R.drawable.round_white)
                binding.imgMyAddress.setColorFilter(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.statusbarcolor
                    )
                )
                binding.tvMyAddress.setTextColor(resources.getColor(R.color.haiti))
                binding.rlSettings.setBackgroundResource(R.color.white)
                binding.rlSettings2.setBackgroundResource(R.drawable.round_white)
                binding.imgSettings.setColorFilter(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.statusbarcolor
                    )
                )
                binding.tvSettings.setTextColor(resources.getColor(R.color.haiti))

            }
        }

        binding.cardBodyMeasurements.setOnClickListener {
            if (type == "3") {
                guestDialog()
            } else {
                binding.rlMyGoals.setBackgroundResource(R.color.white)
                binding.rlMyGoals2.setBackgroundResource(R.drawable.round_white)
                binding.imgGoalsIcon.setColorFilter(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.statusbarcolor
                    )
                )
                binding.tvGoals.setTextColor(resources.getColor(R.color.haiti))
                binding.rlBodyMeasurement.setBackgroundResource(R.color.appColor)
                binding.rlBodyMeasurement2.setBackgroundResource(R.drawable.round_blue)
                binding.imgBodyMeasurements.setColorFilter(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.white
                    )
                )
                binding.tvBodyMeasurements.setTextColor(resources.getColor(R.color.white))
                binding.rlMyPayments.setBackgroundResource(R.color.white)
                binding.rlMyPayments2.setBackgroundResource(R.drawable.round_white)
                binding.imgMyPayments.setColorFilter(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.statusbarcolor
                    )
                )
                binding.tvMyPayments.setTextColor(resources.getColor(R.color.haiti))
                binding.rlInviteFriend.setBackgroundResource(R.color.white)
                binding.rlInviteFriend2.setBackgroundResource(R.drawable.round_white)
                binding.imgInviteFriend.setColorFilter(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.statusbarcolor
                    )
                )
                binding.tvInviteFriend.setTextColor(resources.getColor(R.color.haiti))
                binding.rlEditAccount.setBackgroundResource(R.color.white)
                binding.rlEditAccount2.setBackgroundResource(R.drawable.round_white)
                binding.imgEditAccount.setColorFilter(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.statusbarcolor
                    )
                )
                binding.tvEditAccount.setTextColor(resources.getColor(R.color.haiti))
                binding.rlMyAddress.setBackgroundResource(R.color.white)
                binding.rlMyAddress2.setBackgroundResource(R.drawable.round_white)
                binding.imgMyAddress.setColorFilter(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.statusbarcolor
                    )
                )
                binding.tvMyAddress.setTextColor(resources.getColor(R.color.haiti))
                binding.rlSettings.setBackgroundResource(R.color.white)
                binding.rlSettings2.setBackgroundResource(R.drawable.round_white)
                binding.imgSettings.setColorFilter(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.statusbarcolor
                    )
                )
                binding.tvSettings.setTextColor(resources.getColor(R.color.haiti))

                startActivity(Intent(requireContext(), BodyMeasurementsActivity::class.java))
            }
        }

        binding.cardMyPayments.setOnClickListener {
            if (type == "3") {
                guestDialog()
            } else {
                binding.rlMyGoals.setBackgroundResource(R.color.white)
                binding.rlMyGoals2.setBackgroundResource(R.drawable.round_white)
                binding.imgGoalsIcon.setColorFilter(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.statusbarcolor
                    )
                )
                binding.tvGoals.setTextColor(resources.getColor(R.color.haiti))
                binding.rlBodyMeasurement.setBackgroundResource(R.color.white)
                binding.rlBodyMeasurement2.setBackgroundResource(R.drawable.round_white)
                binding.imgBodyMeasurements.setColorFilter(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.statusbarcolor
                    )
                )
                binding.tvBodyMeasurements.setTextColor(resources.getColor(R.color.haiti))
                binding.rlMyPayments.setBackgroundResource(R.color.appColor)
                binding.rlMyPayments2.setBackgroundResource(R.drawable.round_blue)
                binding.imgMyPayments.setColorFilter(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.white
                    )
                )
                binding.tvMyPayments.setTextColor(resources.getColor(R.color.white))
                binding.rlInviteFriend.setBackgroundResource(R.color.white)
                binding.rlInviteFriend2.setBackgroundResource(R.drawable.round_white)
                binding.imgInviteFriend.setColorFilter(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.statusbarcolor
                    )
                )
                binding.tvInviteFriend.setTextColor(resources.getColor(R.color.haiti))
                binding.rlEditAccount.setBackgroundResource(R.color.white)
                binding.rlEditAccount2.setBackgroundResource(R.drawable.round_white)
                binding.imgEditAccount.setColorFilter(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.statusbarcolor
                    )
                )
                binding.tvEditAccount.setTextColor(resources.getColor(R.color.haiti))
                binding.rlMyAddress.setBackgroundResource(R.color.white)
                binding.rlMyAddress2.setBackgroundResource(R.drawable.round_white)
                binding.imgMyAddress.setColorFilter(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.statusbarcolor
                    )
                )
                binding.tvMyAddress.setTextColor(resources.getColor(R.color.haiti))
                binding.rlSettings.setBackgroundResource(R.color.white)
                binding.rlSettings2.setBackgroundResource(R.drawable.round_white)
                binding.imgSettings.setColorFilter(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.statusbarcolor
                    )
                )
                binding.tvSettings.setTextColor(resources.getColor(R.color.haiti))
                startActivity(Intent(requireContext(), MyPayments2Activity::class.java))
            }
        }

        binding.cardInviteFriend.setOnClickListener {
            if (type == "3") {
                guestDialog()
            } else {
                binding.rlMyGoals.setBackgroundResource(R.color.white)
                binding.rlMyGoals2.setBackgroundResource(R.drawable.round_white)
                binding.imgGoalsIcon.setColorFilter(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.statusbarcolor
                    )
                )
                binding.tvGoals.setTextColor(resources.getColor(R.color.haiti))
                binding.rlBodyMeasurement.setBackgroundResource(R.color.white)
                binding.rlBodyMeasurement2.setBackgroundResource(R.drawable.round_white)
                binding.imgBodyMeasurements.setColorFilter(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.statusbarcolor
                    )
                )
                binding.tvBodyMeasurements.setTextColor(resources.getColor(R.color.haiti))
                binding.rlMyPayments.setBackgroundResource(R.color.white)
                binding.rlMyPayments2.setBackgroundResource(R.drawable.round_white)
                binding.imgMyPayments.setColorFilter(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.statusbarcolor
                    )
                )
                binding.tvMyPayments.setTextColor(resources.getColor(R.color.haiti))
                binding.rlInviteFriend.setBackgroundResource(R.color.appColor)
                binding.rlInviteFriend2.setBackgroundResource(R.drawable.round_blue)
                binding.imgInviteFriend.setColorFilter(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.white
                    )
                )
                binding.tvInviteFriend.setTextColor(resources.getColor(R.color.white))
                binding.rlEditAccount.setBackgroundResource(R.color.white)
                binding.rlEditAccount2.setBackgroundResource(R.drawable.round_white)
                binding.imgEditAccount.setColorFilter(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.statusbarcolor
                    )
                )
                binding.tvEditAccount.setTextColor(resources.getColor(R.color.haiti))
                binding.rlMyAddress.setBackgroundResource(R.color.white)
                binding.rlMyAddress2.setBackgroundResource(R.drawable.round_white)
                binding.imgMyAddress.setColorFilter(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.statusbarcolor
                    )
                )
                binding.tvMyAddress.setTextColor(resources.getColor(R.color.haiti))
                binding.rlSettings.setBackgroundResource(R.color.white)
                binding.rlSettings2.setBackgroundResource(R.drawable.round_white)
                binding.imgSettings.setColorFilter(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.statusbarcolor
                    )
                )
                binding.tvSettings.setTextColor(resources.getColor(R.color.haiti))


                try {
                    val shareIntent = Intent(Intent.ACTION_SEND)
                    shareIntent.type = "text/plain"
                    shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Tabadoul")
                    var shareMessage = "\nLet me recommend you this application::\n"
                    shareMessage =
                        """
                    ${shareMessage}https://play.google.com/store/apps/details?id=${BuildConfig.APPLICATION_ID}
                    """.trimIndent()
                    shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage)
                    startActivity(Intent.createChooser(shareIntent, "choose one"))
                } catch (e: Exception) {
                    //e.toString();
                }
            }

        }

        binding.cardEditAccount.setOnClickListener {
            if (type == "3") {
                guestDialog()
            } else {
                binding.rlMyGoals.setBackgroundResource(R.color.white)
                binding.rlMyGoals2.setBackgroundResource(R.drawable.round_white)
                binding.imgGoalsIcon.setColorFilter(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.statusbarcolor
                    )
                )
                binding.tvGoals.setTextColor(resources.getColor(R.color.haiti))
                binding.rlBodyMeasurement.setBackgroundResource(R.color.white)
                binding.rlBodyMeasurement2.setBackgroundResource(R.drawable.round_white)
                binding.imgBodyMeasurements.setColorFilter(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.statusbarcolor
                    )
                )
                binding.tvBodyMeasurements.setTextColor(resources.getColor(R.color.haiti))
                binding.rlMyPayments.setBackgroundResource(R.color.white)
                binding.rlMyPayments2.setBackgroundResource(R.drawable.round_white)
                binding.imgMyPayments.setColorFilter(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.statusbarcolor
                    )
                )
                binding.tvMyPayments.setTextColor(resources.getColor(R.color.haiti))
                binding.rlInviteFriend.setBackgroundResource(R.color.white)
                binding.rlInviteFriend2.setBackgroundResource(R.drawable.round_white)
                binding.imgInviteFriend.setColorFilter(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.statusbarcolor
                    )
                )
                binding.tvInviteFriend.setTextColor(resources.getColor(R.color.haiti))
                binding.rlEditAccount.setBackgroundResource(R.color.appColor)
                binding.rlEditAccount2.setBackgroundResource(R.drawable.round_blue)
                binding.imgEditAccount.setColorFilter(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.white
                    )
                )
                binding.tvEditAccount.setTextColor(resources.getColor(R.color.white))
                binding.rlMyAddress.setBackgroundResource(R.color.white)
                binding.rlMyAddress2.setBackgroundResource(R.drawable.round_white)
                binding.imgMyAddress.setColorFilter(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.statusbarcolor
                    )
                )
                binding.tvMyAddress.setTextColor(resources.getColor(R.color.haiti))
                binding.rlSettings.setBackgroundResource(R.color.white)
                binding.rlSettings2.setBackgroundResource(R.drawable.round_white)
                binding.imgSettings.setColorFilter(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.statusbarcolor
                    )
                )
                binding.tvSettings.setTextColor(resources.getColor(R.color.haiti))

                startActivity(Intent(requireContext(), SupportActivity::class.java))
            }
        }

        binding.cardMyAddress.setOnClickListener {
            if (type == "3") {
                guestDialog()
            } else {
                binding.rlMyGoals.setBackgroundResource(R.color.white)
                binding.rlMyGoals2.setBackgroundResource(R.drawable.round_white)
                binding.imgGoalsIcon.setColorFilter(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.statusbarcolor
                    )
                )
                binding.tvGoals.setTextColor(resources.getColor(R.color.haiti))
                binding.rlBodyMeasurement.setBackgroundResource(R.color.white)
                binding.rlBodyMeasurement2.setBackgroundResource(R.drawable.round_white)
                binding.imgBodyMeasurements.setColorFilter(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.statusbarcolor
                    )
                )
                binding.tvBodyMeasurements.setTextColor(resources.getColor(R.color.haiti))
                binding.rlMyPayments.setBackgroundResource(R.color.white)
                binding.rlMyPayments2.setBackgroundResource(R.drawable.round_white)
                binding.imgMyPayments.setColorFilter(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.statusbarcolor
                    )
                )
                binding.tvMyPayments.setTextColor(resources.getColor(R.color.haiti))
                binding.rlInviteFriend.setBackgroundResource(R.color.white)
                binding.rlInviteFriend2.setBackgroundResource(R.drawable.round_white)
                binding.imgInviteFriend.setColorFilter(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.statusbarcolor
                    )
                )
                binding.tvInviteFriend.setTextColor(resources.getColor(R.color.haiti))
                binding.rlEditAccount.setBackgroundResource(R.color.white)
                binding.rlEditAccount2.setBackgroundResource(R.drawable.round_white)
                binding.imgEditAccount.setColorFilter(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.statusbarcolor
                    )
                )
                binding.tvEditAccount.setTextColor(resources.getColor(R.color.haiti))
                binding.rlMyAddress.setBackgroundResource(R.color.appColor)
                binding.rlMyAddress2.setBackgroundResource(R.drawable.round_blue)
                binding.imgMyAddress.setColorFilter(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.white
                    )
                )
                binding.tvMyAddress.setTextColor(resources.getColor(R.color.white))
                binding.rlSettings.setBackgroundResource(R.color.white)
                binding.rlSettings2.setBackgroundResource(R.drawable.round_white)
                binding.imgSettings.setColorFilter(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.statusbarcolor
                    )
                )
                binding.tvSettings.setTextColor(resources.getColor(R.color.haiti))

                startActivity(Intent(context, MyAddressActivity::class.java))
            }
        }

        binding.cardSettings.setOnClickListener {
            if (type == "3") {
                guestDialog()
            } else {
                binding.rlMyGoals.setBackgroundResource(R.color.white)
                binding.rlMyGoals2.setBackgroundResource(R.drawable.round_white)
                binding.imgGoalsIcon.setColorFilter(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.statusbarcolor
                    )
                )
                binding.tvGoals.setTextColor(resources.getColor(R.color.haiti))
                binding.rlBodyMeasurement.setBackgroundResource(R.color.white)
                binding.rlBodyMeasurement2.setBackgroundResource(R.drawable.round_white)
                binding.imgBodyMeasurements.setColorFilter(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.statusbarcolor
                    )
                )
                binding.tvBodyMeasurements.setTextColor(resources.getColor(R.color.haiti))
                binding.rlMyPayments.setBackgroundResource(R.color.white)
                binding.rlMyPayments2.setBackgroundResource(R.drawable.round_white)
                binding.imgMyPayments.setColorFilter(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.statusbarcolor
                    )
                )
                binding.tvMyPayments.setTextColor(resources.getColor(R.color.haiti))
                binding.rlInviteFriend.setBackgroundResource(R.color.white)
                binding.rlInviteFriend2.setBackgroundResource(R.drawable.round_white)
                binding.imgInviteFriend.setColorFilter(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.statusbarcolor
                    )
                )
                binding.tvInviteFriend.setTextColor(resources.getColor(R.color.haiti))
                binding.rlEditAccount.setBackgroundResource(R.color.white)
                binding.rlEditAccount2.setBackgroundResource(R.drawable.round_white)
                binding.imgEditAccount.setColorFilter(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.statusbarcolor
                    )
                )
                binding.tvEditAccount.setTextColor(resources.getColor(R.color.haiti))

                binding.rlMyAddress.setBackgroundResource(R.color.white)
                binding.rlMyAddress2.setBackgroundResource(R.drawable.round_white)
                binding.imgMyAddress.setColorFilter(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.statusbarcolor
                    )
                )
                binding.tvMyAddress.setTextColor(resources.getColor(R.color.haiti))
                binding.rlSettings.setBackgroundResource(R.color.appColor)
                binding.rlSettings2.setBackgroundResource(R.drawable.round_blue)
                binding.imgSettings.setColorFilter(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.white
                    )
                )
                binding.tvSettings.setTextColor(resources.getColor(R.color.white))
                startActivity(Intent(requireContext(), SettingsActivity::class.java))
            }
        }
    }

    private fun guestDialog() {
        val dialog = Dialog(requireContext())
        dialog.setContentView(R.layout.dialog_guestmode)
        val lp = WindowManager.LayoutParams()
        lp.copyFrom(dialog.window!!.attributes)
        lp.width = WindowManager.LayoutParams.MATCH_PARENT
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT
        lp.gravity = Gravity.CENTER
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.window!!.attributes = lp
        val layoutsend = dialog.findViewById<RelativeLayout>(R.id.layout_send)
        val imgClose = dialog.findViewById<ImageView>(R.id.imgClose)


        layoutsend.setOnClickListener {
            val intent = Intent(requireContext(), LoginActivity::class.java)
            startActivity(intent)
            dialog.dismiss()
        }
        imgClose.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
    }


}