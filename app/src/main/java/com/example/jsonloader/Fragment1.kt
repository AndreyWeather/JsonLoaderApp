package com.example.jsonloader


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.example.jsonloader.databinding.Fragment1Binding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch



class Fragment1 constructor(_position: Int) : Fragment(R.layout.fragment1) {
    val position: Int

    init {
        position = _position
    }


    private var _binding: Fragment1Binding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        _binding = Fragment1Binding.inflate(inflater, container, false)
        return binding.root


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.gotoPage.setOnClickListener {
            binding.pageNumber.setVisibility(View.VISIBLE)
            binding.goToPage.setVisibility(View.VISIBLE)
            binding.emailText.setVisibility(View.GONE)
            binding.goToEmail.setVisibility(View.GONE)/*val viewPager = activity?.findViewById<ViewPager2>(R.id.viewPager)
            viewPager?.setCurrentItem(9, false)*/
        }

        binding.goToPage.setOnClickListener {
            try {
                if ((binding.pageNumber.getText() != null) && (binding.pageNumber.getText()
                        .toString().toInt() > 0) && (binding.pageNumber.getText().toString()
                        .toInt() < 50)
                ) {
                    val pageNumber: Int = binding.pageNumber.getText().toString().toInt()
                    val viewPager = activity?.findViewById<ViewPager2>(R.id.viewPager)
                    viewPager?.setCurrentItem(pageNumber - 1, false)
                } else {
                    binding.pageNumber.setVisibility(View.GONE)
                    binding.goToPage.setVisibility(View.GONE)
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

        }

        binding.goToEmail.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    if (binding.emailText.getText().toString() != null) {
                        val email = binding.emailText.getText().toString()

                        val loader = Loader()
                        val usersList = loader.jsonParsing(loader.jsonLoader())
                        val firstUsers = mutableListOf<User>()
                        val secondUsers = mutableListOf<User>()


                        for (i in 0 until usersList.size step 2) {
                            firstUsers.add(usersList.get(i))
                        }
                        for (i in 1 until usersList.size step 2) {
                            secondUsers.add(usersList.get(i))
                        }
                        var page = 0;



                        for (i in 0..49) {
                            if ((firstUsers.get(i).email == email) || (secondUsers.get(i).email == email))

                                {

                                page = i + 1
                                if (page > 0) {
                                    activity?.runOnUiThread() {
                                        binding.TV1.text = page.toString()

                                        val viewPager = activity?.findViewById<ViewPager2>(R.id.viewPager)
                                        viewPager?.setCurrentItem(page - 1, false)





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

        }



        fun setText() {
            val loader = Loader()

            CoroutineScope(Dispatchers.IO).launch {
                val usersList = loader.jsonParsing(loader.jsonLoader())
                val firstUsers = mutableListOf<User>()
                val secondUsers = mutableListOf<User>()

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
            }
        }
        setText()

    }


    companion object {

        @JvmStatic
        fun newInstance() = Fragment1(_position = 10)

    }


}
