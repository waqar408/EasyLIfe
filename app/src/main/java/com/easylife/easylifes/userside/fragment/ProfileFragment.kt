package com.easylife.easylifes.userside.fragment

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.easylife.easylifes.R
import com.easylife.easylifes.userside.activities.address.AddNewAddress
import com.easylife.easylifes.userside.activities.choosepackage.ChooseYourPackage
import com.easylife.easylifes.userside.activities.profile.ProfileSettingActivity
import com.easylife.easylifes.userside.activities.setting.SettingsActivity
import com.easylife.easylifes.databinding.FragmentProfileBinding
import com.easylife.easylifes.model.signup.SignUpDataModel
import com.easylife.easylifes.utils.Utilities
import com.google.gson.Gson


class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding
    private lateinit var utilities: Utilities
    var profileImage = ""
    var userName = ""
    var location = ""
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
        if (!jsonn.isEmpty()) {
            val obj: SignUpDataModel = gsonn.fromJson(jsonn, SignUpDataModel::class.java)
            profileImage = obj.profile_image
            userName = obj.username
            location = obj.location
            Glide.with(this@ProfileFragment).load(profileImage).into(binding.profileImage)
            binding.userName.text= userName
            binding.tvLocation.text = location

        }
    }

    private fun statusbarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requireActivity().getWindow()
                .setStatusBarColor(requireActivity().getColor(R.color.haiti))
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            requireActivity().getWindow().getDecorView().getWindowInsetsController()!!
                .setSystemBarsAppearance(0, APPEARANCE_LIGHT_STATUS_BARS)
        }
    }

    private fun onClicks() {
        binding.cardGoals.setOnClickListener {
            binding.rlMyGoals.setBackgroundResource(R.color.appColor)
            binding.rlMyGoals2.setBackgroundResource(R.drawable.round_blue)
            binding.imgGoalsIcon.setColorFilter(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.white
                )
            );
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
        binding.cardBodyMeasurements.setOnClickListener {
            binding.rlMyGoals.setBackgroundResource(R.color.white)
            binding.rlMyGoals2.setBackgroundResource(R.drawable.round_white)
            binding.imgGoalsIcon.setColorFilter(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.statusbarcolor
                )
            );
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
        }
        binding.cardMyPayments.setOnClickListener {

            binding.rlMyGoals.setBackgroundResource(R.color.white)
            binding.rlMyGoals2.setBackgroundResource(R.drawable.round_white)
            binding.imgGoalsIcon.setColorFilter(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.statusbarcolor
                )
            );
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
            startActivity(Intent(requireContext(),ChooseYourPackage::class.java))
        }
        binding.cardInviteFriend.setOnClickListener {
            binding.rlMyGoals.setBackgroundResource(R.color.white)
            binding.rlMyGoals2.setBackgroundResource(R.drawable.round_white)
            binding.imgGoalsIcon.setColorFilter(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.statusbarcolor
                )
            );
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
        }
        binding.cardEditAccount.setOnClickListener {
            binding.rlMyGoals.setBackgroundResource(R.color.white)
            binding.rlMyGoals2.setBackgroundResource(R.drawable.round_white)
            binding.imgGoalsIcon.setColorFilter(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.statusbarcolor
                )
            );
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

            startActivity(Intent(requireContext(),ProfileSettingActivity::class.java))
        }
        binding.cardMyAddress.setOnClickListener {
            binding.rlMyGoals.setBackgroundResource(R.color.white)
            binding.rlMyGoals2.setBackgroundResource(R.drawable.round_white)
            binding.imgGoalsIcon.setColorFilter(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.statusbarcolor
                )
            );
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

            startActivity(Intent(context,AddNewAddress::class.java))
        }
        binding.cardSettings.setOnClickListener {
            binding.rlMyGoals.setBackgroundResource(R.color.white)
            binding.rlMyGoals2.setBackgroundResource(R.drawable.round_white)
            binding.imgGoalsIcon.setColorFilter(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.statusbarcolor
                )
            );
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
            startActivity(Intent(requireContext(),SettingsActivity::class.java))
        }
    }
}