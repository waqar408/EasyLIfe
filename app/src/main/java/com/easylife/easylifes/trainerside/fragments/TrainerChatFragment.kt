package com.easylife.easylifes.trainerside.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.easylife.easylifes.databinding.FragmentTrainerChatBinding
import com.easylife.easylifes.model.chat.messenger.MessengerDataModel
import com.easylife.easylifes.model.chat.messenger.MessengerResponseModel
import com.easylife.easylifes.model.signup.SignUpDataModel
import com.easylife.easylifes.userside.adapter.ChatAdapter
import com.easylife.easylifes.utils.Utilities
import com.google.gson.Gson
import com.tabadol.tabadol.data.network.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import kotlin.collections.ArrayList

class TrainerChatFragment : Fragment() {
    private lateinit var binding : FragmentTrainerChatBinding
    private var chatList : ArrayList<MessengerDataModel> =ArrayList()
    private lateinit var utilities: Utilities
    var filterList: ArrayList<MessengerDataModel> = ArrayList()
    private var userId = ""
    lateinit var adapter : ChatAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentTrainerChatBinding.inflate(inflater,container,false)

        initViews()
        return binding.root
    }

    private fun initViews() {
        adapter = ChatAdapter(requireContext(),chatList)
        utilities = Utilities(requireContext())
        val gsonn = Gson()
        val jsonn: String = utilities.getString(requireContext(), "loginResponse")
        val obj: SignUpDataModel = gsonn.fromJson(jsonn, SignUpDataModel::class.java)
        userId = obj.id.toString()
        getChatList()

        binding.edSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable) {
                filterList.clear()
                if (s.toString().isEmpty()) {
                    binding.rvChat.adapter = ChatAdapter(requireContext(), chatList)
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

    override fun onResume() {
        super.onResume()
        getChatList()
    }

    private fun filter(text: String) {
        if (chatList.size > 0) {
            for (post in chatList) {
                if (post.other_user_name.lowercase()
                        .contains(text.lowercase(Locale.getDefault()))
                ) {
                    filterList.add(post)
                }
            }
            binding.rvChat.adapter = ChatAdapter(requireContext(), filterList)
            adapter.notifyDataSetChanged()
        } else {
            Toast.makeText(
                requireContext(),
                "No User Found",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun getChatList() {
        val apiClient = ApiClient()
        if (utilities.isConnectingToInternet(requireContext())) {
            binding.dotloader.visibility = View.VISIBLE
            val url = apiClient.BASE_URL + "get_messanger/" + userId
            apiClient.getApiService().messengerList(url)
                .enqueue(object : Callback<MessengerResponseModel> {

                    override fun onResponse(
                        call: Call<MessengerResponseModel>,
                        response: Response<MessengerResponseModel>
                    ) {
                        val signupResponse = response.body()
                        if (this@TrainerChatFragment.isAdded) {
                            binding.dotloader.visibility =View.GONE
                            if (signupResponse!!.status) {
                                if (!signupResponse.data.equals("")) {
                                    //setMessagePusher()
                                    chatList = ArrayList()
                                    chatList = signupResponse.data
                                    if (chatList.isEmpty()) {
                                        binding.tvNoChat.visibility = View.VISIBLE
                                        binding.rvChat.visibility = View.GONE
                                    } else {
                                        binding.tvNoChat.visibility = View.GONE
                                        binding.rvChat.visibility = View.VISIBLE
                                    }
                                    binding.rvChat.layoutManager =
                                        LinearLayoutManager(requireContext())
                                    adapter = ChatAdapter(context!!,chatList)
                                    binding.rvChat.adapter  = adapter


                                } else {

                                    utilities.showFailureToast(requireActivity(),"No Data Found")
                                }
                            } else {

                                utilities.showFailureToast(requireActivity(),
                                    signupResponse.message

                                )
                                Log.d("mess", signupResponse.message)

                            }
                        }
                    }

                    override fun onFailure(call: Call<MessengerResponseModel>, t: Throwable) {
                        if (this@TrainerChatFragment.isAdded) {
                            binding.dotloader.visibility = View.GONE
                            utilities.showFailureToast(requireActivity(),t.message!!)
                            Log.d("mess", t.message.toString())
                        }

                    }


                })


        } else {

            utilities.showFailureToast(requireActivity(),"Check your internet connection")

        }

    }



}