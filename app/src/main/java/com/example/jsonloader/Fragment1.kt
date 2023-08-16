package com.example.jsonloader


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.example.jsonloader.databinding.Fragment1Binding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

private const val ARG_PARAM1 = "position"

class Fragment1 constructor() : Fragment(R.layout.fragment1) {
    var position: Int

    init {
        position = 0
    }


    private var _binding: Fragment1Binding? = null
    private val binding get() = _binding!!


    private lateinit var fragmentViewModel: viewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            position = it.getInt(ARG_PARAM1)
        }
        position = (arguments?.getInt(ARG_PARAM1)) as Int
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        _binding = Fragment1Binding.inflate(inflater, container, false)
        return binding.root


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val usersList = mutableListOf<User>()
        val firstUsers = mutableListOf<User>()
        val secondUsers = mutableListOf<User>()
        fragmentViewModel = ViewModelProvider(this).get(viewModel::class.java)
        fragmentViewModel.getUserData()
        fragmentViewModel.users.observe(viewLifecycleOwner, Observer { myUsers ->
            usersList.addAll(myUsers)
            for (i in 0 until usersList.size step 2) {
                firstUsers.add(usersList.get(i))
            }
            for (i in 1 until usersList.size step 2) {
                secondUsers.add(usersList.get(i))
            }
            var id: String = firstUsers.get(position).id
            val email = firstUsers.get(position).email
            val firstName: String = firstUsers.get(position).firstName
            val lastName: String = firstUsers.get(position).lastName
            val dateUpdate: String = firstUsers.get(position).dateUpdate

            val second_users_id: String = secondUsers.get(position).id
            val second_users_email: String = secondUsers.get(position).email
            val second_users_firstName: String = secondUsers.get(position).firstName
            val second_users_lastName: String = secondUsers.get(position).lastName
            val second_users_dateUpdate: String = secondUsers.get(position).dateUpdate

            activity?.runOnUiThread() {

                binding.TV1.text = id
                binding.TV2.text = email
                binding.TV3.text = firstName
                binding.TV4.text = lastName
                binding.TV5.text = dateUpdate
                binding.TV6.text = second_users_id
                binding.TV7.text = second_users_email
                binding.TV8.text = second_users_firstName
                binding.TV9.text = second_users_lastName
                binding.TV10.text = second_users_dateUpdate
            }
        })


        binding.gotoPage.setOnClickListener {
            binding.pageNumber.setVisibility(View.VISIBLE)
            binding.goToPage.setVisibility(View.VISIBLE)
            binding.emailText.setVisibility(View.GONE)
            binding.goToEmail.setVisibility(View.GONE)
            binding.pageNumber.setHint("page")
        }

        binding.goToPage.setOnClickListener {
            try {
                if ((binding.pageNumber.getText() != null) && (binding.pageNumber.getText()
                        .toString().toInt() > 0) && (binding.pageNumber.getText().toString()
                        .toInt() <= 50)
                ) {
                    val pageNumber: Int = binding.pageNumber.getText().toString().toInt()
                    val viewPager = activity?.findViewById<ViewPager2>(R.id.viewPager)
                    viewPager?.setCurrentItem(pageNumber - 1, false)
                    binding.pageNumber.setVisibility(View.GONE)
                    binding.goToPage.setVisibility(View.GONE)
                } else {
                    activity?.runOnUiThread() {
                        binding.emailText.clearFocus()
                        binding.pageNumber.getText().clear()
                        binding.pageNumber.setHint("page not found")
                    }
                    CoroutineScope(Dispatchers.IO).launch {
                        Thread.sleep(1000)
                        activity?.runOnUiThread() {
                            binding.pageNumber.setVisibility(View.GONE)
                            binding.goToPage.setVisibility(View.GONE)
                        }
                    }
                }

            } catch (e: Exception) {
                binding.pageNumber.setVisibility(View.GONE)
                binding.goToPage.setVisibility(View.GONE)
            }

        }


        binding.findByemail.setOnClickListener {
            binding.emailText.setVisibility(View.VISIBLE)
            binding.goToEmail.setVisibility(View.VISIBLE)
            binding.pageNumber.setVisibility(View.GONE)
            binding.goToPage.setVisibility(View.GONE)
            binding.emailText.setHint("email")


        }

        binding.goToEmail.setOnClickListener {

            fun gotoEmail() {

                try {
                    if (binding.emailText.getText().toString() != null) {
                        val email = binding.emailText.getText().toString()

                        var page = -1

                        for (i in 0..49) {
                            if ((firstUsers.get(i).email == email) || (secondUsers.get(i).email == email)) {

                                page = i
                                if (page >= 0) {
                                    activity?.runOnUiThread() {

                                        val viewPager =
                                            activity?.findViewById<ViewPager2>(R.id.viewPager)
                                        viewPager?.setCurrentItem(page, false)

                                        binding.emailText.setVisibility(View.GONE)
                                        binding.goToEmail.setVisibility(View.GONE)

                                    }
                                }
                            } else {
                                activity?.runOnUiThread() {
                                    binding.emailText.clearFocus()
                                    binding.emailText.getText().clear()
                                    binding.emailText.setHint("email not found")
                                }
                                CoroutineScope(Dispatchers.IO).launch {
                                    Thread.sleep(1000)
                                    activity?.runOnUiThread() {
                                        0
                                        binding.emailText.setVisibility(View.GONE)
                                        binding.goToEmail.setVisibility(View.GONE)
                                    }
                                }
                            }
                        }
                    }
                } catch (e: java.lang.Exception) {
                    binding.emailText.setVisibility(View.GONE)
                    binding.goToEmail.setVisibility(View.GONE)
                }

            }
            gotoEmail()
        }

    }


    companion object {

        @JvmStatic
        fun newInstance(param1: Int) =
            Fragment1().apply {
                arguments = Bundle().apply {
                    putInt(ARG_PARAM1, param1)
                }
            }
    }
}
