package com.easylife.easylifes.userside.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.easylife.easylifes.R
import com.easylife.easylifes.userside.adapter.GoalsAdapter
import com.easylife.easylifes.databinding.FragmentGoalsBinding
import com.easylife.easylifes.model.JobsDataModel


class GoalsFragment : Fragment() {
    private lateinit var binding : FragmentGoalsBinding
    private lateinit var goalsList : ArrayList<JobsDataModel>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentGoalsBinding.inflate(inflater,container,false)

        goalsData()
        return binding.root
    }

    private fun goalsData() {
        goalsList = ArrayList()
        goalsList.add(JobsDataModel(R.drawable.goalicon,"Chicken"))
        goalsList.add(JobsDataModel(R.drawable.goalicon,"Chicken"))
        goalsList.add(JobsDataModel(R.drawable.goalicon,"Chicken"))
        goalsList.add(JobsDataModel(R.drawable.goalicon,"Chicken"))
        goalsList.add(JobsDataModel(R.drawable.goalicon,"Chicken"))
        binding.rvGoals.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)
        binding.rvGoals.adapter = GoalsAdapter(requireContext(),goalsList)
    }
}