package com.easylife.easylifes.trainerside.fragments

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.easylife.easylifes.R
import com.easylife.easylifes.databinding.FragmentTrainerExcersiceBinding
import com.easylife.easylifes.model.allclients.AllClientsResponseModel
import com.easylife.easylifes.model.signup.SignUpDataModel
import com.easylife.easylifes.model.trainerhome.TrainerUserDataModel
import com.easylife.easylifes.trainerside.activities.clientdetail.AllWorkoutsActivity
import com.easylife.easylifes.trainerside.adapter.AllClientsListAdapter
import com.easylife.easylifes.utils.Utilities
import com.google.gson.Gson
import com.tabadol.tabadol.data.network.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import kotlin.collections.ArrayList

class TrainerExcersiceFragment : Fragment(),AllClientsListAdapter.onAllClientDetailClick {
    private lateinit var binding :FragmentTrainerExcersiceBinding
    private lateinit var utilities : Utilities
    lateinit var adapter : AllClientsListAdapter
    private var allClientsList : ArrayList<TrainerUserDataModel> = ArrayList()
    var filterList: ArrayList<TrainerUserDataModel> = ArrayList()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentTrainerExcersiceBinding.inflate(inflater,container,false)

        adapter = AllClientsListAdapter(requireContext(),allClientsList,this)
        initViews()
        return binding.root
    }

    private fun initViews() {
        utilities = Utilities(requireContext())
        getAllClients()

        binding.edSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable) {
                filterList.clear()
                if (s.toString().isEmpty()) {
                    binding.rvAllClients.adapter =(AllClientsListAdapter(requireContext(), allClientsList,this@TrainerExcersiceFragment))
                    adapter.notifyDataSetChanged()
                } else {
                    try {
                        filter(s.toString())
                    } catch (e: java.lang.Exception) {
                        Toast.makeText(requireContext(), "" + e.message, Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            }
        })
    }
    private fun filter(text: String) {
        if (allClientsList.size > 0) {
            for (post in allClientsList) {
                if (post.name.lowercase()
                        .contains(text.lowercase(Locale.getDefault()))
                ) {
                    filterList.add(post)
                }
            }
            binding.rvAllClients.adapter =(AllClientsListAdapter(requireContext(), filterList,this@TrainerExcersiceFragment))
            adapter.notifyDataSetChanged()
        } else {
            Toast.makeText(
                requireContext(),
                "No User Found",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun getAllClients() {
        val apiClient = ApiClient()
        if (utilities.isConnectingToInternet(requireContext())) {
            val gsonn = Gson()
            val jsonn: String = utilities.getString(requireContext(), "loginResponse")
            val obj: SignUpDataModel = gsonn.fromJson(jsonn, SignUpDataModel::class.java)
            val useridd: String = java.lang.String.valueOf(obj.id)

            binding.dotloader.visibility = View.VISIBLE
            val url = apiClient.BASE_URL + "all-clients/"+useridd
            apiClient.getApiService().allClients(url)
                .enqueue(object : Callback<AllClientsResponseModel> {

                    override fun onResponse(
                        call: Call<AllClientsResponseModel>,
                        response: Response<AllClientsResponseModel>
                    ) {
                        binding.dotloader.visibility = View.GONE
                        val signupResponse = response.body()
                        if (this@TrainerExcersiceFragment.isAdded) {
                            if (signupResponse!!.status) {
                                //banner list data


                                //categories data
                                allClientsList = ArrayList()
                                allClientsList = response.body()!!.data
                                allClients(allClientsList)



                            } else {
                                utilities.showFailureToast( requireActivity(),signupResponse.message)


                            }
                        }

                    }

                    override fun onFailure(call: Call<AllClientsResponseModel>, t: Throwable) {
                        binding.dotloader.visibility = View.GONE
                        utilities.showFailureToast(requireActivity(),t.message!!)
                    }


                })


        } else {

            utilities.showFailureToast(requireActivity(), resources.getString(R.string.checkinternet))

        }

    }

    private fun allClients(categoriesList : ArrayList<TrainerUserDataModel>) {
        binding.rvAllClients.layoutManager = LinearLayoutManager(requireContext())
        binding.rvAllClients.adapter = AllClientsListAdapter(requireContext(),categoriesList,this)

    }

    override fun onClickArea(position: Int) {
        val model = allClientsList.get(position)
        val intent = Intent(requireContext(),AllWorkoutsActivity::class.java)
        intent.putExtra("clientid",model.id.toString())
        startActivity(intent)

    }

}