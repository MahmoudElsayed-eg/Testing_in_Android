package com.melsayed.testinginandroid.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.RequestManager
import com.google.android.material.snackbar.Snackbar
import com.melsayed.testinginandroid.databinding.FragmentAddShoppingItemBinding
import com.melsayed.testinginandroid.other.Status
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AddShoppingItemFrag : Fragment() {
    private var _binding: FragmentAddShoppingItemBinding? = null
    private val binding get() = _binding!!
//    private val viewModel by activityViewModels<ShoppingViewModel>()
    lateinit var viewModel:ShoppingViewModel
    @Inject
    lateinit var glide:RequestManager
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddShoppingItemBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(ShoppingViewModel::class.java)
        binding.apply {
            ivShoppingImage.setOnClickListener {
                val action =
                    AddShoppingItemFragDirections.actionAddShoppingItemFragToImagePickFrag()
                findNavController().navigate(action)
            }
            btnAddShoppingItem.setOnClickListener {
                val name = etShoppingItemName.text.toString()
                val amount = etShoppingItemAmount.text.toString()
                val price = etShoppingItemPrice.text.toString()
                viewModel.validateShoppingItem(name, amount, price)
            }
            viewModel.curImgUrl.observe(viewLifecycleOwner) {
                glide.load(it).into(ivShoppingImage)
            }
            viewModel.insertShoppingItemStatus.observe(viewLifecycleOwner) {
                it.getContentIfNotHandled()?.let { result ->
                    when(result.status) {
                        Status.SUCCESS -> {
                            Snackbar.make(
                                requireActivity().findViewById(android.R.id.content),"Success",Snackbar.LENGTH_SHORT
                            ).show()
                            findNavController().popBackStack()
                        }
                        Status.ERROR -> {
                            Snackbar.make(
                                requireActivity().findViewById(android.R.id.content),result.message ?: "Error",Snackbar.LENGTH_SHORT
                            ).show()
                        }
                        Status.LOADING-> {
                            /* No-Operation*/
                        }
                    }
                }
            }
        }
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                viewModel.setCurImgUrl("")
                findNavController().popBackStack()
            }
        }

        requireActivity().onBackPressedDispatcher.addCallback(callback)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}