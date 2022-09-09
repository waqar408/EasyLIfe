package com.easylife.easylifes.trainerside.fragments

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowInsetsController
import androidx.core.content.ContextCompat
import com.easylife.easylifes.R
import com.easylife.easylifes.databinding.FragmentTrainerProfileBinding
import com.easylife.easylifes.trainerside.activities.clientdetail.AllClientDetailActivity
import com.easylife.easylifes.trainerside.activities.nutrition.AllNutritionsActivity
import com.easylife.easylifes.userside.activities.Support.SupportActivity
import com.easylife.easylifes.userside.activities.profile.EditProfileActivity


class TrainerProfileFragment : Fragment() {
    private lateinit var binding : FragmentTrainerProfileBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        // Inflate the layout for this fragment
        binding = FragmentTrainerProfileBinding.inflate(inflater,container,false)

        statusbarColor()
        onClicks()
        return binding.root
    }

    private fun statusbarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requireActivity().getWindow()
                .setStatusBarColor(requireActivity().getColor(R.color.haiti))
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            requireActivity().getWindow().getDecorView().getWindowInsetsController()!!
                .setSystemBarsAppearance(0, WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS)
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
            startActivity(Intent(requireContext(), AllClientDetailActivity::class.java))
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
            startActivity(Intent(requireContext(), AllNutritionsActivity::class.java))

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
            startActivity(Intent(requireContext(), EditProfileActivity::class.java))

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

            startActivity(Intent(requireContext(),SupportActivity::class.java))
        }

    }
}